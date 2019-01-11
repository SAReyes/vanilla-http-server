package org.example.store.repository;

import org.example.store.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryTest {

    private ProductRepository sut;

    @Mock
    private List<Product> repo;

    private Product foo;
    private Product bar;
    private Product baz;

    @Before
    public void setUp() {
        foo = new Product();
        foo.setId(1L);
        foo.setName("foo");
        foo.setDepartments(Arrays.asList(3L, 4L));
        foo.setCategories(Collections.singletonList(5L));
        bar = new Product();
        bar.setId(2L);
        bar.setName("bar");
        bar.setDepartments(Collections.singletonList(3L));
        bar.setCategories(Collections.singletonList(6L));
        baz = new Product();
        baz.setId(3L);
        baz.setName("baz");
        baz.setDepartments(Collections.singletonList(4L));
        baz.setCategories(Collections.singletonList(6L));

        sut = new ProductRepository(repo);
    }

    @Test
    public void find_by_department() {
        given(repo.stream()).willReturn(Stream.of(foo, bar, baz));

        List<Product> result = sut.findByDepartment(4);

        assertThat(result).isEqualTo(Arrays.asList(foo, baz));
    }

    @Test
    public void find_by_category() {
        given(repo.stream()).willReturn(Stream.of(foo, bar, baz));

        List<Product> result = sut.findByCategory(6);

        assertThat(result).isEqualTo(Arrays.asList(bar, baz));
    }

    @Test
    public void find_by_department_and_category() {
        given(repo.stream()).willReturn(Stream.of(foo, bar, baz));

        List<Product> result = sut.findByDepartmentAndCategory(3,6);

        assertThat(result).isEqualTo(Collections.singletonList(bar));
    }
}
