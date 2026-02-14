package com.company.controller;


import com.company.entity.Employee;
import com.company.entity.Project;
import com.company.mapper.EmployeeMapper;
import com.company.model.EmployeeDTO;
import com.company.payload.APIResponse;
import com.company.service.EmployeeService;
import com.company.service.ProjectService;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ProjectService projectService;

    EmployeeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<APIResponse<EmployeeDTO>> createEmployee(@PathVariable Long projectId, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Project project = projectService.getProjectById(projectId);

        Employee employeeResponse = employeeService.saveEmployee(projectId, EmployeeMapper.toEmployeeEntity(employeeDTO, project));
        APIResponse<EmployeeDTO> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Employee Created Successfully", EmployeeMapper.toEmployeeDTO(employeeResponse));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<EmployeeDTO>>> getAllEmployee() {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployee().stream().map(EmployeeMapper::toEmployeeDTO).toList();

        APIResponse<List<EmployeeDTO>> apiResponse = new APIResponse<>(HttpStatus.OK.value(), "Employees Fetched Successfully", employeeDTOList);
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/page")
    public ResponseEntity<APIResponse<Map<String, Object>>> getAllEmployeePaginated(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction sort = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort, sortBy));

        Page<Employee> employeePage = employeeService.getAllEmployeePaginated(pageable);

        List<EmployeeDTO> employeeDTOList = employeePage.getContent().stream().map(EmployeeMapper::toEmployeeDTO).toList();

        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("content", employeeDTOList);
        responseMap.put("currentPage", employeePage.getNumber());
        responseMap.put("totalItems", employeePage.getTotalElements());
        responseMap.put("totalPages", employeePage.getTotalPages());
        responseMap.put("pageSize", employeePage.getSize());
        responseMap.put("last", employeePage.isLast());


        APIResponse<Map<String, Object>> response = new APIResponse<>(HttpStatus.OK.value(), "Employees paginated", responseMap);


        return ResponseEntity.ok(response);


    }


    @GetMapping("{id}")
    public ResponseEntity<APIResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {

        Employee employeeResponse = employeeService.getEmployeeById(id);

        APIResponse<EmployeeDTO> apiResponse = new APIResponse<>(HttpStatus.OK.value(), "Employee Fetched Successfully", EmployeeMapper.toEmployeeDTO(employeeResponse));
        return ResponseEntity.ok(apiResponse);
    }

   /* @GetMapping("/{id}/{departmentId}")
    public Employee updateEmployeeDepartment(@PathVariable Long id,@PathVariable Long departmentId)
    {
        return  employeeService.updateEmployeeDepartment(id,departmentId);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        //  employeeService.updateEmployee(id,employee);


        Project project = projectService.getProjectById(employeeDTO.getProjectId());

        Employee employeeResponse = employeeService.updateEmployee(id, EmployeeMapper.toEmployeeEntity(employeeDTO, project));
        APIResponse<EmployeeDTO> apiResponse = new APIResponse<>(HttpStatus.OK.value(), "Employee Updated Successfully", EmployeeMapper.toEmployeeDTO(employeeResponse));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        APIResponse<String> apiResponse = new APIResponse<>(HttpStatus.OK.value(), "Employee Deleted Successfully", "Ok");
        return ResponseEntity.ok(apiResponse);
    }
}
