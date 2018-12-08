package org.example.handler;

import org.example.repository.ProductRepository;
import org.example.server.RestExchange;

public class ProductHandler {

    private ProductRepository repository;

    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Object get(RestExchange exchange) {
        String name = exchange.getParam("name");
        String department = exchange.getParam("department");

        if (name != null) {
            return repository.findByName(name).orElse(null);
        }

        if (department != null) {
            return repository.findByDepartment(department);
        } else {
            return repository.findAll();
        }

    }
}
