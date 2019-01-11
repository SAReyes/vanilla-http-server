package org.example.store.repository.cart;

import org.example.store.domain.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CartRepositoryTest {

    private CartRepository sut;

    private Cart foo;
    private Cart bar;

    @Before
    public void setUp() {
        foo = new Cart();
        foo.setId(1L);
        bar = new Cart();
        bar.setId(2L);

        List<Cart> carts = new ArrayList<>(asList(foo, bar));

        sut = new CartRepositoryImpl(carts, new AtomicLong(3));
    }

    @Test
    public void should_find_a_cart_by_id() {
        Optional<Cart> result = sut.find(2L);

        assertThat(result).contains(bar);
    }

    @Test
    public void should_return_empty_optional_if_the_id_is_not_found() {
        Optional<Cart> result = sut.find(3L);

        assertThat(result).isEmpty();
    }

    @Test
    public void saving_a_new_cart_should_increment_the_counter() {
        Cart result = sut.saveNew(new Cart());

        assertThat(result.getId()).isEqualTo(3L);
    }

    @Test
    public void adding_products_to_a_non_existing_cart_should_return_an_empty_optional() {
        Optional<Cart> result = sut.addProductsToCart(3L, emptyList());

        assertThat(result).isEmpty();
    }

    @Test
    public void adding_products_to_a_cart_() {
        Optional<Cart> result = sut.addProductsToCart(1L, asList(2L, 3L));

        assertThat(result).get().hasFieldOrPropertyWithValue("products", asList(2L, 3L));
    }

    @Test
    public void should_delete_products_from_a_cart() {
        foo.setProducts(new ArrayList<>(asList(1L, 2L, 2L, 3L, 4L)));

        Optional<Cart> result = sut.deleteProductsFromCart(1L, asList(2L, 3L));

        assertThat(result).get().hasFieldOrPropertyWithValue("products", asList(1L, 2L, 4L));
    }

    @Test
    public void deleting_products_from_a_non_existing_cart_should_return_an_empty_optional() {
        Optional<Cart> result = sut.deleteProductsFromCart(3L, emptyList());

        assertThat(result).isEmpty();
    }
}
