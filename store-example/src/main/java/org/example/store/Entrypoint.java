package org.example.store;

import org.example.server.HttpResponse;
import org.example.server.RestServer;
import org.example.server.error.RestServerErrorHandlers;
import org.example.store.config.Config;
import org.example.store.config.ProductStore;
import org.example.store.dto.exception.BadRequestException;
import org.example.store.dto.exception.NotFoundException;

import java.io.IOException;

import static org.example.server.RestServer.*;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductStore store = Config.createStore("/data.json");

        RestServerErrorHandlers errorHandlers = new RestServerErrorHandlers()
                .register(NotFoundException.class, e -> HttpResponse.NOT_FOUND)
                .register(BadRequestException.class, e -> HttpResponse.BAD_REQUEST);

        new RestServer(Integer.valueOf(System.getenv().getOrDefault("PORT", "8080")))
                .setErrorHandlers(errorHandlers)
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
