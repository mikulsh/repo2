package com.operations.crud.service;

import com.operations.crud.dto.DepartmentDto;
import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.dto.mapper.DepartmentMapper;
import com.operations.crud.dto.mapper.EmployeeMapper;
import com.operations.crud.exception.EmployeeException;
import com.operations.crud.exception.EntityType;
import com.operations.crud.exception.ExceptionType;
import com.operations.crud.model.Department;
import com.operations.crud.model.Employee;
import com.operations.crud.repository.DepartmentRepository;
import com.operations.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<DepartmentDto> allDepartments() {
        List<Department> filteredDepartments = departmentRepository.findAll();
        filteredDepartments.stream().filter(Predicate.not(Department::isDeleted)).collect(Collectors.toList());
        return departmentMapper.mapListToDto(filteredDepartments);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department saveDepartment = departmentMapper.mapToEntity(departmentDto);
        return departmentMapper.mapToDto(departmentRepository.save(saveDepartment));
    }

    @Override
    public void deleteDepartment(long id) {
        Optional<Department> existing = departmentRepository.findById(id);
        if (existing.isPresent()) {
            Department saveDepartment = new Department()
                    .setDepartmentId(existing.get().getDepartmentId())
                    .setDepartmentName(existing.get().getDepartmentName())
                    .setDeleted(true);
            departmentRepository.save(saveDepartment);
        }
        throw exception(EntityType.DEPARTMENT, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id));
    }


    @Override
    public List<EmployeeDto> getEmployeesByDepartment(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isPresent()) {
            List<Employee> employeeList = department.get().getEmployees();
            List<EmployeeDto> dtoList = employeeMapper.mapListToDto(employeeList);
            return dtoList;
        }
        throw exception(EntityType.DEPARTMENT, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(departmentId));
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
        return EmployeeException.throwException(entityType, exceptionType, id, String.valueOf(args));
    }
}
