package org.example.store.service.product;

import org.example.store.domain.GenericTestDomain;
import org.example.store.dto.GenericTestDto;
import org.example.store.mapper.product.GenericItemMapper;
import org.example.store.repository.product.GenericItemRepository;

public class GenericTestService extends GenericItemServiceImpl<GenericTestDomain, GenericTestDto> {

    public GenericTestService(GenericItemRepository<GenericTestDomain> repository,
                                 GenericItemMapper<GenericTestDomain, GenericTestDto> mapper) {
        super(repository, mapper);
    }
}
