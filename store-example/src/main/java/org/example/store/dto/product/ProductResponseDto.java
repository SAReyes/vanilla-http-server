package org.example.store.dto.product;

import java.util.ArrayList;
import java.util.List;

public class ProductResponseDto extends GenericItemDto {

    private List<DepartmentDto> departments;
    private List<CategoryDto> categories;

    public ProductResponseDto() {
        departments = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<DepartmentDto> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentDto> departments) {
        this.departments = departments;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
