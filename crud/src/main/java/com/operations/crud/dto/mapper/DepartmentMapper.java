package com.operations.crud.dto.mapper;

import com.operations.crud.dto.DepartmentDto;
import com.operations.crud.model.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {


    public DepartmentDto mapToDto(Department department) {
        return new DepartmentDto()
                .setDepartmentId(department.getDepartmentId())
                .setDepartmentName(department.getDepartmentName());
    }


    public Department mapToEntity(DepartmentDto departmentDto) {
        return new Department()
                .setDepartmentId(departmentDto.getDepartmentId())
                .setDepartmentName(departmentDto.getDepartmentName())
                .setDeleted(false);
    }

    public List<Department> mapListToEntity(List<DepartmentDto> departmentDtos) {
        return departmentDtos.stream().map(x -> mapToEntity(x)).collect(Collectors.toList());
    }

    public List<DepartmentDto> mapListToDto(List<Department> department) {
        return department.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
    }

}
