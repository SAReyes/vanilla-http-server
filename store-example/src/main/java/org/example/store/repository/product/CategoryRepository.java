package org.example.store.repository.product;

import org.example.store.domain.product.Category;

import java.util.List;

public class CategoryRepository extends GenericItemRepository<Category> {
    public CategoryRepository(List<Category> repository) {
        super(repository);
    }
}
