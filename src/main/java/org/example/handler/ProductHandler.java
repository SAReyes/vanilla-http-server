package org.example.handler;

import org.example.domain.Department;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.server.RestExchange;

import java.util.Collections;
import java.util.Optional;

public class ProductHandler {
    private ProductRepository repository;
    private DepartmentRepository departmentRepository;
    private ProductMapper mapper;

    public ProductHandler(ProductRepository repository,
                          DepartmentRepository departmentRepository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    public Object get(RestExchange exchange) {
        String name = exchange.getParam("name");
        String departmentName = exchange.getParam("department");

        if (name != null) {
            return repository.findByName(name).map(it -> mapper.toDto(it)).orElse(null);
        }

        if (departmentName != null) {
            Optional<Department> department = departmentRepository.findByName(departmentName);
            return department.isPresent()
                    ? mapper.toDtoList(repository.findByDepartment(department.get().getId()))
                    : Collections.emptyList();
        } else {
            return mapper.toDtoList(repository.findAll());
        }
    }
}
