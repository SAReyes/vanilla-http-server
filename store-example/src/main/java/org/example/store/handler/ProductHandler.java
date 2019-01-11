package org.example.store.handler;

import org.example.store.domain.product.Category;
import org.example.store.domain.product.Department;
import org.example.store.mapper.ProductMapper;
import org.example.store.repository.CategoryRepository;
import org.example.store.repository.DepartmentRepository;
import org.example.store.repository.ProductRepository;
import org.example.server.RestExchange;

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
