package com.company.mapper;

import com.company.entity.Company;
import com.company.model.CompanyDTO;

public class CompanyMapper {

    public static Company toCompanyEntity(CompanyDTO companyDTO)
    {
        Company company=new Company();

        company.setName(companyDTO.getName());
        company.setLocation(companyDTO.getLocation());

        return  company;
    }

    public static  CompanyDTO toCompanyDTO(Company company)
    {
        CompanyDTO companyDTO=new CompanyDTO();

        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setLocation(company.getLocation());
        return  companyDTO;
    }
}
