package org.example.store.handler;

import org.example.store.dto.exception.NotFoundException;
import org.example.store.dto.product.ProductResponseDto;
import org.example.server.RestExchange;
import org.example.store.service.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductHandlerTest {

    private ProductHandler sut;

    @Mock
    private ProductService service;

    @Mock
    private RestExchange exchange;

    private ProductResponseDto responseDto;

    @Before
    public void setUp() {
        responseDto = new ProductResponseDto();

        sut = new ProductHandler(service);
    }

    @Test
    public void should_find_a_product_by_its_name() {
        given(exchange.getParam("name")).willReturn("foo");
        given(service.findByName(any())).willReturn(Optional.of(responseDto));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    public void finding_a_product_by_name_should_use_the_service() {
        given(exchange.getParam("name")).willReturn("foo");
        given(service.findByName(any())).willReturn(Optional.of(responseDto));

        sut.get(exchange);

        verify(service).findByName("foo");
    }

    @Test
    public void not_finding_a_product_by_name_should_raise_not_found_exception() {
        given(exchange.getParam("name")).willReturn("foo");
        given(service.findByName(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.get(exchange)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void should_find_products_by_department_and_category() {
        given(exchange.getParam("department")).willReturn("departmentName");
        given(exchange.getParam("category")).willReturn("categoryName");
        given(service.findByDepartmentAndCategory(any(), any())).willReturn(asList(responseDto, responseDto));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(asList(responseDto, responseDto));
    }

    @Test
    public void finding_products_by_department_and_category_should_use_the_service() {
        given(exchange.getParam("department")).willReturn("departmentName");
        given(exchange.getParam("category")).willReturn("categoryName");
        given(service.findByDepartmentAndCategory(any(), any())).willReturn(asList(responseDto, responseDto));

        sut.get(exchange);

        verify(service).findByDepartmentAndCategory("departmentName", "categoryName");
    }
}
