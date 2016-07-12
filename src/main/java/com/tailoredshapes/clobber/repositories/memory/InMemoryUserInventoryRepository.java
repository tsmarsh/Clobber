package com.tailoredshapes.clobber.repositories.memory;

import com.google.inject.Inject;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.repositories.Repository;

import javax.inject.Provider;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class InMemoryUserInventoryRepository extends InMemoryNestedRepository<Inventory, User> {
    private final Provider<User> parent;

    @Inject
    public InMemoryUserInventoryRepository(Map<Long, Inventory> db,
                                           DAO<Inventory> dao,
                                           Provider<User> parent,
                                           Repository<User, ?> parentRepo,
                                           Predicate<Inventory> filter) {
        super(db, dao, parent, filter, parentRepo);
        this.parent = parent;
    }

    @Override
    public Collection<Inventory> list() {
        return parent.get().getInventories();
    }
}
