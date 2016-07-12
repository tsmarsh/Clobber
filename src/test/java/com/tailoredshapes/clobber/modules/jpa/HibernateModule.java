package com.tailoredshapes.clobber.modules.jpa;

import com.google.inject.*;
import com.tailoredshapes.clobber.dao.CategorySaver;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.dao.Saver;
import com.tailoredshapes.clobber.dao.hibernate.HibernateDAO;
import com.tailoredshapes.clobber.extractors.IdExtractor;
import com.tailoredshapes.clobber.filters.TFilter;
import com.tailoredshapes.clobber.model.*;
import com.tailoredshapes.clobber.parsers.InventoryParser;
import com.tailoredshapes.clobber.parsers.Parser;
import com.tailoredshapes.clobber.repositories.FinderFactory;
import com.tailoredshapes.clobber.repositories.Repository;
import com.tailoredshapes.clobber.repositories.finders.categories.HibernateFindCategoryByFullName;
import com.tailoredshapes.clobber.repositories.finders.inventories.HibernateFindInventoryById;
import com.tailoredshapes.clobber.repositories.finders.metrictype.HibernateFindMetricTypeByName;
import com.tailoredshapes.clobber.repositories.finders.users.HibernateFindUserById;
import com.tailoredshapes.clobber.repositories.finders.users.HibernateFindUserByName;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

public class HibernateModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<DAO<Inventory>>() {})
                .to(new TypeLiteral<HibernateDAO<Inventory>>() {});

        binder.bind(new TypeLiteral<HibernateDAO<Inventory>>() {});


        binder.bind(new TypeLiteral<DAO<User>>() {})
                .to(new TypeLiteral<HibernateDAO<User>>() {});


        binder.bind(new TypeLiteral<HibernateDAO<User>>() {});


        binder.bind(new TypeLiteral<DAO<Category>>() {})
                .to(new TypeLiteral<HibernateDAO<Category>>() {});

        binder.bind(new TypeLiteral<HibernateDAO<Category>>() {});


        binder.bind(new TypeLiteral<DAO<Metric>>() {})
                .to(new TypeLiteral<HibernateDAO<Metric>>() {});

        binder.bind(new TypeLiteral<HibernateDAO<Metric>>() {});

        binder.bind(new TypeLiteral<DAO<MetricType>>() {})
                .to(new TypeLiteral<HibernateDAO<MetricType>>() {});

        binder.bind(new TypeLiteral<HibernateDAO<MetricType>>() {});

        binder.bind(new TypeLiteral<FinderFactory<Category, String, EntityManager>>() {})
                .to(HibernateFindCategoryByFullName.class);

        binder.bind(new TypeLiteral<FinderFactory<MetricType, String, EntityManager>>() {})
                .to(HibernateFindMetricTypeByName.class);

        binder.bind(new TypeLiteral<FinderFactory<User, String, EntityManager>>() {})
                .to(HibernateFindUserByName.class);

        binder.bind(new TypeLiteral<FinderFactory<User, Long, EntityManager>>() {})
                .to(HibernateFindUserById.class);

        binder.bind(new TypeLiteral<FinderFactory<Inventory, Long, EntityManager>>() {})
                .to(HibernateFindInventoryById.class);

        binder.bind(new TypeLiteral<Parser<Inventory>>() {})
                .to(new TypeLiteral<InventoryParser<EntityManager, EntityManager>>() {});

        binder.bind(new TypeLiteral<Saver<Category>>() {})
                .to(new TypeLiteral<CategorySaver<EntityManager>>() {});


        binder.bind(new TypeLiteral<Repository<Inventory, ?>>() {})
                .to(new TypeLiteral<Repository<Inventory, EntityManager>>() {});

        binder.bind(new TypeLiteral<Repository<User, ?>>() {})
                .to(new TypeLiteral<Repository<User, EntityManager>>() {});

        binder.bind(new TypeLiteral<Repository<Category, ?>>() {})
                .to(new TypeLiteral<Repository<Category, EntityManager>>() {});

        binder.bind(new TypeLiteral<Repository<Metric, ?>>() {})
                .to(new TypeLiteral<Repository<Metric, EntityManager>>() {});

        binder.bind(new TypeLiteral<Repository<MetricType, ?>>() {})
                .to(new TypeLiteral<Repository<MetricType, EntityManager>>() {});

        binder.bind(new TypeLiteral<TFilter<Long, User, ?>>() {})
                .to(new TypeLiteral<TFilter<Long, User, EntityManager>>() {});

        binder.bind(new TypeLiteral<TFilter<String, User, ?>>() {})
                .to(new TypeLiteral<TFilter<String, User, EntityManager>>() {});

        binder.bind(new TypeLiteral<TFilter<Long, Inventory, ?>>() {})
                .to(new TypeLiteral<TFilter<Long, Inventory, EntityManager>>() {});
    }

    @Provides
    @Singleton
    public TFilter<Long, User, EntityManager> providesUserFromIdFilter(Provider<Parser<User>> parser,
                                                                       Provider<IdExtractor<Long, User>> idExtractor,
                                                                       Provider<Repository<User, EntityManager>> repository,
                                                                       Provider<FinderFactory<User, Long, EntityManager>> finderFactory) {
        return new TFilter<>(parser, idExtractor, finderFactory, repository, User.class, "user");
    }

    @Provides
    @Singleton
    public TFilter<String, User, EntityManager> providesUserFromNameFilter(Provider<Parser<User>> parser,
                                                                           Provider<IdExtractor<String, User>> idExtractor,
                                                                           Provider<Repository<User, EntityManager>> repository,
                                                                           Provider<FinderFactory<User, String, EntityManager>> finderFactory) {
        return new TFilter<>(parser, idExtractor, finderFactory, repository, User.class, "user");
    }

    @Provides
    @Singleton
    public TFilter<Long, Inventory, EntityManager> providesInventoryFromIdFilter(Provider<Parser<Inventory>> parser,
                                                                                 Provider<IdExtractor<Long, Inventory>> idExtractor,
                                                                                 Provider<Repository<Inventory, EntityManager>> repository,
                                                                                 Provider<FinderFactory<Inventory, Long, EntityManager>> finderFactory) {
        return new TFilter<>(parser, idExtractor, finderFactory, repository, Inventory.class, "inventory");
    }
}
