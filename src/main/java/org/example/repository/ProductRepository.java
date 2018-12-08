package org.example.repository;

import org.example.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository extends GenericRepository<Product> {
    public ProductRepository(List<Product> repository) {
        super(repository);
    }

    public List<Product> findByDepartment(long department) {
        return getRepository().stream()
                .filter(it -> it.getDepartments().stream().anyMatch(d -> d == department))
                .collect(Collectors.toList());
    }
}
