package org.example.store.repository.product;

import org.example.store.domain.product.GenericItem;

import java.util.List;
import java.util.Optional;

public abstract class GenericItemRepository<T extends GenericItem> {
    private List<T> repository;

    public GenericItemRepository(List<T> repository) {
        this.repository = repository;
    }

    protected List<T> getRepository() {
        return repository;
    }

    public boolean save(T t) {
        return repository.add(t);
    }

    public Optional<T> findById(Long id) {
        return repository.stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }

    public Optional<T> findByName(String name) {
        return repository.stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }

    public List<T> findAll() {
        return repository;
    }
}
