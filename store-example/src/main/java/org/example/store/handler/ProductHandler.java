package org.example.store.handler;

import org.example.server.RestExchange;
import org.example.store.dto.exception.NotFoundException;
import org.example.store.service.product.ProductService;

public class ProductHandler {

    private ProductService service;

    public ProductHandler(ProductService service) {
        this.service = service;
    }

    public Object get(RestExchange exchange) {
        String name = exchange.getParam("name");
        String departmentName = exchange.getParam("department");
        String categoryName = exchange.getParam("category");

        if (name != null) {
            return service.findByName(name)
                    .orElseThrow(() -> new NotFoundException("404"));
        }

        return service.findByDepartmentAndCategory(departmentName, categoryName);
    }
}
