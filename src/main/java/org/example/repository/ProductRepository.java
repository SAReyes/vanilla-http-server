package org.example.repository;

import org.example.domain.Product;

import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = products;
    }

    public boolean save(Product product) {
        return products.add(product);
    }

    public Optional<Product> find(long id) {
        return products.stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }

    public Optional<Product> findByName(String name) {
        return findAll().stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }

    public List<Product> findAll() {
        return products;
    }
}
