package com.company.controller;


import com.company.entity.Company;
import com.company.entity.Department;
import com.company.entity.Project;
import com.company.mapper.DepartmentMapper;
import com.company.mapper.ProjectMapper;
import com.company.model.DepartmentDTO;
import com.company.model.ProjectDTO;
import com.company.payload.APIResponse;
import com.company.service.CompanyService;
import com.company.service.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final CompanyService companyService;

    DepartmentController(DepartmentService departmentService,CompanyService companyService)
    {
        this.departmentService=departmentService;
        this.companyService=companyService;
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<APIResponse<DepartmentDTO>> saveDepartment(@PathVariable Long companyId, @Valid @RequestBody DepartmentDTO departmentDTO)
    {
        Company company=companyService.getCompanyById(companyId);
        Department departmentResponse= departmentService.saveDepartment(companyId,
                DepartmentMapper.toDepartmentEntity(departmentDTO,company));


        APIResponse<DepartmentDTO> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Department Created Successfully",
                        DepartmentMapper.toDepartmentDTO(departmentResponse));
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<DepartmentDTO>>> getAllDepartment()
    {
           List<DepartmentDTO> departmentList=departmentService.getAllDepartment()
                   .stream().map(DepartmentMapper::toDepartmentDTO).toList();
        APIResponse<List<DepartmentDTO>> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Department Fetched Successfully",
                        departmentList);
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/page")
    public ResponseEntity<APIResponse<Map<String , Object>>> getAllDepartmentsPaginated(
            @RequestParam(defaultValue = "0")int pageNo,
            @RequestParam(defaultValue = "5")int pageSize,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortOrder
    )
    {
        Sort.Direction sort=sortOrder.equalsIgnoreCase("desc")?
                Sort.Direction.DESC:Sort.Direction.ASC;

        Pageable pageable= PageRequest.of(pageNo,pageSize,Sort.by(sort,sortBy));

        Page<Department> departmentPage=departmentService.getAllDepartmentPaginated(pageable);

        List<DepartmentDTO> departmentDTOList=departmentPage.getContent().
                stream().map(DepartmentMapper::toDepartmentDTO).toList();

        Map<String,Object> departmentDTOMap=new HashMap<>();

        departmentDTOMap.put("content",departmentDTOList);
        departmentDTOMap.put("pageSize",departmentPage.getSize());
        departmentDTOMap.put("currentPage",departmentPage.getNumber());
        departmentDTOMap.put("totalItems",departmentPage.getTotalElements());
        departmentDTOMap.put("totalPages",departmentPage.getTotalPages());
        departmentDTOMap.put("last",departmentPage.isLast());

        APIResponse<Map<String,Object>> apiResponse=new APIResponse<>(HttpStatus.OK.value(),
                "Department Paginated",departmentDTOMap);
        return  ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<DepartmentDTO>> getDepartmentById(@PathVariable Long id)
    {
        Department department= departmentService.getDepartmentById(id);

        APIResponse<DepartmentDTO> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Department Fetched Successfully",
                        DepartmentMapper.toDepartmentDTO(department));
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<DepartmentDTO>> updateDepartmentById(@PathVariable Long id,@Valid @RequestBody DepartmentDTO departmentDTO)
    {
        Company company=companyService.getCompanyById(departmentDTO.getCompanyId());
        Department departmentResponse= departmentService.updateDepartmentById(id,DepartmentMapper.toDepartmentEntity(departmentDTO,company));

        APIResponse<DepartmentDTO> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Department Updated Successfully",
                        DepartmentMapper.toDepartmentDTO(departmentResponse));
        return ResponseEntity.ok(apiResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteDepartmentById(@PathVariable Long id)
    {
         departmentService.deleteDepartmentById(id);

        APIResponse<String> apiResponse=new APIResponse<>
                (HttpStatus.CREATED.value(), "Department Deleted Successfully",
                        "Ok");
        return ResponseEntity.ok(apiResponse);
    }
}
