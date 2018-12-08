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

        sut = new ProductRepository(repo);
    }

    @Test
    public void adds_a_product() {
        given(repo.add(product1)).willReturn(true);

        boolean result = sut.save(product1);

        assertThat(result).isTrue();
        verify(repo).add(product1);
    }

    @Test
    public void find_by_id() {
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        Optional<Product> result = sut.find(2);

        assertThat(result).get().isEqualTo(product2);
    }

    @Test
    public void find_by_name() {
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        Optional<Product> result = sut.findByName("p-2");

        assertThat(result).get().isEqualTo(product2);
    }

    @Test
    public void find_by_department() {
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        List<Product> result = sut.findByDepartment("d-1");

        assertThat(result).isEqualTo(Collections.singletonList(product1));
    }

    @Test
    public void find_all() {
        List<Product> result = sut.findAll();

        assertThat(result).isEqualTo(repo);
    }
}
