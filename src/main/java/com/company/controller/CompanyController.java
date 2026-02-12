package com.company.controller;

import com.company.entity.Company;
import com.company.service.CompanyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company){
        return companyService.saveCompany(company);
    }

    @GetMapping
    public List<Company> getCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }

    @PutMapping("/{id}")
    public Company updateCompanyById(@PathVariable Long id,@RequestBody Company company){
        return companyService.updateCompanyByID(id,company);
    }

    @DeleteMapping("/{id}")
    public Company deleteCompanyById(@PathVariable Long id){
        return companyService.deleteCompanyById(id);
    }

}
