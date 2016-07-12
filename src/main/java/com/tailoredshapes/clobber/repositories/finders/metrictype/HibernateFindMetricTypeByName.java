package com.tailoredshapes.clobber.repositories.finders.metrictype;

import com.tailoredshapes.clobber.model.MetricType;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class HibernateFindMetricTypeByName implements FinderFactory<MetricType, String, EntityManager>, Finder<MetricType, EntityManager> {
    private String name;

    @Override
    public MetricType find(EntityManager manager) {
        Query cq = manager.createQuery("select m from MetricType m where m.name = :name")
                .setParameter("name", name);

        MetricType type;
        try {
            type = manager.merge((MetricType) cq.getSingleResult());
        } catch (Exception e) {
            type = new MetricType().setName(name);
        }
        return type;
    }

    @Override
    public Finder<MetricType, EntityManager> lookFor(String strings) {
        this.name = strings;
        return this;
    }
}

