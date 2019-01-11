package org.example.store.dto.product;

import org.example.store.domain.product.Category;
import org.example.store.domain.product.Department;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private Long id;
    private String name;
    private List<Department> departments;
    private List<Category> categories;

    public ProductDto() {
        departments = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
