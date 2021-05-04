package com.operations.crud.service;

import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.dto.mapper.DepartmentMapper;
import com.operations.crud.dto.mapper.EmployeeMapper;
import com.operations.crud.exception.EmployeeException;
import com.operations.crud.exception.EntityType;
import com.operations.crud.exception.ExceptionType;
import com.operations.crud.model.Employee;
import com.operations.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.operations.crud.exception.EntityType.EMPLOYEE;
import static com.operations.crud.exception.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentMapper departmentMapper;


    //Stream implementation of projection
    @Override
    @Cacheable("employee-cache")
    public List<Employee> getAll() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().filter(Predicate.not(Employee::isDeleted)).collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "employee-cache", key = "#employeeDto.employeeId")
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEntity(employeeDto);
        Employee saveEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToDto(saveEmployee);
    }

    @Override
    @CacheEvict(value = "employee-cache", key = "#id")
    public void deleteEmployee(Long id) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.get().isDeleted() == false) {
            Employee updateEmployee = new Employee()
                    .setEmployeeId(existing.get().getEmployeeId())
                    .setFirstName(existing.get().getFirstName())
                    .setLastName(existing.get().getLastName())
                    .setAge(existing.get().getAge())
                    .setGender(existing.get().getGender())
                    .setSalary(existing.get().getSalary())
                    .setEmail(existing.get().getEmail())
                    .setDeleted(true)
                    .setDepartments(existing.get().getDepartments());
            employeeRepository.save(updateEmployee);
        } else {
            throw exception(EMPLOYEE, ENTITY_NOT_FOUND, String.valueOf(id));
        }

    }


    @Override
    @CachePut(value = "employee-cache", key = "#employeeDto.employeeId")
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Long id = employeeDto.getEmployeeId();
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isPresent() && existing.get().isDeleted() == Boolean.FALSE) {
            Employee employee = employeeMapper.mapToEntity(employeeDto);
            employeeRepository.save(employee);
            return employeeMapper.mapToDto(employee);
        }
        throw exception(EMPLOYEE, ENTITY_NOT_FOUND, String.valueOf(id));
    }

    @Override
    @Cacheable(value = "employee-cache", key = "#id", unless = "#result==null")
    public EmployeeDto getEmployeeOnId(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent() && employee.get().isDeleted() == Boolean.FALSE) {
            EmployeeDto employeeDto = employeeMapper.mapToDto(employee.get());
            return employeeDto;
        }
        throw exception(EMPLOYEE, ENTITY_NOT_FOUND, String.valueOf(id));
    }


    @Override
    @Cacheable(value = "employee-cache", key = "#pageNumber")
    public Page<EmployeeDto> allPaginated(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<EmployeeDto> employeeDtoPage = employeeRepository.getAll(pageable);
        return employeeDtoPage;
    }

    @Override
    @Cacheable("employee-cache")
    public List<EmployeeDto> allFilteredEmployee() {
        List<Employee> employeeList = employeeRepository.findByIsDeleted(Boolean.FALSE);
        List<EmployeeDto> dtoList = employeeMapper.mapListToDto(employeeList);
        return dtoList;
    }


    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return EmployeeException.throwException(entityType, exceptionType, args);
    }


}
