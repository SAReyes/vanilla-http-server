package org.example.repository;

import org.example.domain.Category;

import java.util.List;

public class CategoryRepository extends GenericRepository<Category> {
    public CategoryRepository(List<Category> repository) {
        super(repository);
    }
}
