package org.example.store.service.product;

import org.example.logger.LoggerFactory;
import org.example.store.domain.product.GenericItem;
import org.example.store.dto.product.GenericItemDto;
import org.example.store.mapper.product.GenericItemMapper;
import org.example.store.repository.product.GenericItemRepository;

import java.util.Optional;
import java.util.logging.Logger;

public abstract class GenericItemServiceImpl<D extends GenericItem, T extends GenericItemDto>
        implements GenericItemService<T> {

    private static Logger logger = LoggerFactory.getLogger(GenericItemServiceImpl.class);

    private GenericItemRepository<D> repository;
    private GenericItemMapper<D, T> mapper;

    public GenericItemServiceImpl(GenericItemRepository<D> repository, GenericItemMapper<D, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected GenericItemRepository<D> getRepository() {
        return repository;
    }

    @Override
    public Optional<T> find(Long id) {
        logger.finest(() -> this.getClass().getSimpleName() + " - " + " find by id=" + id);

        return repository.findById(id)
                .map(item -> mapper.toDto(item));
    }

    @Override
    public Optional<T> findByName(String name) {
        logger.finest(() -> this.getClass().getSimpleName() + " - " + " find by name=" + name);
        return repository.findByName(name)
                .map(item -> mapper.toDto(item));
    }
}
