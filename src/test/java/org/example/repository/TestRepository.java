package org.example.repository;

import java.util.List;

public class TestRepository extends GenericRepository<TestItem> {
    public TestRepository(List<TestItem> repository) {
        super(repository);
    }
}
