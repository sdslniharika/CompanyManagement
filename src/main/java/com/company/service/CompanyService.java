package com.company.service;

import com.company.entity.Company;
import com.company.exception.ResourceNotFound;
import com.company.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Page<Company> getAppCompaniesPaginated(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Company getCompanyById(Long id){
        return companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Company Not Found"));
    }

    public Company updateCompanyByID(Long id,Company company){
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Company Not Found"));

        existingCompany.setName(company.getName());
        existingCompany.setLocation(company.getLocation());

        return companyRepository.save(existingCompany);
    }

    public Company deleteCompanyById(Long id){
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Company Not Found"));

        companyRepository.delete(existingCompany);

        return existingCompany;
    }
}
