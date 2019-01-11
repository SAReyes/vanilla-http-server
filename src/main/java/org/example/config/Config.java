package org.example.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Entrypoint;
import org.example.domain.product.Category;
import org.example.domain.product.Department;
import org.example.domain.product.Product;
import org.example.handler.CartHandler;
import org.example.handler.ProductHandler;
import org.example.mapper.ProductMapper;
import org.example.mapper.cart.CartMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.repository.cart.CartRepository;
import org.example.repository.cart.CartRepositoryImpl;
import org.example.service.cart.CartService;
import org.example.service.cart.CartServiceImpl;
import org.example.service.product.ProductService;
import org.example.service.product.ProductServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Config {

    public static ProductStore createStore(String seed) throws IOException {
        ProductRepository productRepository = new ProductRepository(new ArrayList<>());
        DepartmentRepository departmentRepository = new DepartmentRepository(new ArrayList<>());
        CategoryRepository categoryRepository = new CategoryRepository(new ArrayList<>());
        ProductMapper productMapper = new ProductMapper(departmentRepository, categoryRepository);
        ProductHandler productHandler = new ProductHandler(productRepository, departmentRepository, categoryRepository,
                productMapper);


        ProductService productService = new ProductServiceImpl(productRepository, productMapper);

        CartRepository cartRepository = new CartRepositoryImpl(new ArrayList<>(), new AtomicLong(1));
        CartMapper cartMapper = new CartMapper(productService);
        CartService cartService = new CartServiceImpl(cartRepository, cartMapper);

        CartHandler cartHandler = new CartHandler(cartService);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode node = mapper.readTree(Entrypoint.class.getResourceAsStream(seed));

        node.findValue("products").elements().forEachRemaining(it -> {
            try {
                productRepository.save(mapper.treeToValue(it, Product.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        node.findValue("departments").elements().forEachRemaining(it -> {
            try {
                departmentRepository.save(mapper.treeToValue(it, Department.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        node.findValue("categories").elements().forEachRemaining(it -> {
            try {
                categoryRepository.save(mapper.treeToValue(it, Category.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return new ProductStore(productHandler, cartHandler);
    }
}
