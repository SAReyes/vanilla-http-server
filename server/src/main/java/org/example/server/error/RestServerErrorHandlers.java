package org.example.server.error;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RestServerErrorHandlers {

    private Map<Class<? extends Throwable>, Function<? extends Throwable, Object>> handlers;

    public RestServerErrorHandlers() {
        handlers = new HashMap<>();
    }

    public <T extends Throwable> RestServerErrorHandlers register(Class<T> clazz, Function<T, Object> handler) {
        handlers.put(clazz, handler);
        return this;
    }

    public <T extends Throwable> Function<? extends Throwable, Object> getHandler(Class<T> clazz) {
        return handlers.get(clazz);
    }
}
