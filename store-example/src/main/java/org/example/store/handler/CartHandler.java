package org.example.store.handler;

import org.example.server.HttpResponse;
import org.example.store.dto.cart.CartPathRequestDto;
import org.example.store.dto.cart.CartProductRequestDto;
import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.exception.BadRequestException;
import org.example.store.dto.exception.NotFoundException;
import org.example.server.RestExchange;
import org.example.store.service.cart.CartService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CartHandler {
    private static final String PATH = "/cart/([^/]*)(/items/([0-9](,[0-9])*))?|/cart/?";

    private Pattern idPatter;
    private CartService service;

    public CartHandler(CartService service) {
        this.service = service;
        idPatter = Pattern.compile(PATH);
    }

    public Object delete(RestExchange exchange) {
        CartPathRequestDto pathVariables = getCartId(exchange.getPath());

        if (pathVariables.getCartId().isPresent()
                && pathVariables.getProducts().isEmpty() && service.delete(pathVariables.getCartId().get())) {
            return HttpResponse.OK;
        }

        return pathVariables.getCartId()
                .map(
                        id ->
                                service.deleteProducts(id, pathVariables.getProducts())
                                        .orElseThrow(() -> new NotFoundException("404"))
                )
                .get();
    }

    public Object post(RestExchange exchange) {
        CartPathRequestDto pathVariables = getCartId(exchange.getPath());
        CartRequestDto request = exchange.getBody(CartRequestDto.class);

        return pathVariables.getCartId()
                .map(
                        id -> service.addProductsToCart(id, request)
                                .orElseThrow(() -> new NotFoundException("404"))
                )
                .orElseGet(() -> service.create(request));
    }

    public Object get(RestExchange exchange) {
        CartPathRequestDto pathVariables = getCartId(exchange.getPath());

        if (!pathVariables.getCartId().isPresent()) return service.findAll();

        return pathVariables.getCartId()
                .map(id -> service.find(id).orElseThrow(() -> new NotFoundException("404")))
                .get();
    }

    private CartPathRequestDto getCartId(String path) {
        Matcher matcher = idPatter.matcher(path);

        try {
            if (matcher.find()) {
                CartPathRequestDto request = new CartPathRequestDto();

                request.setCartId(matcher.group(1));
                if (matcher.group(3) != null) {
                    request.setProducts(Arrays.stream(matcher.group(3).split(","))
                            .map(Long::valueOf)
                            .collect(toList()));
                }

                return request;
            } else {
                return new CartPathRequestDto();
            }
        } catch (NumberFormatException ignored) {
            throw new BadRequestException("Non-numeric value");
        }
    }
}
