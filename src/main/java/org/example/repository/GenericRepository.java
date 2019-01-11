package org.example.repository;

import org.example.domain.product.GenericItem;

import java.util.List;
import java.util.Optional;

public class GenericRepository<T extends GenericItem> {
    private List<T> repository;

    GenericRepository(List<T> repository) {
        this.repository = repository;
    }

    protected List<T> getRepository() {
        return repository;
    }

    public boolean save(T t) {
        return repository.add(t);
    }

    public Optional<T> findById(long id) {
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
