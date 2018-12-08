package org.example.handler;

import org.example.repository.ProductRepository;
import org.example.server.RestExchange;

public class ProductHandler {

    private ProductRepository repository;

    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Object get(RestExchange exchange) {
        String nameParam = exchange.getParam("name");

        if (nameParam == null) {
            return repository.findAll();
        } else {
            return repository.findByName(nameParam).orElse(null);
        }
    }

    public Object post(RestExchange exchange) {
        return "Hello, post";
    }
}
