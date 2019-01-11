package org.example.store.repository;

import org.example.store.domain.product.Product;

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

    public List<Product> findByCategory(long category) {
        return getRepository().stream()
                .filter(it -> it.getCategories().stream().anyMatch(c -> c == category))
                .collect(Collectors.toList());
    }

    public List<Product> findByDepartmentAndCategory(long department, long category) {
        return getRepository().stream()
                .filter(it -> it.getDepartments().stream().anyMatch(d -> d == department)
                        && it.getCategories().stream().anyMatch(c -> c == category))
                .collect(Collectors.toList());
    }
}
