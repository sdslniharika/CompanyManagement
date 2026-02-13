package com.company.controller;


import com.company.entity.Department;
import com.company.entity.Project;
import com.company.mapper.ProjectMapper;
import com.company.model.ProjectDTO;
import com.company.payload.APIResponse;
import com.company.service.DepartmentService;
import com.company.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private  final ProjectService projectService;
    private final DepartmentService departmentService;


    ProjectController(ProjectService projectService,DepartmentService departmentService)
    {
        this.projectService=projectService;
        this.departmentService=departmentService;
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<APIResponse<ProjectDTO>> createProject(@PathVariable Long departmentId, @Valid @RequestBody ProjectDTO projectDTO)
    {

        Department department=departmentService.getDepartmentById(departmentId);

        Project projectResponse=  projectService.saveProject(departmentId,
                ProjectMapper.toProjectEntity(projectDTO,department));
        APIResponse<ProjectDTO> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Project Created Successfully",ProjectMapper.toProjectDTO(projectResponse));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProjectDTO>>> getAllProjects()
    {
         List<ProjectDTO> projectDTOList= projectService.getAllProjects().
                 stream().map(ProjectMapper::toProjectDTO).toList();

        APIResponse<List<ProjectDTO>> apiResponse=new APIResponse<>
                (HttpStatus.OK.value(), "Projects Fetched Successfully",projectDTOList);
        return  ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> getProjectById(@PathVariable Long id)
    {
        Project project=  projectService.getProjectById(id);

        APIResponse<ProjectDTO> apiResponse=new APIResponse<>
                (HttpStatus.OK.value(), "Project Fetched Successfully",ProjectMapper.toProjectDTO(project));
        return  ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> updateProject(@PathVariable Long id,@Valid @RequestBody ProjectDTO projectDTO)
    {
        Department department=departmentService.getDepartmentById(projectDTO.getDepartmentId());

        Project projectResponse=  projectService.updateProject(id,
                ProjectMapper.toProjectEntity(projectDTO,department));
        APIResponse<ProjectDTO> apiResponse=new APIResponse<>
                (HttpStatus.OK.value(), "Project Updated Successfully",ProjectMapper.toProjectDTO(projectResponse));
        return  ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>>  deleteProject(@PathVariable Long id)
    {
          projectService.deleteProject(id);

        APIResponse<String> apiResponse=new APIResponse<>
                (HttpStatus.OK.value(), "Project Deleted Successfully","Ok");
        return  ResponseEntity.ok(apiResponse);
    }

}
