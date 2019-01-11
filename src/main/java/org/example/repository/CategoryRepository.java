package org.example.repository;

import org.example.domain.product.Category;

import java.util.List;

public class CategoryRepository extends GenericRepository<Category> {
    public CategoryRepository(List<Category> repository) {
        super(repository);
    }
}
