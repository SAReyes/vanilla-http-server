package org.example.service.cart;

import org.example.dto.cart.CartRequestDto;
import org.example.dto.cart.CartResponseDto;

import java.util.Optional;

public interface CartService {
    CartResponseDto create(CartRequestDto request);
    Optional<CartResponseDto> find(Long id);
    Optional<CartResponseDto> addProductsToCart(Long id, CartRequestDto request);
}
