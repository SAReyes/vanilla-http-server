package org.example.store.mapper.product;

import org.example.store.domain.product.GenericItem;
import org.example.store.dto.product.GenericItemDto;

public abstract class GenericItemMapper<D extends GenericItem, T extends GenericItemDto> {

    protected abstract T newDto();
    protected abstract D newDomain();

    public T toDto(D domain) {
        T dto = newDto();

        dto.setId(domain.getId());
        dto.setName(domain.getName());

        return dto;
    }

    public D toDomain(T dto) {
        D domain = newDomain();

        domain.setId(dto.getId());
        domain.setName(dto.getName());

        return domain;
    }
}
