package org.example.store.service.cart;

import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartResponseDto create(CartRequestDto request);
    Optional<CartResponseDto> find(Long id);
    Optional<CartResponseDto> addProductsToCart(Long id, CartRequestDto request);
    Optional<CartResponseDto> deleteProducts(Long id, List<Long> products);
    boolean delete(Long id);
    List<CartResponseDto> findAll();
}
