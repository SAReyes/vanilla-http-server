package org.example.store.repository.cart;

import org.example.store.domain.cart.Cart;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class CartRepositoryImpl implements CartRepository {

    private AtomicLong ids;
    private List<Cart> repository;

    public CartRepositoryImpl(List<Cart> repository, AtomicLong counter) {
        this.repository = repository;
        this.ids = counter;
    }

    @Override
    public Optional<Cart> find(Long id) {
        return repository.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public Cart saveNew(Cart cart) {
        cart.setId(getNextId());

        repository.add(cart);

        return cart;
    }

    @Override
    public Optional<Cart> addProductsToCart(Long id, List<Long> products) {
        Optional<Cart> optionalCart = find(id);

        optionalCart.ifPresent(cart -> cart.getProducts().addAll(products));

        return optionalCart;
    }

    private Long getNextId() {
        return ids.getAndIncrement();
    }
}
