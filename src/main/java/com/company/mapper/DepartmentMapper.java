package com.company.mapper;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.model.DepartmentDTO;

public class DepartmentMapper {

    public static Department toDepartmentEntity(DepartmentDTO departmentDTO, Company company)
    {
        Department department=new Department();

        department.setName(departmentDTO.getName());
        department.setCompany(company);
        return department;

    }

    public static DepartmentDTO toDepartmentDTO(Department department)
    {
        DepartmentDTO departmentDTO=new DepartmentDTO();

        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setCompanyId(department.getCompany().getId());

        return departmentDTO;

    }


}
