package org.example.store.repository;

import org.example.store.domain.product.Category;

import java.util.List;

public class CategoryRepository extends GenericRepository<Category> {
    public CategoryRepository(List<Category> repository) {
        super(repository);
    }
}
