package org.example.repository;

import org.example.domain.Department;
import org.example.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryTest {

    private ProductRepository sut;

    @Mock
    private List<Product> repo;

    private Product product1;
    private Product product2;

    @Before
    public void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("p-1");
        product1.setDepartments(Arrays.asList(1L, 2L));
        product2 = new Product();
        product2.setId(2L);
        product2.setName("p-2");
        product2.setDepartments(Collections.singletonList(2L));

        sut = new ProductRepository(repo);
    }

    @Test
    public void find_by_department() {
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        List<Product> result = sut.findByDepartment(1);

        assertThat(result).isEqualTo(Collections.singletonList(product1));
    }
}
