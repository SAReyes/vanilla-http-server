package org.example.store.repository.product;

import org.example.store.domain.product.Department;

import java.util.List;

public class DepartmentRepository extends GenericItemRepository<Department> {
    public DepartmentRepository(List<Department> repository) {
        super(repository);
    }
}
