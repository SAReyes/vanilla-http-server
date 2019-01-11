package org.example.store.service.product;

import org.example.store.domain.product.Department;
import org.example.store.dto.product.DepartmentDto;
import org.example.store.mapper.product.DepartmentMapper;
import org.example.store.repository.product.DepartmentRepository;

public class DepartmentServiceImpl extends GenericItemServiceImpl<Department, DepartmentDto>
        implements DepartmentService {

    public DepartmentServiceImpl(DepartmentRepository repository,
                                    DepartmentMapper mapper) {
        super(repository, mapper);
    }
}
