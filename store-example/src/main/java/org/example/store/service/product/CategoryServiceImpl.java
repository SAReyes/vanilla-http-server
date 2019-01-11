package org.example.store.service.product;

import org.example.store.domain.product.Category;
import org.example.store.dto.product.CategoryDto;
import org.example.store.mapper.product.CategoryMapper;
import org.example.store.repository.product.CategoryRepository;

public class CategoryServiceImpl extends GenericItemServiceImpl<Category, CategoryDto> implements CategoryService {

    public CategoryServiceImpl(CategoryRepository repository,
                                  CategoryMapper mapper) {
        super(repository, mapper);
    }
}
