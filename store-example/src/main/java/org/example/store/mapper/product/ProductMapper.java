package org.example.store.mapper.product;

import org.example.store.domain.product.Product;
import org.example.store.dto.product.CategoryDto;
import org.example.store.dto.product.DepartmentDto;
import org.example.store.dto.product.GenericItemDto;
import org.example.store.dto.product.ProductResponseDto;
import org.example.store.service.product.CategoryService;
import org.example.store.service.product.DepartmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductMapper extends GenericItemMapper<Product, ProductResponseDto> {

    private DepartmentService departmentService;
    private CategoryService categoryService;

    public ProductMapper(DepartmentService departmentService, CategoryService categoryService) {
        this.departmentService = departmentService;
        this.categoryService = categoryService;
    }

    @Override
    protected ProductResponseDto newDto() {
        return new ProductResponseDto();
    }

    @Override
    protected Product newDomain() {
        return new Product();
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = super.toDto(product);

        List<DepartmentDto> departments = product.getDepartments().stream()
                .map(it -> departmentService.find(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<CategoryDto> categories = product.getCategories().stream()
                .map(it -> categoryService.find(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        dto.setPrice(product.getPrice());
        dto.setDepartments(departments);
        dto.setCategories(categories);

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Product toDomain(ProductResponseDto product) {
        Product domain = super.toDomain(product);

        domain.setPrice(product.getPrice());
        domain.setDepartments(
                product.getDepartments().stream().map(GenericItemDto::getId).collect(Collectors.toList())
        );
        domain.setCategories(
                product.getCategories().stream().map(GenericItemDto::getId).collect(Collectors.toList())
        );

        return domain;
    }
}
