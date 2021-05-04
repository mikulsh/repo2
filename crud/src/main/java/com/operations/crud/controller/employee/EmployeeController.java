package com.operations.crud.controller.employee;

import com.operations.crud.dto.DepartmentDto;
import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.repository.EmployeeRepository;
import com.operations.crud.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Api(description = "Operations pertaining to employee in application")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping("/")
    @ApiOperation(value = "Retrieves all employees")
    public ResponseEntity<List<EmployeeDto>> allEmployees() {
        return new ResponseEntity<List<EmployeeDto>>(employeeService.allFilteredEmployee(), HttpStatus.OK);
    }


    @PostMapping("/")
    @ApiOperation(value = "Creates a new employee entity in system")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<EmployeeDto>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("Deletes an existing employee from system using employee id")
    public ResponseEntity deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @ApiOperation("Updates an existing employee in system using employee id")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id,
                                                      @RequestBody EmployeeDto employeeDto) {
        employeeDto.setEmployeeId(id);
        return new ResponseEntity<EmployeeDto>(employeeService.updateEmployee(employeeDto), HttpStatus.ACCEPTED);
    }


    @GetMapping("/{employeeId}/departments")
    @ApiOperation("All departments in which Employee is related")
    public ResponseEntity<List<DepartmentDto>> departmentOfAnEmployee(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<List<DepartmentDto>>(employeeService.getEmployeeOnId(employeeId)
                .getDepartments(),
                HttpStatus.OK);
    }


    @GetMapping("/page")
    @ApiOperation("Paginated employee list")
    public Page<EmployeeDto> allInPage(@RequestParam(defaultValue = "0") Integer pageNumber,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return employeeService.allPaginated(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    @ApiOperation("Particular employee on ID")
    public ResponseEntity<EmployeeDto> employeeOnId(@PathVariable("id") Long id) {
        return new ResponseEntity<EmployeeDto>(employeeService.getEmployeeOnId(id), HttpStatus.OK);
    }

}
