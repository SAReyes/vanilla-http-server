package org.example;

import org.example.config.Config;
import org.example.config.ProductStore;
import org.example.server.RestServer;

import java.io.IOException;

import static org.example.server.RestServer.get;
import static org.example.server.RestServer.post;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductStore store = Config.createStore("/data.json");

        new RestServer()
                .nest("/product", get(store.getProductHandler()::get))
                .nest("/cart", post(store.getCartHandler()::post))
                .start();
    }
}
