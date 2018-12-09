package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Category;
import org.example.domain.Department;
import org.example.domain.Product;
import org.example.handler.ProductHandler;
import org.example.mapper.ProductMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.server.RestServer;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.server.RestServer.get;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductRepository productRepository = new ProductRepository(new ArrayList<>());
        DepartmentRepository departmentRepository = new DepartmentRepository(new ArrayList<>());
        CategoryRepository categoryRepository = new CategoryRepository(new ArrayList<>());
        ProductMapper productMapper = new ProductMapper(departmentRepository, categoryRepository);
        ProductHandler productHandler = new ProductHandler(productRepository, departmentRepository, categoryRepository,
                productMapper);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode node = mapper.readTree(Entrypoint.class.getResourceAsStream("/data.json"));

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

        new RestServer()
                .nest("/product", get(productHandler::get))
                .start();
    }
}
