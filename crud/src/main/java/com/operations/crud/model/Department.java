package com.operations.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"employees"}, ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long departmentId;

    private String departmentName;

    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "departments")
    private List<Employee> employees;

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

    public Department(long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}
