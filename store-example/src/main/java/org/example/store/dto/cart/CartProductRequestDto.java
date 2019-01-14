package org.example.store.dto.cart;

public class CartProductRequestDto {

    private Long id;
    private Long quantity;

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

    @Override
    public String toString() {
        return "CartProductRequestDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
