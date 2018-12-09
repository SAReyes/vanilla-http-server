package org.example.mapper;

import org.example.domain.Category;
import org.example.domain.Department;
import org.example.domain.Product;
import org.example.dto.ProductDto;
import org.example.repository.CategoryRepository;
import org.example.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductMapper {
    private DepartmentRepository departmentRepository;
    private CategoryRepository categoryRepository;

    public ProductMapper(DepartmentRepository departmentRepository, CategoryRepository categoryRepository) {
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto toDto(Product product) {
        List<Department> departments = product.getDepartments().stream()
                .map(it -> departmentRepository.findById(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<Category> categories = product.getCategories().stream()
                .map(it -> categoryRepository.findById(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDepartments(departments);
        dto.setCategories(categories);

        return dto;
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
