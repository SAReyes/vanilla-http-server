package org.example.store.config;

import org.example.store.handler.CartHandler;
import org.example.store.handler.ProductHandler;

public class ProductStore {
    private ProductHandler productHandler;
    private CartHandler cartHandler;

    public ProductStore(ProductHandler productHandler, CartHandler cartHandler) {
        this.productHandler = productHandler;
        this.cartHandler = cartHandler;
    }

    public ProductHandler getProductHandler() {
        return productHandler;
    }

    public void setProductHandler(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    public CartHandler getCartHandler() {
        return cartHandler;
    }

    public void setCartHandler(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }
}
