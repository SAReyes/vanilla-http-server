package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Product;
import org.example.handler.ProductHandler;
import org.example.repository.ProductRepository;
import org.example.server.RestServer;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.server.RestServer.get;

public class Entrypoint {

    public static void main(String[] args) throws IOException {
        ProductRepository productRepository = new ProductRepository(new ArrayList<>());
        ProductHandler productHandler = new ProductHandler(productRepository);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode node = mapper.readTree(Entrypoint.class.getResourceAsStream("/data.json"));

        node.findValue("products").elements().forEachRemaining(it -> {
            try {
                productRepository.save(mapper.treeToValue(it, Product.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        new RestServer()
                .nest("/product", get(productHandler::get))
                .start();
    }
}
