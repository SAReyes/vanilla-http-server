package org.example.handler;

import org.example.domain.Department;
import org.example.domain.Product;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.server.RestExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductHandlerTest {

    private ProductHandler sut;

    @Mock
    private ProductRepository repository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RestExchange exchange;

    private ProductDto product1;
    private ProductDto product2;

    @Before
    public void setUp() {
        Department department1 = new Department();
        department1.setId(1L);
        Department department2 = new Department();
        department2.setId(1L);

        product1 = new ProductDto();
        product1.setId(1L);
        product1.setName("p-1");
        product1.setDepartments(Arrays.asList(department1, department2));
        product2 = new ProductDto();
        product2.setId(2L);
        product2.setName("p-2");
        product2.setDepartments(Collections.singletonList(department2));

        sut = new ProductHandler(repository, departmentRepository, productMapper);
    }

    @Test
    public void gets_all_products() {
        List<Product> domainProducts = new ArrayList<>();

        given(exchange.getParam("name")).willReturn(null);
        given(repository.findAll()).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Arrays.asList(product1, product2));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Arrays.asList(product1, product2));
    }

    @Test
    public void filters_product_by_name() {
        Product domainProduct = new Product();
        domainProduct.setName("domain");

        given(exchange.getParam("name")).willReturn("p-1");
        given(repository.findByName("p-1")).willReturn(Optional.of(domainProduct));
        given(productMapper.toDto(domainProduct)).willReturn(product1);

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(product1);
    }

    @Test
    public void filters_products_by_department() {
        Department department = new Department();
        department.setId(1L);
        List<Product> domainProducts = new ArrayList<>();

        given(exchange.getParam("department")).willReturn("d-1");
        given(departmentRepository.findByName("d-1")).willReturn(Optional.of(department));
        given(repository.findByDepartment(1L)).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Collections.singletonList(product2));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Collections.singletonList(product2));
    }
}
