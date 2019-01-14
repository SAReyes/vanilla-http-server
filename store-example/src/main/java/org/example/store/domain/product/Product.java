package org.example.store.domain.product;

import java.util.ArrayList;
import java.util.List;

public class Product extends GenericItem {

    private Double price;
    private List<Long> departments;
    private List<Long> categories;

    public Product() {
        departments = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
