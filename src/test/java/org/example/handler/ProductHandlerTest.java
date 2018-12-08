package org.example.handler;

import org.example.domain.Department;
import org.example.domain.Product;
import org.example.repository.ProductRepository;
import org.example.server.RestExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductHandlerTest {

    private ProductHandler sut;

    @Mock
    private ProductRepository repository;

    @Mock
    private RestExchange exchange;

    private Product product1;
    private Product product2;
    private Department department1;
    private Department department2;

    @Before
    public void setUp() {
        department1 = new Department();
        department1.setName("d-1");
        department2 = new Department();
        department2.setName("d-2");
        product1 = new Product();
        product1.setId(1);
        product1.setName("p-1");
        product1.setDepartments(Arrays.asList(department1, department2));
        product2 = new Product();
        product2.setId(2);
        product2.setName("p-2");
        product2.setDepartments(Collections.singletonList(department2));

        sut = new ProductHandler(repository);
    }

    @Test
    public void gets_all_products() {
        given(exchange.getParam("name")).willReturn(null);
        given(repository.findAll()).willReturn(Arrays.asList(product1, product2));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Arrays.asList(product1, product2));
    }

    @Test
    public void filters_product_by_name() {
        given(exchange.getParam("name")).willReturn("p-1");
        given(repository.findByName("p-1")).willReturn(Optional.of(product1));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(product1);
    }

    @Test
    public void filters_products_by_department() {
        given(exchange.getParam("department")).willReturn("d-1");
        given(repository.findByDepartment("d-1")).willReturn(Collections.singletonList(product2));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Collections.singletonList(product2));
    }
}
