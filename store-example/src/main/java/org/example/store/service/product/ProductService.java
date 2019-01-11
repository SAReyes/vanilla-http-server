package org.example.store.service.product;

import org.example.store.dto.product.ProductDto;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> find(Long id);
}
