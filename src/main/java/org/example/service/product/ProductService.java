package org.example.service.product;

import org.example.dto.product.ProductDto;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> find(Long id);
}
