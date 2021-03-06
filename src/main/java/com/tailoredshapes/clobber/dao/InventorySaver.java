package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Category;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.Metric;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class InventorySaver extends Saver<Inventory> {

    private final DAO<Inventory> inventoryDAO;
    private final DAO<Metric> metricDAO;
    private final DAO<Category> categoryDAO;

    @Inject
    public InventorySaver(DAO<Inventory> inventoryDAO,
                          DAO<Metric> metricDAO,
                          DAO<Category> categoryDAO) {
        this.inventoryDAO = inventoryDAO;
        this.metricDAO = metricDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Inventory saveChildren(Inventory object) {
        object.setParent(upsert(object.getParent(), inventoryDAO));
        object.setCategory(upsert(object.getCategory(), categoryDAO));
        List<Metric> savedMetrics = new ArrayList<>();

        for (Metric metric : object.getMetrics()) {
            savedMetrics.add(upsert(metric, metricDAO));
        }

        object.setMetrics(savedMetrics);
        return object;
    }
}

