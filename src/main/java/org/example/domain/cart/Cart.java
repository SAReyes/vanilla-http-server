package org.example.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long id;
    private List<Long> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }
}
