package com.operations.crud.repository;

import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new com.operations.crud.dto.EmployeeDto(a.employeeId,a.firstName,a.lastName, a.gender,a.age,a.salary," +
            "a.email)" +
            " FROM Employee a WHERE a.isDeleted=false")
    Page<EmployeeDto> getAll(Pageable pageable);


    @Query(value = "SELECT * FROM employee e WHERE e.is_deleted=false", nativeQuery = true)
    List<Employee> allFiltered();


    List<Employee> findByIsDeleted(Boolean isDeleted);

    @Query(value = "SELECT new com.operations.crud.dto.EmployeeDto(e.employeeId," +
            "e)" +
            " FROM Employee e" +
            " LEFT OUTER JOIN Department d" +
            " ON e.employeeId = d.departmentId" +
            " WHERE  e.employeeId  = ?1")
    List<EmployeeDto> onID(Long employeeId);


}


