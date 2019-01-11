package org.example.service.product;

import org.example.dto.product.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Optional<ProductDto> find(Long id) {
         return productRepository.findById(id)
                 .map(product -> productMapper.toDto(product));
    }
}
