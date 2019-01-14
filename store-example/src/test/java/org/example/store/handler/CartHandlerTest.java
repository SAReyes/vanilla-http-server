package org.example.store.handler;

import org.example.server.RestExchange;
import org.example.store.dto.cart.CartProductRequestDto;
import org.example.store.dto.cart.CartRequestDto;
import org.example.store.dto.cart.CartResponseDto;
import org.example.store.dto.exception.BadRequestException;
import org.example.store.dto.exception.NotFoundException;
import org.example.store.service.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
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
        CartProductRequestDto p1 = new CartProductRequestDto();
        p1.setId(2L);
        requestDto.getProducts().add(p1);
    }

    @Test
    public void should_use_the_service_to_retrieve_an_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(any())).willReturn(Optional.of(responseDto));

        sut.get(exchange);

        verify(service).find(1L);
    }

    @Test
    public void should_use_the_service_to_retrieve_all_carts() {
        given(exchange.getPath()).willReturn("/cart/");
        given(service.findAll()).willReturn(singletonList(responseDto));

        sut.get(exchange);

        verify(service).findAll();
    }

    @Test
    public void should_retrieve_an_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(any())).willReturn(Optional.of(responseDto));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    public void retrieving_a_cart_when_the_cart_does_not_exist_should_raise_not_found_exception() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.find(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.get(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void retrieving_a_cart_when_the_id_is_not_a_number_should_raise_bad_request_exception() {
        given(exchange.getPath()).willReturn("/cart/foo");

        assertThatThrownBy(() -> sut.get(exchange)).isInstanceOf(BadRequestException.class);
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

    @Test
    public void saving_an_item_in_a_cart_when_the_id_is_not_a_number_should_raise_bad_request_exception() {
        given(exchange.getPath()).willReturn("/cart/foo");

        assertThatThrownBy(() -> sut.post(exchange)).isInstanceOf(BadRequestException.class);
    }

    @Test
    public void saving_an_item_in_a_cart_when_the_cart_is_not_found_should_rasie_not_found_exception() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.addProductsToCart(any(), any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.post(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void should_delete_products_from_an_existing_cart() {
        given(exchange.getPath()).willReturn("/cart/1/items/1,2");
        given(service.deleteProducts(any(), any())).willReturn(Optional.of(responseDto));

        Object result = sut.delete(exchange);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    public void deleting_items_from_a_cart_should_use_the_service() {
        given(exchange.getPath()).willReturn("/cart/1/items/1,2");
        given(service.deleteProducts(any(), any())).willReturn(Optional.of(responseDto));

        sut.delete(exchange);

        verify(service).deleteProducts(1L, asList(1L, 2L));
    }

    @Test
    public void deleting_request_without_products_should_delete_the_cart() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(service.delete(any())).willReturn(true);

        sut.delete(exchange);

        verify(service).delete(1L);
    }

    @Test
    public void deleting_items_from_a_cart_when_the_cart_does_not_exist_should_raise_not_found_exception() {
        given(exchange.getPath()).willReturn("/cart/1");
        given(exchange.getBody(CartRequestDto.class)).willReturn(requestDto);
        given(service.deleteProducts(any(), any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.delete(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleting_items_when_the_id_is_not_a_number_should_raise_bad_request_exception() {
        given(exchange.getPath()).willReturn("/cart/foo");

        assertThatThrownBy(() -> sut.delete(exchange)).isInstanceOf(BadRequestException.class);
    }
}