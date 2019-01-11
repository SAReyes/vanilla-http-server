package org.example.store.service.product;

import org.example.store.domain.product.Category;
import org.example.store.domain.product.Product;
import org.example.store.dto.product.CategoryDto;
import org.example.store.dto.product.DepartmentDto;
import org.example.store.dto.product.ProductResponseDto;
import org.example.store.mapper.product.ProductMapper;
import org.example.store.repository.product.CategoryRepository;
import org.example.store.repository.product.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl extends GenericItemServiceImpl<Product, ProductResponseDto> implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private DepartmentService departmentService;
    private CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              DepartmentService departmentService,
                              CategoryService categoryService) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.departmentService = departmentService;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductResponseDto> findByDepartmentAndCategory(String departmentName, String categoryName) {
        Optional<DepartmentDto> department = departmentName == null
                ? Optional.empty()
                : departmentService.findByName(departmentName);

        Optional<CategoryDto> category = categoryName == null
                ? Optional.empty()
                : categoryService.findByName(categoryName);

        if (department.isPresent() && category.isPresent()) {
            return productMapper
                    .toDtoList(
                            productRepository
                                    .findByDepartmentAndCategory(department.get().getId(), category.get().getId())
                    );
        } else if (department.isPresent()) {
            return productMapper.toDtoList(productRepository.findByDepartment(department.get().getId()));
        } else if (category.isPresent()) {
            return productMapper.toDtoList(productRepository.findByCategory(category.get().getId()));
        } else {
            return productMapper.toDtoList(productRepository.findAll());
        }
    }
}
