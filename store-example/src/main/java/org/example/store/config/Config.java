package org.example.store.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.store.Entrypoint;
import org.example.store.domain.product.Category;
import org.example.store.domain.product.Department;
import org.example.store.domain.product.Product;
import org.example.store.handler.CartHandler;
import org.example.store.handler.HomeHandler;
import org.example.store.handler.ProductHandler;
import org.example.store.mapper.cart.CartMapper;
import org.example.store.mapper.product.CategoryMapper;
import org.example.store.mapper.product.DepartmentMapper;
import org.example.store.mapper.product.ProductMapper;
import org.example.store.repository.product.CategoryRepository;
import org.example.store.repository.product.DepartmentRepository;
import org.example.store.repository.product.ProductRepository;
import org.example.store.repository.cart.CartRepository;
import org.example.store.repository.cart.CartRepositoryImpl;
import org.example.store.service.cart.CartService;
import org.example.store.service.cart.CartServiceImpl;
import org.example.store.service.product.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Config {

    public static ProductStore createStore(String seed) throws IOException {
        DepartmentRepository departmentRepository = new DepartmentRepository(new ArrayList<>());
        CategoryRepository categoryRepository = new CategoryRepository(new ArrayList<>());
        ProductRepository productRepository = new ProductRepository(new ArrayList<>());
        DepartmentMapper departmentMapper = new DepartmentMapper();
        CategoryMapper categoryMapper = new CategoryMapper();

        DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository, departmentMapper);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);


        ProductMapper productMapper = new ProductMapper(departmentService, categoryService);

        ProductService productService = new ProductServiceImpl(productRepository, productMapper, departmentService,
                categoryService);

        ProductHandler productHandler = new ProductHandler(productService);



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

        HomeHandler homeHandler = new HomeHandler();
        return new ProductStore(homeHandler, productHandler, cartHandler);
    }
}
