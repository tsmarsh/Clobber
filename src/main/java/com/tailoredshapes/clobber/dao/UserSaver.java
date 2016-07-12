package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UserSaver extends Saver<User> {

    private final DAO<Inventory> inventoryDAO;

    @Inject
    public UserSaver(DAO<Inventory> inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    @Override
    public User saveChildren(User object) {
        List<Inventory> savedInventories = new ArrayList<>();
        for (Inventory inventory : object.getInventories()) {
            savedInventories.add(upsert(inventory, inventoryDAO));
        }

        object.setInventories(savedInventories);
        return object;
    }
}
