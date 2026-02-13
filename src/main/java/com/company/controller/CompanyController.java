package com.company.controller;

import com.company.entity.Company;
import com.company.mapper.CompanyMapper;
import com.company.model.CompanyDTO;
import com.company.payload.APIResponse;
import com.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<CompanyDTO>> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {

        Company companyResponse = companyService.saveCompany(CompanyMapper.toCompanyEntity(companyDTO));

        APIResponse<CompanyDTO> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(),
                "Created Successfully", CompanyMapper.toCompanyDTO(companyResponse));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CompanyDTO>>> getCompanies() {


        List<Company> companyResponse = companyService.getAllCompanies();

        List<CompanyDTO> companyDTOS = companyResponse.stream().map(CompanyMapper::toCompanyDTO).toList();
        APIResponse<List<CompanyDTO>> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "Fetched Successfully", companyDTOS);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CompanyDTO>> getCompanyById(@PathVariable Long id) {

        Company company = companyService.getCompanyById(id);
        APIResponse<CompanyDTO> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "Company Fetched Successfully", CompanyMapper.toCompanyDTO(company));

        return ResponseEntity.ok(apiResponse);

    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CompanyDTO>> updateCompanyById(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO) {

        Company company = companyService.updateCompanyByID(id, CompanyMapper.toCompanyEntity(companyDTO));
        APIResponse<CompanyDTO> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "Company Updated Successfully", CompanyMapper.toCompanyDTO(company));

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCompanyById(@PathVariable Long id) {

        companyService.deleteCompanyById(id);
        APIResponse<String> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "Deleted Updated Successfully", "OK");

        return ResponseEntity.ok(apiResponse);
    }

}
