package org.example.store.service.cart;

import org.example.store.domain.cart.Cart;
import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;
import org.example.store.mapper.cart.CartMapper;
import org.example.store.repository.cart.CartRepository;

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
        return cartRepository.addProductsToCart(id, getProductsIds(request))
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public Optional<CartResponseDto> delete(Long id, CartRequestDto request) {
        return cartRepository.deleteProductsFromCart(id, getProductsIds(request))
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public boolean delete(Long id) {
        return cartRepository.delete(id);
    }

    private List<Long> getProductsIds(CartRequestDto request) {
        return request.getProducts().stream()
                .flatMapToLong(product ->
                        LongStream.range(0, product.getQuantity())
                                .map(it -> product.getId())
                )
                .boxed()
                .collect(Collectors.toList());
    }
}
