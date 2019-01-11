package org.example.store.mapper.product;

import org.example.store.domain.product.Category;
import org.example.store.dto.product.CategoryDto;

public class CategoryMapper extends GenericItemMapper<Category, CategoryDto> {
    @Override
    protected CategoryDto newDto() {
        return new CategoryDto();
    }

    @Override
    protected Category newDomain() {
        return new Category();
    }
}
