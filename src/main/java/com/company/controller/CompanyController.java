package com.company.controller;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.mapper.CompanyMapper;
import com.company.mapper.DepartmentMapper;
import com.company.model.CompanyDTO;
import com.company.model.DepartmentDTO;
import com.company.payload.APIResponse;
import com.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/page")
    public ResponseEntity<APIResponse<Map<String , Object>>> getAllCompanyPaginated(
            @RequestParam(defaultValue = "0")int pageNo,
            @RequestParam(defaultValue = "5")int pageSize,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortOrder
    )
    {
        Sort.Direction sort=sortOrder.equalsIgnoreCase("desc")?
                Sort.Direction.DESC:Sort.Direction.ASC;

        Pageable pageable= PageRequest.of(pageNo,pageSize,Sort.by(sort,sortBy));

        Page<Company> companyPage=companyService.getAppCompaniesPaginated(pageable);

        List<CompanyDTO> companyDTOList=companyPage.getContent().
                stream().map(CompanyMapper::toCompanyDTO).toList();

        Map<String,Object> companyDTOMap=new HashMap<>();

        companyDTOMap.put("content",companyDTOList);
        companyDTOMap.put("pageSize",companyPage.getSize());
        companyDTOMap.put("currentPage",companyPage.getNumber());
        companyDTOMap.put("totalItems",companyPage.getTotalElements());
        companyDTOMap.put("totalPages",companyPage.getTotalPages());
        companyDTOMap.put("last",companyPage.isLast());

        APIResponse<Map<String,Object>> apiResponse=new APIResponse<>(HttpStatus.OK.value(),
                "Department Paginated",companyDTOMap);
        return  ResponseEntity.ok(apiResponse);

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
