package org.example.mapper;

import org.example.domain.Department;
import org.example.domain.Product;
import org.example.dto.ProductDto;
import org.example.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductMapper {
    private DepartmentRepository departmentRepository;

    public ProductMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ProductDto toDto(Product product) {
        List<Department> departments = product.getDepartments().stream()
                .map(it -> departmentRepository.findById(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDepartments(departments);

        return dto;
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
