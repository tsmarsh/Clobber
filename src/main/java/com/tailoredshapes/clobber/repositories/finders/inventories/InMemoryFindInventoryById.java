package com.tailoredshapes.clobber.repositories.finders.inventories;

import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import java.util.Map;

public class InMemoryFindInventoryById implements FinderFactory<Inventory, Long, Map<Long, Inventory>>, Finder<Inventory, Map<Long, Inventory>> {
    private Long id;

    @Override
    public Inventory find(Map<Long, Inventory> db) {
        return db.get(id);
    }

    @Override
    public Finder<Inventory, Map<Long, Inventory>> lookFor(Long ts) {
        this.id = ts;
        return this;
    }
}
