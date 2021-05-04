package com.operations.crud.service;

import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    void deleteEmployee(Long id);

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeOnId(long id);

    Page<EmployeeDto> allPaginated(int pageNumber, int pageSize, String sortBy);

    List<EmployeeDto> allFilteredEmployee();

}


