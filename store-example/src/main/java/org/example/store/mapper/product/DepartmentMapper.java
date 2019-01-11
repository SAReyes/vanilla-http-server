package org.example.store.mapper.product;

import org.example.store.domain.product.Department;
import org.example.store.dto.product.DepartmentDto;

public class DepartmentMapper extends GenericItemMapper<Department, DepartmentDto> {
    @Override
    protected DepartmentDto newDto() {
        return new DepartmentDto();
    }

    @Override
    protected Department newDomain() {
        return new Department();
    }
}
