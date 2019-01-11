package org.example.service.cart;

import org.example.domain.cart.Cart;
import org.example.dto.cart.CartRequestDto;
import org.example.dto.cart.CartResponseDto;
import org.example.mapper.cart.CartMapper;
import org.example.repository.cart.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartResponseDto create(CartRequestDto request) {
        Cart cart = cartMapper.toDomain(request);

        cart = cartRepository.saveNew(cart);

        return cartMapper.toDto(cart);
    }

    @Override
    public Optional<CartResponseDto> find(Long id) {
        return cartRepository.find(id)
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public Optional<CartResponseDto> addProductsToCart(Long id, CartRequestDto request) {
        List<Long> productIds = request.getProducts().stream()
                .flatMapToLong(product ->
                        LongStream.range(0, product.getQuantity())
                                .map(it -> product.getId())
                )
                .boxed()
                .collect(Collectors.toList());

        return cartRepository.addProductsToCart(id, productIds)
                .map(cart -> cartMapper.toDto(cart));
    }
}
