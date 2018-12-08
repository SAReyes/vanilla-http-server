package org.example.repository;

import org.example.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Before
    public void setUp() {
        sut = new ProductRepository(repo);
    }

    @Test
    public void adds_a_product() {
        Product product = dummyProduct(1, "foo");
        given(repo.add(product)).willReturn(true);

        boolean result = sut.save(product);

        assertThat(result).isTrue();
        verify(repo).add(product);
    }

    @Test
    public void find_by_id() {
        Product product1 = dummyProduct(1, "foo");
        Product product2 = dummyProduct(2, "bar");
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        Optional<Product> result = sut.find(2);

        assertThat(result).get().isEqualTo(product2);
    }

    @Test
    public void find_by_name() {
        Product product1 = dummyProduct(1, "foo");
        Product product2 = dummyProduct(2, "bar");
        given(repo.stream()).willReturn(Stream.of(product1, product2));

        Optional<Product> result = sut.findByName("bar");

        assertThat(result).get().isEqualTo(product2);
    }

    @Test
    public void find_all() {
        List<Product> result = sut.findAll();

        assertThat(result).isEqualTo(repo);
    }

    private Product dummyProduct(long id, String name) {
        Product product = new Product();

        product.setId(id);
        product.setName(name);

        return product;
    }
}
