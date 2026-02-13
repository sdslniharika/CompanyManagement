package com.company.mapper;

import com.company.entity.Department;
import com.company.entity.Project;
import com.company.model.ProjectDTO;

public class ProjectMapper {

    public static Project toProjectEntity(ProjectDTO projectDTO, Department department)
    {
        Project project=new Project();

        project.setName(projectDTO.getName());
        project.setBudget(projectDTO.getBudget());
        project.setDepartment(department);

        return  project;
    }

    public static  ProjectDTO toProjectDTO(Project project)
    {
        ProjectDTO projectDTO=new ProjectDTO();

        projectDTO.setId(project.getId());
        projectDTO.setBudget(project.getBudget());
        projectDTO.setName(project.getName());
        projectDTO.setDepartmentId(project.getDepartment().getId());

        return  projectDTO;

    }
}
