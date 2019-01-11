package org.example.store.service.product;

import org.example.store.dto.product.GenericItemDto;

import java.util.Optional;

public interface GenericItemService<T extends GenericItemDto> {
    Optional<T> find(Long id);

    Optional<T> findByName(String name);
}
