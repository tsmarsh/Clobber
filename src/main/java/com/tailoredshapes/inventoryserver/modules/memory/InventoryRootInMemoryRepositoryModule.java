package com.tailoredshapes.inventoryserver.modules.memory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.inventoryserver.model.*;
import com.tailoredshapes.inventoryserver.repositories.Repository;
import com.tailoredshapes.inventoryserver.repositories.memory.InMemoryRepository;

import java.util.Map;

public class InventoryRootInMemoryRepositoryModule implements Module {
    @Override
    public void configure(Binder binder) {

        binder.bind(new TypeLiteral<Repository<Inventory, Map<Long, Inventory>>>() {})
                .to(new TypeLiteral<InMemoryRepository<Inventory>>() {});

        binder.bind(new TypeLiteral<Repository<Category, Map<Long, Category>>>() {})
                .to(new TypeLiteral<InMemoryRepository<Category>>() {});

        binder.bind(new TypeLiteral<Repository<User, Map<Long, User>>>() {})
                .to(new TypeLiteral<InMemoryRepository<User>>() {});

        binder.bind(new TypeLiteral<Repository<Metric, Map<Long, Metric>>>() {})
                .to(new TypeLiteral<InMemoryRepository<Metric>>() {});

        binder.bind(new TypeLiteral<Repository<MetricType, Map<Long, MetricType>>>() {})
                .to(new TypeLiteral<InMemoryRepository<MetricType>>() {});
    }
}