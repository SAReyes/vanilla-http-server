package org.example.store.repository;

import org.example.store.domain.product.Department;

import java.util.List;

public class DepartmentRepository extends GenericRepository<Department> {
    public DepartmentRepository(List<Department> repository) {
        super(repository);
    }
}
