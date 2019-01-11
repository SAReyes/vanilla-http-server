package org.example.store.dto.cart;

import java.util.ArrayList;
import java.util.List;

public class CartRequestDto {

    private List<CartProductRequestDto> products;

    public CartRequestDto() {
        products = new ArrayList<>();
    }

    public List<CartProductRequestDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductRequestDto> products) {
        this.products = products;
    }
}
