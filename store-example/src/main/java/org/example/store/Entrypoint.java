package org.example.store;

import org.example.store.config.Config;
import org.example.store.config.ProductStore;
import org.example.server.RestServer;

import java.io.IOException;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductStore store = Config.createStore("/data.json");

        new RestServer()
                .nest("/product", RestServer.get(store.getProductHandler()::get))
                .nest(
                        "/cart",
                        RestServer.post(store.getCartHandler()::post),
                        RestServer.get(store.getCartHandler()::get)
                )
                .start();
    }
}
