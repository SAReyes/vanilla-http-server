package org.example.server;

import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;

public class RestExchange {
    private HttpExchange exchange;
    private Map<String, String> params;

    public RestExchange(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public String getParam(String param) {
        if (this.params == null) this.params = splitQuery(exchange.getRequestURI());

        return params.get(param);
    }

    public HttpMethod getMethod() {
        return HttpMethod.valueOf(exchange.getRequestMethod());
    }

    private Map<String, String> splitQuery(URI uri) {
        if (uri == null || uri.getQuery() == null || uri.getQuery().isEmpty()) {
            return Collections.emptyMap();
        }
        return Arrays.stream(uri.getQuery().split("&"))
                .map(this::splitQueryParameter)
                .collect(Collectors.groupingBy(it -> ((Map.Entry) it).getKey().toString(),
                        mapping(it -> ((Map.Entry) it).getValue().toString(), joining(",")))
                );
    }

    private Map.Entry<String, String> splitQueryParameter(String param) {
        String[] strings = param.split("=");
        String key = strings.length > 0 ? strings[0] : null;
        String value = strings.length > 1 ? strings[1] : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
