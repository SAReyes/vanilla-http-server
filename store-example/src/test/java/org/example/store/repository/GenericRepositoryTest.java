package org.example.store.repository;

import org.assertj.core.api.Assertions;
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
public class GenericRepositoryTest {
    private TestRepository sut;

    @Mock
    private List<TestItem> repo;

    private TestItem foo;
    private TestItem bar;

    @Before
    public void setUp() {
        foo = new TestItem();
        foo.setId(1L);
        foo.setName("foo");
        bar = new TestItem();
        bar.setId(2L);
        bar.setName("bar");

        sut = new TestRepository(repo);
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

        Optional<TestItem> result = sut.findById(2);

        Assertions.assertThat(result).get().isEqualTo(bar);
    }

    @Test
    public void find_by_name() {
        given(repo.stream()).willReturn(Stream.of(foo, bar));

        Optional<TestItem> result = sut.findByName("bar");

        Assertions.assertThat(result).get().isEqualTo(bar);
    }

    @Test
    public void find_all() {
        List<TestItem> result = sut.findAll();

        Assertions.assertThat(result).isEqualTo(repo);
    }
}
