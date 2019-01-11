package org.example.service.cart;

import org.example.domain.cart.Cart;
import org.example.dto.cart.CartProductRequestDto;
import org.example.dto.cart.CartRequestDto;
import org.example.dto.cart.CartResponseDto;
import org.example.mapper.cart.CartMapper;
import org.example.repository.cart.CartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    private CartService sut;

    @Mock
    private CartRepository repository;
    @Mock
    private CartMapper mapper;

    private CartRequestDto requestDto;
    private CartResponseDto responseDto;
    private Cart domain;

    @Before
    public void setUp() {
        sut = new CartServiceImpl(repository, mapper);

        requestDto = new CartRequestDto();
        responseDto = new CartResponseDto();
        domain = new Cart();
    }

    @Test
    public void creating_a_cart_should_map_the_request_correctly() {
        sut.create(requestDto);

        verify(mapper).toDomain(requestDto);
    }

    @Test
    public void creating_a_cart_should_map_the_response_correctly() {
        given(repository.saveNew(any())).willReturn(domain);

        sut.create(requestDto);

        verify(mapper).toDto(domain);
    }

    @Test
    public void creating_a_cart_should_save_the_object_in_the_repository() {
        given(mapper.toDomain(any())).willReturn(domain);

        sut.create(requestDto);

        verify(repository).saveNew(domain);
    }

    @Test
    public void creating_a_cart_will_return_the_expected_response() {
        given(mapper.toDto(any())).willReturn(responseDto);

        CartResponseDto result = sut.create(requestDto);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    public void finding_a_cart_calls_the_repository() {
        given(repository.find(any())).willReturn(Optional.of(domain));

        sut.find(1L);

        verify(repository).find(1L);
    }

    @Test
    public void finding_a_cart_will_map_it_correctly() {
        given(repository.find(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(responseDto);

        sut.find(1L);

        verify(mapper).toDto(domain);
    }

    @Test
    public void not_finding_a_cart_when_fetching_a_cart_will_return_empty_optional() {
        given(repository.find(any())).willReturn(Optional.empty());

        Optional<CartResponseDto> result = sut.find(1L);

        assertThat(result).isEmpty();
    }

    @Test
    public void finding_a_cart_will_return_it() {
        given(repository.find(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(responseDto);

        Optional<CartResponseDto> result = sut.find(1L);

        assertThat(result).hasValue(responseDto);
    }

    @Test
    public void not_finding_a_cart_when_adding_products_to_a_cart_will_return_empty_optional() {
        given(repository.addProductsToCart(any(), any())).willReturn(Optional.empty());

        Optional<CartResponseDto> result = sut.addProductsToCart(1L, requestDto);

        assertThat(result).isEmpty();
    }

    @Test
    public void adding_products_to_a_cart_will_add_the_products_using_the_repository() {
        CartProductRequestDto p1 = new CartProductRequestDto();
        p1.setId(2L);
        p1.setQuantity(1L);
        CartProductRequestDto p2 = new CartProductRequestDto();
        p2.setId(3L);
        p2.setQuantity(2L);
        requestDto.setProducts(asList(p1, p2));
        domain.setId(1L);

        given(repository.addProductsToCart(any(), any())).willReturn(Optional.of(domain));

        sut.addProductsToCart(1L, requestDto);

        verify(repository).addProductsToCart(1L, asList(2L, 3L, 3L));
    }

    @Test
    public void adding_products_to_a_cart_will_map_the_response() {
        given(repository.addProductsToCart(any(), any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(responseDto);

        sut.addProductsToCart(1L, requestDto);

        verify(mapper).toDto(domain);
    }

    @Test
    public void adding_products_to_a_cart_will_return_the_expected_response() {
        given(repository.addProductsToCart(any(), any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(responseDto);

        Optional<CartResponseDto> result = sut.addProductsToCart(1L, requestDto);

        assertThat(result).contains(responseDto);
    }
}