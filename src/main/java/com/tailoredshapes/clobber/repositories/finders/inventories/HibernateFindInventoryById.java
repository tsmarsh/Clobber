package com.tailoredshapes.clobber.repositories.finders.inventories;

import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import javax.persistence.EntityManager;

public class HibernateFindInventoryById implements FinderFactory<Inventory, Long, EntityManager>, Finder<Inventory, EntityManager> {
    private Long id;

    @Override
    public Inventory find(EntityManager db) {
        return db.find(Inventory.class, id);
    }

    @Override
    public Finder<Inventory, EntityManager> lookFor(Long ts) {
        this.id = ts;
        return this;
    }
}

