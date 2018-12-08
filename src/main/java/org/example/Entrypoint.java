package org.example;

import org.example.server.RestServer;

import java.io.IOException;

import static org.example.server.RestServer.get;
import static org.example.server.RestServer.post;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        new RestServer()
                .nest("/product",
                        get(exchange ->
                                exchange.getParam("department") + " - " + exchange.getMethod() + " - body: get"),
                        post(exchange ->
                                exchange.getParam("department") + " - " + exchange.getMethod() + " - body: post")
                )
                .start();
    }
}
