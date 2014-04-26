package com.tailoredshapes.inventoryserver;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.inventoryserver.dao.CategorySaver;
import com.tailoredshapes.inventoryserver.dao.DAO;
import com.tailoredshapes.inventoryserver.dao.Saver;
import com.tailoredshapes.inventoryserver.dao.memory.InMemoryDAO;
import com.tailoredshapes.inventoryserver.model.*;
import com.tailoredshapes.inventoryserver.parsers.InventoryParser;
import com.tailoredshapes.inventoryserver.parsers.Parser;
import com.tailoredshapes.inventoryserver.repositories.FinderFactory;
import com.tailoredshapes.inventoryserver.repositories.Repository;
import com.tailoredshapes.inventoryserver.repositories.finders.categories.InMemoryFindByFullName;
import com.tailoredshapes.inventoryserver.repositories.finders.metrictype.InMemoryFindByName;
import com.tailoredshapes.inventoryserver.repositories.memory.InMemoryRepository;
import com.tailoredshapes.inventoryserver.repositories.memory.InMemoryUserInventoryRepository;
import com.tailoredshapes.inventoryserver.security.RSA;
import com.tailoredshapes.inventoryserver.security.SHA;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryModule implements Module {
    @Override
    public void configure(Binder binder) {

        binder.bind(new TypeLiteral<Map<Long, User>>() {}).to(new TypeLiteral<ConcurrentHashMap<Long, User>>() {}).in(Singleton.class);
        binder.bind(new TypeLiteral<Map<Long, Inventory>>() {}).to(new TypeLiteral<ConcurrentHashMap<Long, Inventory>>() {}).in(Singleton.class);
        binder.bind(new TypeLiteral<Map<Long, Metric>>() {}).to(new TypeLiteral<ConcurrentHashMap<Long, Metric>>() {}).in(Singleton.class);
        binder.bind(new TypeLiteral<Map<Long, MetricType>>() {}).to(new TypeLiteral<ConcurrentHashMap<Long, MetricType>>() {}).in(Singleton.class);
        binder.bind(new TypeLiteral<Map<Long, Category>>() {}).to(new TypeLiteral<ConcurrentHashMap<Long, Category>>() {}).in(Singleton.class);

        binder.bind(new TypeLiteral<DAO<Inventory>>() {})
                .to(new TypeLiteral<InMemoryDAO<Inventory, SHA>>() {});

        binder.bind(new TypeLiteral<DAO<User>>() {})
                .to(new TypeLiteral<InMemoryDAO<User, RSA>>() {});


        binder.bind(new TypeLiteral<DAO<Category>>() {})
                .to(new TypeLiteral<InMemoryDAO<Category, SHA>>() {});


        binder.bind(new TypeLiteral<DAO<Metric>>() {})
                .to(new TypeLiteral<InMemoryDAO<Metric, SHA>>() {});

        binder.bind(new TypeLiteral<DAO<MetricType>>() {})
                .to(new TypeLiteral<InMemoryDAO<MetricType, SHA>>() {});


        binder.bind(new TypeLiteral<Repository<Inventory, ?>>() {})
                .to(new TypeLiteral<Repository<Inventory, Map<Long, Inventory>>>() {});

        binder.bind(new TypeLiteral<Repository<User, ?>>() {})
                .to(new TypeLiteral<Repository<User, Map<Long, User>>>() {});

        binder.bind(new TypeLiteral<Repository<Category, ?>>() {})
                .to(new TypeLiteral<Repository<Category, Map<Long, Category>>>() {});

        binder.bind(new TypeLiteral<Repository<Metric, ?>>() {})
                .to(new TypeLiteral<Repository<Metric, Map<Long, Metric>>>() {});

        binder.bind(new TypeLiteral<Repository<MetricType, ?>>() {})
                .to(new TypeLiteral<Repository<MetricType, Map<Long, MetricType>>>() {});

        binder.bind(new TypeLiteral<Repository<Inventory, Map<Long, Inventory>>>() {})
                .to(new TypeLiteral<InMemoryUserInventoryRepository>() {});

        binder.bind(new TypeLiteral<Repository<Category, Map<Long, Category>>>() {})
                .to(new TypeLiteral<InMemoryRepository<Category>>() {});

        binder.bind(new TypeLiteral<Repository<User, Map<Long, User>>>() {})
                .to(new TypeLiteral<InMemoryRepository<User>>() {});

        binder.bind(new TypeLiteral<Repository<Metric, Map<Long, Metric>>>() {})
                .to(new TypeLiteral<InMemoryRepository<Metric>>() {});

        binder.bind(new TypeLiteral<Repository<MetricType, Map<Long, MetricType>>>() {})
                .to(new TypeLiteral<InMemoryRepository<MetricType>>() {});

        binder.bind(new TypeLiteral<FinderFactory<Category, String, Map<Long, Category>>>() {})
                .to(InMemoryFindByFullName.class);

        binder.bind(new TypeLiteral<FinderFactory<MetricType, String, Map<Long, MetricType>>>() {})
                .to(InMemoryFindByName.class);

        binder.bind(new TypeLiteral<Parser<Inventory>>() {})
                .to(new TypeLiteral<InventoryParser<Map<Long, Category>, Map<Long, MetricType>>>() {});

        binder.bind(new TypeLiteral<Saver<Category>>() {})
                .to(new TypeLiteral<CategorySaver<Map<Long, Category>>>() {});

    }
}
