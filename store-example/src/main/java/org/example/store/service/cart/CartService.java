package org.example.store.service.cart;

import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;

import java.util.Optional;

public interface CartService {
    CartResponseDto create(CartRequestDto request);
    Optional<CartResponseDto> find(Long id);
    Optional<CartResponseDto> addProductsToCart(Long id, CartRequestDto request);
    Optional<CartResponseDto> delete(Long id, CartRequestDto request);
}
