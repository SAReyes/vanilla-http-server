package org.example.store.mapper.cart;

import org.example.store.domain.cart.Cart;
import org.example.store.dto.cart.CartProductResponseDto;
import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;
import org.example.store.service.product.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;

public class CartMapper {

    private ProductService productService;

    public CartMapper(ProductService productService) {
        this.productService = productService;
    }

    public Cart toDomain(CartRequestDto requestDto) {
        List<Long> products = requestDto.getProducts().stream()
                .flatMapToLong(
                        it -> LongStream.range(0, it.getQuantity())
                                .map(ignored -> it.getId())
                )
                .boxed()
                .collect(toList());

        Cart cart = new Cart();
        cart.setProducts(products);
        return cart;
    }

    public CartResponseDto toDto(Cart domain) {
        List<CartProductResponseDto> cartProducts = mapIdToQuantity(domain)
                .entrySet().stream()
                .map(it -> productService.find(it.getKey()).map(product -> {
                    CartProductResponseDto responseDto = new CartProductResponseDto();

                    responseDto.setId(product.getId());
                    responseDto.setName(product.getName());
                    responseDto.setCategories(product.getCategories());
                    responseDto.setDepartments(product.getDepartments());
                    responseDto.setQuantity(it.getValue());

                    return responseDto;
                }))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        CartResponseDto response = new CartResponseDto();
        response.setProducts(cartProducts);
        response.setId(domain.getId());
        return response;
    }

    private Map<Long, Long> mapIdToQuantity(Cart domain) {
        return domain.getProducts().stream()
                .collect(groupingBy( it -> it, counting()));
    }
}
