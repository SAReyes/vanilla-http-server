package org.example.store.domain.product;

import java.util.ArrayList;
import java.util.List;

public class Product extends GenericItem {
    private List<Long> departments;
    private List<Long> categories;

    public Product() {
        departments = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<Long> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Long> departments) {
        this.departments = departments;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
