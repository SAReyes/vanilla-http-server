package org.example.handler;

import org.example.domain.Category;
import org.example.domain.Department;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.server.RestExchange;

import java.util.Collections;
import java.util.Optional;

public class ProductHandler {
    private ProductRepository repository;
    private DepartmentRepository departmentRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper mapper;

    public ProductHandler(ProductRepository repository,
                          DepartmentRepository departmentRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Object get(RestExchange exchange) {
        String name = exchange.getParam("name");
        String departmentName = exchange.getParam("department");
        String categoryName = exchange.getParam("category");

        if (name != null) {
            return repository.findByName(name).map(it -> mapper.toDto(it)).orElse(null);
        }

        Optional<Department> department = departmentName == null
                ? Optional.empty()
                : departmentRepository.findByName(departmentName);

        Optional<Category> category = categoryName == null
                ? Optional.empty()
                : categoryRepository.findByName(categoryName);

        if (department.isPresent() && category.isPresent()) {
            return mapper.toDtoList(repository.findByDepartmentAndCategory(department.get().getId(),
                    category.get().getId()));
        } else if (department.isPresent()) {
            return mapper.toDtoList(repository.findByDepartment(department.get().getId()));
        } else if (category.isPresent()) {
            return mapper.toDtoList(repository.findByCategory(category.get().getId()));
        } else {
            return mapper.toDtoList(repository.findAll());
        }
    }
}
