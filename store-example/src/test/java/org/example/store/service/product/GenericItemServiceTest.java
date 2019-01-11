package org.example.store.service.product;

import org.example.store.domain.GenericTestDomain;
import org.example.store.dto.GenericTestDto;
import org.example.store.mapper.product.GenericItemMapper;
import org.example.store.repository.product.GenericItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GenericItemServiceTest {

    @Mock
    private GenericItemRepository<GenericTestDomain> repository;
    @Mock
    private GenericItemMapper<GenericTestDomain, GenericTestDto> mapper;

    private GenericTestService sut;

    private GenericTestDomain domain;
    private GenericTestDto dto;

    @Before
    public void setUp() {
        sut = new GenericTestService(repository, mapper);
        domain = new GenericTestDomain();
        dto = new GenericTestDto();
    }

    @Test
    public void should_find_item_by_id() {
        given(repository.findById(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        Optional<GenericTestDto> result = sut.find(1L);

        assertThat(result).contains(dto);
    }

    @Test
    public void finding_an_item_by_id_should_use_the_repository() {
        given(repository.findById(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        sut.find(1L);

        verify(repository).findById(1L);
    }

    @Test
    public void finding_an_item_by_id_should_map_the_correct_object() {
        given(repository.findById(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        sut.find(1L);

        verify(mapper).toDto(domain);
    }

    @Test
    public void finding_an_non_existing_item_by_id_should_return_an_empty_optional() {
        given(repository.findById(any())).willReturn(Optional.empty());

        Optional<GenericTestDto> result = sut.find(1L);

        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_item_by_name() {
        given(repository.findByName(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        Optional<GenericTestDto> result = sut.findByName("foo");

        assertThat(result).contains(dto);
    }

    @Test
    public void finding_an_item_by_name_should_use_the_repository() {
        given(repository.findByName(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        sut.findByName("foo");

        verify(repository).findByName("foo");
    }

    @Test
    public void finding_an_item_by_name_should_map_the_correct_object() {
        given(repository.findByName(any())).willReturn(Optional.of(domain));
        given(mapper.toDto(any())).willReturn(dto);

        sut.findByName("foo");

        verify(mapper).toDto(domain);
    }

    @Test
    public void finding_an_non_existing_item_by_name_should_return_an_empty_optional() {
        given(repository.findByName(any())).willReturn(Optional.empty());

        Optional<GenericTestDto> result = sut.findByName("foo");

        assertThat(result).isEmpty();
    }
}
