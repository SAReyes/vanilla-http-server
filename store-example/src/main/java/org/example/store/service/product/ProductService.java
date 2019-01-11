package org.example.store.service.product;

import org.example.store.dto.product.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService extends GenericItemService<ProductResponseDto> {
    List<ProductResponseDto> findByDepartmentAndCategory(String department, String category);
}
