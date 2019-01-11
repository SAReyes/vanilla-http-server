package org.example.repository.cart;

import org.example.domain.cart.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> find(Long id);
    Cart saveNew(Cart cart);
    Optional<Cart> addProductsToCart(Long id, List<Long> products);
}
