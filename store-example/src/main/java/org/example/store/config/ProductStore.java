package org.example.store.config;

import org.example.store.handler.CartHandler;
import org.example.store.handler.HomeHandler;
import org.example.store.handler.ProductHandler;

public class ProductStore {
    private HomeHandler homeHandler;
    private ProductHandler productHandler;
    private CartHandler cartHandler;

    public ProductStore(HomeHandler homeHandler, ProductHandler productHandler, CartHandler cartHandler) {
        this.homeHandler = homeHandler;
        this.productHandler = productHandler;
        this.cartHandler = cartHandler;
    }

    public HomeHandler getHomeHandler() {
        return homeHandler;
    }

    public ProductHandler getProductHandler() {
        return productHandler;
    }

    public CartHandler getCartHandler() {
        return cartHandler;
    }
}
