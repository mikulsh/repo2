package com.operations.crud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.operations.crud.model.Department;
import com.operations.crud.model.Employee;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private Double salary;
    private String email;
    private List<DepartmentDto> departments;

    public EmployeeDto(long employeeId, String firstName, String lastName, String gender,
                       int age, double salary,
                       String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
        this.email = email;

    }

    public EmployeeDto(long employeeId, Employee employee) {
        this.employeeId = employeeId;
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.gender = employee.getGender();
        this.age = employee.getAge();
        this.salary = employee.getSalary();
        this.email = employee.getEmail();
        this.departments = mapListToDto(employee.getDepartments());
    }

    public DepartmentDto mapToDto(Department department) {
        return new DepartmentDto()
                .setDepartmentId(department.getDepartmentId())
                .setDepartmentName(department.getDepartmentName());
    }

    public List<DepartmentDto> mapListToDto(List<Department> department) {
        return department.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                '}';
    }
}
