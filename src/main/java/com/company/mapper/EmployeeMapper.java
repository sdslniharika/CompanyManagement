package com.company.mapper;

import com.company.entity.Employee;
import com.company.entity.Project;
import com.company.model.EmployeeDTO;

public class EmployeeMapper {

    public static Employee toEmployeeEntity(EmployeeDTO employeeDTO, Project project)
    {
        Employee employee=new Employee();


        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setProject(project);

        return employee;
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee)
    {
        EmployeeDTO employeeDTO=new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setProjectId(employee.getProject().getId());

        return employeeDTO;
    }
}
