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
    public List<Cart> findAll() {
        return repository;
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

    @Override
    public Optional<Cart> deleteProductsFromCart(Long id, List<Long> products) {
        return find(id)
                .map(cart -> {
                    products.forEach(product -> cart.getProducts().remove(product));
                    return cart;
                });
    }

    @Override
    public boolean delete(Long id) {
        return find(id)
                .map(cart -> repository.remove(cart))
                .orElse(false);
    }

    private Long getNextId() {
        return ids.getAndIncrement();
    }
}
