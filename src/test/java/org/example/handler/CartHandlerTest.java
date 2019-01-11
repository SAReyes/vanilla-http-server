package org.example.handler;

import org.example.dto.cart.CartRequestDto;
import org.example.dto.cart.CartResponseDto;
import org.example.dto.exception.BadRequestException;
import org.example.dto.exception.NotFoundException;
import org.example.server.RestExchange;
import org.example.service.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartHandlerTest {

    private CartHandler sut;

    @Mock
    private CartService service;

    @Mock
    private RestExchange exchange;

    private CartRequestDto requestDto;
    private CartResponseDto responseDto;

    @Before
    public void setUp() {
        sut = new CartHandler(service);

        requestDto = new CartRequestDto();
        responseDto = new CartResponseDto();
    }

    @Test
    public void should_use_the_service_to_retrieve_an_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(any())).willReturn(Optional.of(responseDto));

        sut.get(exchange);

        verify(service).find(1L);
    }

    @Test
    public void should_retrieve_an_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(any())).willReturn(Optional.of(responseDto));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    public void should_raise_not_found_exception_no_cart_is_found() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.get(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void should_raise_bad_request_exception_when_retrieving_an_item_and_the_id_is_not_a_number() {
        given(exchange.getPath()).willReturn("/cart/foo");

        assertThatThrownBy(() -> sut.get(exchange)).isInstanceOf(BadRequestException.class);
    }

    @Test
    public void should_raise_bad_request_exception_when_saving_an_item_and_the_id_is_not_a_number() {
        given(exchange.getPath()).willReturn("/cart/foo");

        assertThatThrownBy(() -> sut.post(exchange)).isInstanceOf(BadRequestException.class);
    }

    @Test
    public void should_raise_not_found_exception_when_saving_an_item_and_the_cart_does_not_exist() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.addProductsToCart(any(), any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.post(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void should_add_products_to_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(exchange.getBody(CartRequestDto.class)).willReturn(requestDto);
        given(service.addProductsToCart(any(), any())).willReturn(Optional.of(responseDto));

        sut.post(exchange);

        verify(service).addProductsToCart(1L, requestDto);
    }

    @Test
    public void should_create_a_new_cart_when_no_id_is_present() {
        given(exchange.getPath()).willReturn("/cart");
        given(exchange.getBody(CartRequestDto.class)).willReturn(requestDto);
        given(service.create(any())).willReturn(responseDto);

        sut.post(exchange);

        verify(service).create(requestDto);
    }
}