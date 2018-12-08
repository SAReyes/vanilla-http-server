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
public class GenericRepositoryTest {
    private TestRepository sut;

    @Mock
    private List<TestItem> repo;

    private TestItem item1;
    private TestItem item2;

    @Before
    public void setUp() {
        item1 = new TestItem();
        item1.setId(1L);
        item1.setName("p-1");
        item2 = new TestItem();
        item2.setId(2L);
        item2.setName("p-2");

        sut = new TestRepository(repo);
    }

    @Test
    public void adds_a_product() {
        given(repo.add(item1)).willReturn(true);

        boolean result = sut.save(item1);

        assertThat(result).isTrue();
        verify(repo).add(item1);
    }

    @Test
    public void find_by_id() {
        given(repo.stream()).willReturn(Stream.of(item1, item2));

        Optional<TestItem> result = sut.findById(2);

        assertThat(result).get().isEqualTo(item2);
    }

    @Test
    public void find_by_name() {
        given(repo.stream()).willReturn(Stream.of(item1, item2));

        Optional<TestItem> result = sut.findByName("p-2");

        assertThat(result).get().isEqualTo(item2);
    }

    @Test
    public void find_all() {
        List<TestItem> result = sut.findAll();

        assertThat(result).isEqualTo(repo);
    }
}
