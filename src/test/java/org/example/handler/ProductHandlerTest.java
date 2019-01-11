package org.example.handler;

import org.example.domain.product.Category;
import org.example.domain.product.Department;
import org.example.domain.product.Product;
import org.example.dto.product.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProductRepository;
import org.example.server.RestExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RestExchange exchange;

    @Mock
    private List<Product> domainProducts;

    private ProductDto foo;
    private ProductDto bar;

    @Before
    public void setUp() {
        Department department1 = new Department();
        department1.setId(1L);
        Department department2 = new Department();
        department2.setId(1L);

        foo = new ProductDto();
        foo.setId(1L);
        foo.setName("foo");
        foo.setDepartments(Arrays.asList(department1, department2));
        bar = new ProductDto();
        bar.setId(2L);
        bar.setName("bar");
        bar.setDepartments(Collections.singletonList(department2));

        sut = new ProductHandler(repository, departmentRepository, categoryRepository, productMapper);
    }

    @Test
    public void gets_all_products() {
        given(exchange.getParam("name")).willReturn(null);
        given(repository.findAll()).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Arrays.asList(foo, bar));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Arrays.asList(foo, bar));
    }

    @Test
    public void filters_product_by_name() {
        Product domainProduct = new Product();
        domainProduct.setName("domain");

        given(exchange.getParam("name")).willReturn("foo");
        given(repository.findByName("foo")).willReturn(Optional.of(domainProduct));
        given(productMapper.toDto(domainProduct)).willReturn(foo);

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(foo);
    }

    @Test
    public void filters_products_by_department() {
        Department department = new Department();
        department.setId(2L);

        given(exchange.getParam("department")).willReturn("d-1");
        given(departmentRepository.findByName("d-1")).willReturn(Optional.of(department));
        given(repository.findByDepartment(2L)).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Collections.singletonList(bar));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Collections.singletonList(bar));
    }

    @Test
    public void filters_products_by_category() {
        Category category = new Category();
        category.setId(1L);

        given(exchange.getParam("category")).willReturn("c-1");
        given(categoryRepository.findByName("c-1")).willReturn(Optional.of(category));
        given(repository.findByCategory(1L)).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Collections.singletonList(bar));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Collections.singletonList(bar));
    }

    @Test
    public void filters_products_by_category_and_name() {
        Category category = new Category();
        category.setId(1L);
        Department department = new Department();
        department.setId(2L);

        given(exchange.getParam("category")).willReturn("c-1");
        given(categoryRepository.findByName("c-1")).willReturn(Optional.of(category));
        given(exchange.getParam("department")).willReturn("d-1");
        given(departmentRepository.findByName("d-1")).willReturn(Optional.of(department));
        given(repository.findByDepartmentAndCategory(2L, 1L)).willReturn(domainProducts);
        given(productMapper.toDtoList(domainProducts)).willReturn(Collections.singletonList(bar));

        Object result = sut.get(exchange);

        assertThat(result).isEqualTo(Collections.singletonList(bar));
    }
}
