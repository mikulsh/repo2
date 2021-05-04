package com.operations.crud.controller.department;

import com.operations.crud.dto.DepartmentDto;
import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @GetMapping("/")
    @ApiOperation("All departments in system")
    public ResponseEntity<List<DepartmentDto>> allDepartments() {
        return new ResponseEntity<List<DepartmentDto>>(departmentService.allDepartments(), HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("New department in system")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<DepartmentDto>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }


    @GetMapping("/{departmentId}/employees")
    @ApiOperation("All employees related to particular department")
    public ResponseEntity<List<EmployeeDto>> allEmployeesInADepartment(@PathVariable("departmentId") Long departmentId) {
        return new ResponseEntity<List<EmployeeDto>>(departmentService
                .getEmployeesByDepartment(departmentId),
                HttpStatus.OK);
    }

    //  Soft Deletion
    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Removes an existing department")
    public ResponseEntity deleteDepartment(@PathVariable("departmentId") long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

}
