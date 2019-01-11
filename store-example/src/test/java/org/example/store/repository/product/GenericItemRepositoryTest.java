package org.example.store.repository.product;

import org.assertj.core.api.Assertions;
import org.example.store.domain.GenericTestDomain;
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
public class GenericItemRepositoryTest {
    private GenericTestRepository sut;

    @Mock
    private List<GenericTestDomain> repo;

    private GenericTestDomain foo;
    private GenericTestDomain bar;

    @Before
    public void setUp() {
        foo = new GenericTestDomain();
        foo.setId(1L);
        foo.setName("foo");
        bar = new GenericTestDomain();
        bar.setId(2L);
        bar.setName("bar");

        sut = new GenericTestRepository(repo);
    }

    @Test
    public void adds_a_product() {
        given(repo.add(foo)).willReturn(true);

        boolean result = sut.save(foo);

        assertThat(result).isTrue();
        verify(repo).add(foo);
    }

    @Test
    public void find_by_id() {
        given(repo.stream()).willReturn(Stream.of(foo, bar));

        Optional<GenericTestDomain> result = sut.findById(2L);

        Assertions.assertThat(result).get().isEqualTo(bar);
    }

    @Test
    public void find_by_name() {
        given(repo.stream()).willReturn(Stream.of(foo, bar));

        Optional<GenericTestDomain> result = sut.findByName("bar");

        Assertions.assertThat(result).get().isEqualTo(bar);
    }

    @Test
    public void find_all() {
        List<GenericTestDomain> result = sut.findAll();

        Assertions.assertThat(result).isEqualTo(repo);
    }
}
