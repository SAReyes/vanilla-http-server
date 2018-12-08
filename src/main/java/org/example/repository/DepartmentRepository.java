package org.example.repository;

import org.example.domain.Department;

import java.util.List;

public class DepartmentRepository extends GenericRepository<Department> {
    public DepartmentRepository(List<Department> repository) {
        super(repository);
    }
}
