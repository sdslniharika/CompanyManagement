package com.company.service;


import com.company.entity.Department;
import com.company.entity.Project;
import com.company.exception.ResourceNotFound;
import com.company.repository.DepartmentRepository;
import com.company.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final DepartmentRepository departmentRepository;

    ProjectService(ProjectRepository projectRepository,DepartmentRepository departmentRepository)
    {
        this.projectRepository=projectRepository;
        this.departmentRepository=departmentRepository;
    }

    public Project saveProject(Long departmentId,Project project)
    {
        Department department=departmentRepository.findById(departmentId).
                orElseThrow(()->new ResourceNotFound("Department Not Found"));

        project.setDepartment(department);
       return projectRepository.save(project);
    }
    public List<Project> getAllProjects()
    {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id)
    {
        return projectRepository.findById(id).
                orElseThrow(()->new ResourceNotFound("Project Not Found"));

    }

    public Project updateProject(Long id,Project project)
    {
        Project project1=projectRepository.findById(id).
                orElseThrow(()->new ResourceNotFound("Project Not Found"));

        project1.setName(project.getName());
        project1.setBudget(project.getBudget());
       return  projectRepository.save(project1);
    }

    public Project deleteProject(Long id)
    {
        Project project1=projectRepository.findById(id).
                orElseThrow(()->new ResourceNotFound("Project Not Found"));

        projectRepository.delete(project1);
        return project1;
    }

}
