package com.operations.crud.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private Double salary;
    private String email;
    private boolean isDeleted;


    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "emp_dep",
            joinColumns = {
                    @JoinColumn(name = "employee_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "department_id")
            })
    private List<Department> departments;

    public Employee(Long employeeId, String firstName, String lastName, Integer age, String gender, Double salary,
                    String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.email = email;
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
