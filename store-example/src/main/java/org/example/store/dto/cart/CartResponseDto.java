package org.example.store.dto.cart;

import java.util.ArrayList;
import java.util.List;

public class CartResponseDto {

    private Long id;
    private List<CartProductResponseDto> products;

    public CartResponseDto() {
        products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductResponseDto> products) {
        this.products = products;
    }
}
