package org.example.store.service.product;


import org.example.store.domain.product.Category;
import org.example.store.domain.product.Product;
import org.example.store.dto.product.CategoryDto;
import org.example.store.dto.product.DepartmentDto;
import org.example.store.dto.product.ProductResponseDto;
import org.example.store.mapper.product.ProductMapper;
import org.example.store.repository.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService sut;

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private CategoryService categoryService;

    private ProductResponseDto foo;
    private ProductResponseDto bar;

    private Product fooDomain;
    private Product barDomain;

    @Before
    public void setUp() {
        sut = new ProductServiceImpl(repository, mapper, departmentService, categoryService);
        foo = new ProductResponseDto();
        bar = new ProductResponseDto();
        fooDomain = new Product();
        barDomain = new Product();
    }

    @Test
    public void when_providing_no_department_nor_category_should_find_all_products() {
        given(repository.findAll()).willReturn(asList(fooDomain, barDomain));
        given(mapper.toDtoList(asList(fooDomain, barDomain))).willReturn(asList(foo, bar));

        List<ProductResponseDto> result = sut.findByDepartmentAndCategory(null, null);

        assertThat(result).containsExactly(foo, bar);
    }

    @Test
    public void when_providing_category_should_find_products_by_category() {
        CategoryDto category = new CategoryDto();
        category.setId(1L);

        given(repository.findByCategory(1L)).willReturn(asList(fooDomain, barDomain));
        given(mapper.toDtoList(asList(fooDomain, barDomain))).willReturn(asList(foo, bar));
        given(categoryService.findByName("category")).willReturn(Optional.of(category));

        List<ProductResponseDto> result = sut.findByDepartmentAndCategory(null, "category");

        assertThat(result).containsExactly(foo, bar);
    }


    @Test
    public void when_providing_department_should_find_products_by_department() {
        DepartmentDto department = new DepartmentDto();
        department.setId(1L);

        given(repository.findByDepartment(1L)).willReturn(asList(fooDomain, barDomain));
        given(mapper.toDtoList(asList(fooDomain, barDomain))).willReturn(asList(foo, bar));
        given(departmentService.findByName("department")).willReturn(Optional.of(department));

        List<ProductResponseDto> result = sut.findByDepartmentAndCategory("department", null);

        assertThat(result).containsExactly(foo, bar);
    }

    @Test
    public void when_providing_department_and_category_should_find_products_by_department_and_category() {
        CategoryDto category = new CategoryDto();
        category.setId(1L);
        DepartmentDto department = new DepartmentDto();
        department.setId(2L);

        given(repository.findByDepartmentAndCategory(2L,1L)).willReturn(asList(fooDomain, barDomain));
        given(mapper.toDtoList(asList(fooDomain, barDomain))).willReturn(asList(foo, bar));
        given(categoryService.findByName("category")).willReturn(Optional.of(category));
        given(departmentService.findByName("department")).willReturn(Optional.of(department));

        List<ProductResponseDto> result = sut.findByDepartmentAndCategory("department", "category");

        assertThat(result).containsExactly(foo, bar);
    }
}