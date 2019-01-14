package org.example.store.handler;

import org.example.server.RestExchange;

public class HomeHandler {

    public Object get(RestExchange exchange) {
        return "Head to the following url to use this api: " +
                "https://app.swaggerhub.com/apis-docs/SAReyes/vanilla-store-example/1.0.0";
    }
}
