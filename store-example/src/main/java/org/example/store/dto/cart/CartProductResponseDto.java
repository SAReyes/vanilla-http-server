package org.example.store.dto.cart;

import org.example.store.dto.product.CategoryDto;
import org.example.store.dto.product.DepartmentDto;

import java.util.ArrayList;
import java.util.List;

public class CartProductResponseDto {

    private Long id;
    private Long quantity;
    private String name;
    private Double price;
    private List<DepartmentDto> departments;
    private List<CategoryDto> categories;

    public CartProductResponseDto() {
        departments = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "CartProductResponseDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", departments=" + departments +
                ", categories=" + categories +
                '}';
    }
}
