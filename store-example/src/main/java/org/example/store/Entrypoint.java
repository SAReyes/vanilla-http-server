package org.example.store;

import org.example.server.RestServer;
import org.example.store.config.Config;
import org.example.store.config.ProductStore;

import java.io.IOException;

import static org.example.server.RestServer.*;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductStore store = Config.createStore("/data.json");

        new RestServer()
                .nest("/product", get(store.getProductHandler()::get))
                .nest(
                        "/cart",
                        post(store.getCartHandler()::post),
                        get(store.getCartHandler()::get),
                        delete(store.getCartHandler()::delete)
                )
                .start();
    }
}
