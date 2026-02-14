package com.company.service;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.exception.ResourceNotFound;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private  final CompanyRepository companyRepository;

    DepartmentService(DepartmentRepository departmentRepository,CompanyRepository companyRepository)
    {
        this.departmentRepository=departmentRepository;
        this.companyRepository=companyRepository;
    }

    public Department saveDepartment(Long companyId,Department department)
    {
        Company company=companyRepository.findById(companyId).
                orElseThrow(()->new ResourceNotFound("Company not found"));

        department.setCompany(company);
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartment()
    {
        return departmentRepository.findAll();
    }
    public Page<Department> getAllDepartmentPaginated(Pageable pageable)
    {
        return departmentRepository.findAll(pageable);
    }

    public Department getDepartmentById(Long id)
    {
        return departmentRepository.findById(id).
                orElseThrow(()->new ResourceNotFound("Department not found"));
    }

    public Department updateDepartmentById(Long id,Department department)
    {
        Department depart= departmentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFound("Department not found"));

        depart.setName(department.getName());
        return departmentRepository.save(depart);
    }

    public Department deleteDepartmentById(Long id)
    {
       Department department= departmentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFound("Department not found"));

        departmentRepository.delete(department);

        return  department;

    }



}
