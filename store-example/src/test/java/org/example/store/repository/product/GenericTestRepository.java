package org.example.store.repository.product;

import org.example.store.domain.GenericTestDomain;

import java.util.List;

public class GenericTestRepository extends GenericItemRepository<GenericTestDomain> {
    public GenericTestRepository(List<GenericTestDomain> repository) {
        super(repository);
    }
}
