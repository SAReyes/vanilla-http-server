package org.example.domain;

import java.util.List;
import java.util.Objects;

public class Product extends GenericItem {
    private List<Long> departments;
    private List<Long> categories;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDepartments(), product.getDepartments()) &&
                Objects.equals(getCategories(), product.getCategories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDepartments(), getCategories());
    }
}
