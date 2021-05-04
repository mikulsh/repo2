package com.operations.crud.service;

import com.operations.crud.dto.DepartmentDto;
import com.operations.crud.dto.EmployeeDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    void deleteDepartment(long id);

    List<DepartmentDto> allDepartments();

    List<EmployeeDto> getEmployeesByDepartment(Long departmentId);
}
