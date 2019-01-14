package org.example.store.service.cart;

import org.example.logger.LoggerFactory;
import org.example.store.domain.cart.Cart;
import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;
import org.example.store.mapper.cart.CartMapper;
import org.example.store.repository.cart.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

public class CartServiceImpl implements CartService {

    private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private CartRepository cartRepository;
    private CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartResponseDto create(CartRequestDto request) {
        logger.finest(() -> "Creating cart request " + request);

        Cart cart = cartMapper.toDomain(request);

        cart = cartRepository.saveNew(cart);

        return cartMapper.toDto(cart);
    }

    @Override
    public Optional<CartResponseDto> find(Long id) {
        logger.finest(() -> "Find cart " + id);

        return cartRepository.find(id)
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public List<CartResponseDto> findAll(){
        logger.finest(() -> "Find all carts");

        return cartRepository.findAll().stream()
                .map(cart -> cartMapper.toDto(cart))
                .collect(toList());
    }

    @Override
    public Optional<CartResponseDto> addProductsToCart(Long id, CartRequestDto request) {
        logger.finest(() -> "Add products to cart " + id + " - " + request);

        return cartRepository.addProductsToCart(id, getProductsIds(request))
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public Optional<CartResponseDto> delete(Long id, CartRequestDto request) {
        logger.finest(() -> "Delete products from cart " + id + " - " + request);

        return cartRepository.deleteProductsFromCart(id, getProductsIds(request))
                .map(cart -> cartMapper.toDto(cart));
    }

    @Override
    public boolean delete(Long id) {
        logger.finest(() -> "Delete cart " + id);

        return cartRepository.delete(id);
    }

    private List<Long> getProductsIds(CartRequestDto request) {
        return request.getProducts().stream()
                .flatMapToLong(product ->
                        LongStream.range(0, product.getQuantity())
                                .map(it -> product.getId())
                )
                .boxed()
                .collect(toList());
    }
}
