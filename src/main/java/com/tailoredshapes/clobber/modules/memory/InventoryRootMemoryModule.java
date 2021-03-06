package com.tailoredshapes.clobber.modules.memory;

import com.google.inject.*;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.dao.*;
import com.tailoredshapes.clobber.dao.memory.InMemoryDAO;
import com.tailoredshapes.clobber.encoders.Encoder;
import com.tailoredshapes.clobber.encoders.SHAEncoder;
import com.tailoredshapes.clobber.extractors.IdExtractor;
import com.tailoredshapes.clobber.extractors.InventoryIdExtractor;
import com.tailoredshapes.clobber.filters.TFilter;
import com.tailoredshapes.clobber.model.*;
import com.tailoredshapes.clobber.parsers.InventoryParser;
import com.tailoredshapes.clobber.parsers.Parser;
import com.tailoredshapes.clobber.repositories.FinderFactory;
import com.tailoredshapes.clobber.repositories.InventoryCategoryPredicate;
import com.tailoredshapes.clobber.repositories.Repository;
import com.tailoredshapes.clobber.repositories.finders.categories.InMemoryFindCategoryByFullName;
import com.tailoredshapes.clobber.repositories.finders.inventories.InMemoryFindInventoryById;
import com.tailoredshapes.clobber.repositories.finders.metrictype.InMemoryFindMetricTypeByName;
import com.tailoredshapes.clobber.repositories.finders.users.InMemoryFindUserById;
import com.tailoredshapes.clobber.repositories.memory.InMemoryRepository;
import com.tailoredshapes.clobber.responders.JSONListResponder;
import com.tailoredshapes.clobber.responders.JSONResponder;
import com.tailoredshapes.clobber.responders.Responder;
import com.tailoredshapes.clobber.security.SHA;
import com.tailoredshapes.clobber.serialisers.*;
import com.tailoredshapes.clobber.servlets.Pestlet;
import com.tailoredshapes.clobber.urlbuilders.InventoryUrlBuilder;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import com.tailoredshapes.clobber.validators.InventoryValidator;
import com.tailoredshapes.clobber.validators.UserValidator;
import com.tailoredshapes.clobber.validators.Validator;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class InventoryRootMemoryModule extends PrivateModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Repository<Category, Map<Long, Category>>>() {
        })
                .to(new TypeLiteral<InMemoryRepository<Category>>() {
                });

        bind(new TypeLiteral<Repository<Inventory, Map<Long, Inventory>>>() {
        })
                .to(new TypeLiteral<InMemoryRepository<Inventory>>() {
                });

        bind(new TypeLiteral<Repository<User, Map<Long, User>>>() {
        })
                .to(new TypeLiteral<InMemoryRepository<User>>() {
                });

        bind(new TypeLiteral<Repository<Metric, Map<Long, Metric>>>() {
        })
                .to(new TypeLiteral<InMemoryRepository<Metric>>() {
                });

        bind(new TypeLiteral<Repository<MetricType, Map<Long, MetricType>>>() {
        })
                .to(new TypeLiteral<InMemoryRepository<MetricType>>() {
                });


        ///

        bind(new TypeLiteral<DAO<Inventory>>() {
        })
                .to(new TypeLiteral<InMemoryDAO<Inventory>>() {
                });

        bind(new TypeLiteral<InMemoryDAO<Inventory>>() {
        });


        bind(new TypeLiteral<DAO<User>>() {
        })
                .to(new TypeLiteral<InMemoryDAO<User>>() {
                });


        bind(new TypeLiteral<InMemoryDAO<User>>() {
        });


        bind(new TypeLiteral<DAO<Category>>() {
        })
                .to(new TypeLiteral<InMemoryDAO<Category>>() {
                });

        bind(new TypeLiteral<InMemoryDAO<Category>>() {
        });


        bind(new TypeLiteral<DAO<Metric>>() {
        })
                .to(new TypeLiteral<InMemoryDAO<Metric>>() {
                });

        bind(new TypeLiteral<InMemoryDAO<Metric>>() {
        });

        bind(new TypeLiteral<DAO<MetricType>>() {
        })
                .to(new TypeLiteral<InMemoryDAO<MetricType>>() {
                });

        bind(new TypeLiteral<InMemoryDAO<MetricType>>() {
        });

        bind(new TypeLiteral<FinderFactory<Category, String, Map<Long, Category>>>() {
        })
                .to(InMemoryFindCategoryByFullName.class);

        bind(new TypeLiteral<FinderFactory<MetricType, String, Map<Long, MetricType>>>() {
        })
                .to(InMemoryFindMetricTypeByName.class);

        bind(new TypeLiteral<FinderFactory<User, Long, Map<Long, User>>>() {
        })
                .to(InMemoryFindUserById.class);

        bind(new TypeLiteral<FinderFactory<Inventory, Long, Map<Long, Inventory>>>() {
        })
                .to(InMemoryFindInventoryById.class);

        bind(new TypeLiteral<Parser<Inventory>>() {
        })
                .to(new TypeLiteral<InventoryParser<Map<Long, Category>, Map<Long, MetricType>>>() {
                });

        bind(new TypeLiteral<Saver<Category>>() {
        })
                .to(new TypeLiteral<CategorySaver<Map<Long, Category>>>() {
                });


        bind(new TypeLiteral<Repository<Inventory, ?>>() {
        })
                .to(new TypeLiteral<Repository<Inventory, Map<Long, Inventory>>>() {
                });

        bind(new TypeLiteral<Repository<User, ?>>() {
        })
                .to(new TypeLiteral<Repository<User, Map<Long, User>>>() {
                });

        bind(new TypeLiteral<Repository<Category, ?>>() {
        })
                .to(new TypeLiteral<Repository<Category, Map<Long, Category>>>() {
                });

        bind(new TypeLiteral<Repository<Metric, ?>>() {
        })
                .to(new TypeLiteral<Repository<Metric, Map<Long, Metric>>>() {
                });

        bind(new TypeLiteral<Repository<MetricType, ?>>() {
        })
                .to(new TypeLiteral<Repository<MetricType, Map<Long, MetricType>>>() {
                });

        bind(new TypeLiteral<TFilter<Long, Inventory, ?>>() {
        }).annotatedWith(Names.named("inventory_root"))
                .to(Key.get(new TypeLiteral<TFilter<Long, Inventory, Map<Long, Inventory>>>() {
                }));

        expose(new TypeLiteral<TFilter<Long, Inventory, ?>>() {
        }).annotatedWith(Names.named("inventory_root"));

        bind(new TypeLiteral<Encoder<User, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<User>>() {
                });

        bind(new TypeLiteral<Encoder<Inventory, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Inventory>>() {
                });

        bind(new TypeLiteral<Encoder<Metric, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Metric>>() {
                });

        bind(new TypeLiteral<Encoder<MetricType, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<MetricType>>() {
                });

        bind(new TypeLiteral<Encoder<Category, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Category>>() {
                });

        ///

        bind(new TypeLiteral<Serialiser<Inventory, byte[]>>() {
        })
                .to(InventorySerialiser.class);

        bind(new TypeLiteral<Serialiser<Inventory, String>>() {
        })
                .to(InventoryStringSerialiser.class);

        bind(new TypeLiteral<Serialiser<Category, byte[]>>() {
        })
                .to(new TypeLiteral<CategorySerialiser>() {
                });

        bind(new TypeLiteral<Serialiser<Category, String>>() {
        })
                .to(new TypeLiteral<CategoryStringSerialiser>() {
                });

        bind(new TypeLiteral<Serialiser<User, byte[]>>() {
        })
                .to(UserSerialiser.class);

        bind(new TypeLiteral<Serialiser<Metric, byte[]>>() {
        })
                .to(MetricSerialiser.class);

        bind(new TypeLiteral<Serialiser<Metric, String>>() {
        })
                .to(MetricStringSerialiser.class);

        bind(new TypeLiteral<Serialiser<MetricType, byte[]>>() {
        })
                .to(new TypeLiteral<MetricTypeSerialiser>() {
                });

        bind(new TypeLiteral<Serialiser<MetricType, String>>() {
        })
                .to(new TypeLiteral<MetricTypeStringSerialiser>() {
                });

        bind(new TypeLiteral<Responder<Inventory>>() {
        })
                .to(new TypeLiteral<JSONResponder<Inventory>>() {
                });

        ///

        bind(new TypeLiteral<UrlBuilder<Inventory>>() {
        })
                .to(InventoryUrlBuilder.class);

        ///
        bind(new TypeLiteral<IdExtractor<Long, Inventory>>() {
        })
                .to(InventoryIdExtractor.class);

        bind(new TypeLiteral<Saver<User>>() {
        })
                .to(new TypeLiteral<UserSaver>() {
                });

        bind(new TypeLiteral<Saver<Inventory>>() {
        })
                .to(InventorySaver.class);

        bind(new TypeLiteral<Saver<Metric>>() {
        })
                .to(MetricSaver.class);

        bind(new TypeLiteral<Saver<MetricType>>() {
        })
                .to(new TypeLiteral<ChildFreeSaver<MetricType>>() {
                });

        bind(new TypeLiteral<Predicate<Inventory>>() {
        })
                .to(InventoryCategoryPredicate.class);

        bind(new TypeLiteral<Validator<User>>() {
        })
                .to(UserValidator.class);

        bind(new TypeLiteral<Validator<Inventory>>() {
        })
                .to(InventoryValidator.class);
    }

    @Provides
    @Singleton
    @Exposed
    @javax.inject.Named("inventory_root")
    public Pestlet<Inventory> providePestletInventory(@javax.inject.Named("current_inventory") com.google.inject.Provider<Inventory> provider,
                                                      com.google.inject.Provider<Responder<Inventory>> responder,
                                                      com.google.inject.Provider<Responder<Collection<Inventory>>> collectionResponder,
                                                      com.google.inject.Provider<DAO<Inventory>> dao,
                                                      com.google.inject.Provider<UrlBuilder<Inventory>> urlBuilder,
                                                      com.google.inject.Provider<Repository<Inventory, ?>> repository,
                                                      Validator<Inventory> validator) {
        return new Pestlet<>(provider, responder, collectionResponder, dao, urlBuilder, repository, validator);
    }

    @Provides
    @Singleton
    public TFilter<Long, Inventory, Map<Long, Inventory>> providesInventoryFromIdFilter(com.google.inject.Provider<Parser<Inventory>> parser,
                                                                                        com.google.inject.Provider<IdExtractor<Long, Inventory>> idExtractor,
                                                                                        com.google.inject.Provider<Repository<Inventory, Map<Long, Inventory>>> repository,
                                                                                        com.google.inject.Provider<FinderFactory<Inventory, Long, Map<Long, Inventory>>> finderFactory) {
        return new TFilter<>(parser, idExtractor, finderFactory, repository, Inventory.class, "inventory");
    }

    @Provides
    Responder<Collection<Inventory>> inventoryCollectionResponderProvider(Serialiser<Inventory, String> serialiser) {
        return new JSONListResponder<>(serialiser, "inventories");
    }
}