package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;

public class RestExchange {
    private HttpExchange exchange;
    private ObjectMapper mapper;
    private String body;
    private Map<String, String> params;

    public RestExchange(HttpExchange exchange, ObjectMapper mapper) {
        this.exchange = exchange;
        this.mapper = mapper;
        this.body = getBodyAsString();
    }

    public String getParam(String param) {
        if (this.params == null) this.params = splitQuery(exchange.getRequestURI());

        return params.get(param);
    }

    public HttpMethod getMethod() {
        try {
            return HttpMethod.valueOf(exchange.getRequestMethod());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
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

    public String getPath() {
        return exchange.getRequestURI().toString();
    }

    public <T> T getBody(Class<T> clazz) {
        try {
            String body = this.getBody();

            body = body.trim().equals("") ? "{}" : body;

            return mapper.readValue(body, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBody() {
        return body;
    }


    @Override
    public String toString() {
        return getMethod() + " " + exchange.getRequestURI();
    }

    private String getBodyAsString() {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            int b;
            StringBuilder buf = new StringBuilder();
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }

            br.close();
            isr.close();

            return buf.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map.Entry<String, String> splitQueryParameter(String param) {
        String[] strings = param.split("=");
        String key = strings.length > 0 ? strings[0] : null;
        String value = strings.length > 1 ? strings[1] : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
