package com.operations.crud.dto.mapper;

import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    @Autowired
    DepartmentMapper mapper;


    public Employee mapToEntity(EmployeeDto employeeDto) {
        return new Employee()
                .setEmployeeId(employeeDto.getEmployeeId())
                .setFirstName(employeeDto.getFirstName())
                .setLastName(employeeDto.getLastName())
                .setAge(employeeDto.getAge())
                .setGender(employeeDto.getGender())
                .setSalary(employeeDto.getSalary())
                .setEmail(employeeDto.getEmail())
                .setDepartments(mapper.mapListToEntity(employeeDto.getDepartments()))
                .setDeleted(false);
    }

    public EmployeeDto mapToDto(Employee employee) {
        return new EmployeeDto()
                .setEmployeeId(employee.getEmployeeId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setAge(employee.getAge())
                .setGender(employee.getGender())
                .setSalary(employee.getSalary())
                .setEmail(employee.getEmail())
                .setDepartments(mapper.mapListToDto(employee.getDepartments()));

    }

    public List<EmployeeDto> mapListToDto(List<Employee> employees) {
        return employees.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
    }

    public List<Employee> mapListToEntity(List<EmployeeDto> dtoList) {
        return dtoList.stream().map(x -> mapToEntity(x)).collect(Collectors.toList());
    }

}
