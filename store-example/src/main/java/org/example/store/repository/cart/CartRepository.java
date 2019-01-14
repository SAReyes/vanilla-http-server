package org.example.store.repository.cart;

import org.example.store.domain.cart.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> find(Long id);
    Cart saveNew(Cart cart);
    Optional<Cart> addProductsToCart(Long id, List<Long> products);
    Optional<Cart> deleteProductsFromCart(Long id, List<Long> products);
    boolean delete(Long id);
    List<Cart> findAll();
}
