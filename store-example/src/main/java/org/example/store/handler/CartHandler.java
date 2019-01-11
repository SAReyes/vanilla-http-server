package org.example.store.handler;

import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.exception.BadRequestException;
import org.example.store.dto.exception.NotFoundException;
import org.example.server.RestExchange;
import org.example.store.service.cart.CartService;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartHandler {
    private static final String PATH = "/cart/(.*)|/cart/?";

    private Pattern idPatter;
    private CartService service;

    public CartHandler(CartService service) {
        this.service = service;
        idPatter = Pattern.compile(PATH);
    }

    public Object delete(RestExchange exchange) {
        Optional<Long> cartId = getCartId(exchange.getPath());
        CartRequestDto request = exchange.getBody(CartRequestDto.class);

        return cartId
                .map(
                        id -> service.delete(id, request)
                                .orElseThrow(() -> new NotFoundException("404")) // TODO: Exception handling
                )
                .get();
    }

    public Object post(RestExchange exchange) {
        Optional<Long> cartId = getCartId(exchange.getPath());
        CartRequestDto request = exchange.getBody(CartRequestDto.class);

        return cartId
                .map(
                        id -> service.addProductsToCart(id, request)
                                .orElseThrow(() -> new NotFoundException("404")) // TODO: Exception handling
                )
                .orElseGet(() -> service.create(request));
    }

    public Object get(RestExchange exchange) {
        Optional<Long> cartId = getCartId(exchange.getPath());

        return cartId
                .map(id -> service.find(id).orElseThrow(() -> new NotFoundException("404"))) // TODO: Exception handling
                .get();
    }

    private Optional<Long> getCartId(String path) {
        Matcher matcher = idPatter.matcher(path);

        try {
            if (matcher.find()) {
                return matcher.group(1) == null
                        ? Optional.empty()
                        : Optional.of(Long.valueOf(matcher.group(1)));
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException ignored) {
            throw new BadRequestException("Non-numeric value");
        }
    }
}
