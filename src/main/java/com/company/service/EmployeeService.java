package com.company.service;


import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.entity.Project;
import com.company.exception.ResourceNotFound;
import com.company.repository.ProjectRepository;
import com.company.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private  final ProjectRepository projectRepository;

    EmployeeService(EmployeeRepository employeeRepository,ProjectRepository projectRepository)
    {
        this.employeeRepository=employeeRepository;
        this.projectRepository = projectRepository;


    }

    public Employee saveEmployee(Long projectId,Employee employee)
    {

       Project project= projectRepository.findById(projectId).
                    orElseThrow(()->new ResourceNotFound("Project Not found"));
        employee.setProject(project);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployee()
    {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id)
    {

        return employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFound("Employee not found"));
    }

   /* public Employee updateEmployeeDepartment(Long employeeId,Long p)
    {
      Department  dep= projectRepository.findById(departmentId).
                orElseThrow(()->new RuntimeException("Not Found"));

        Employee employee=employeeRepository.findById(employeeId).
                orElseThrow(()->new RuntimeException("Not found"));

        employee.setDepartment(dep);
        return employeeRepository.save(employee);
    }*/

    public Employee updateEmployee(Long id,Employee employee)
    {
        Employee employeeObject=employeeRepository.findById(id).
                orElseThrow(()->new ResourceNotFound("Employee Not Found"));

        employeeObject.setName(employee.getName());
        employeeObject.setSalary(employee.getSalary());

        return   employeeRepository.save(employeeObject);


    }

    public  Employee deleteEmployee(Long id)
    {
        Employee employee=employeeRepository.findById(id).
                orElseThrow(()->new RuntimeException("Employee Not Found"));

        employeeRepository.delete(employee);
        return  employee;
    }


}
