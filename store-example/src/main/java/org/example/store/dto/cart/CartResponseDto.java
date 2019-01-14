package org.example.store.dto.cart;

import java.util.ArrayList;
import java.util.List;

public class CartResponseDto {

    private Long id;
    private Double totalPrice;
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

    public Double getTotalPrice() {
        return products.stream()
                .mapToDouble(products -> products.getPrice() * products.getQuantity())
                .sum();
    }

    public List<CartProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductResponseDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartResponseDto{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
