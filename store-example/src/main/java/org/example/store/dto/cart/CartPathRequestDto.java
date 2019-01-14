package org.example.store.dto.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartPathRequestDto {

    private Long cartId;
    private List<Long> products;

    public CartPathRequestDto() {
        cartId = null;
        products = new ArrayList<>();
    }

    public Optional<Long> getCartId() {
        return Optional.ofNullable(cartId);
    }

    public void setCartId(String cartId) {
        this.cartId = cartId == null || cartId.isEmpty()
                ? null
                : Long.valueOf(cartId);
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }
}
