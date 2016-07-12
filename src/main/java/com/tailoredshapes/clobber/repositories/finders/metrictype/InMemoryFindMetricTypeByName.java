package com.tailoredshapes.clobber.repositories.finders.metrictype;

import com.tailoredshapes.clobber.model.MetricType;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import java.util.Map;

public class InMemoryFindMetricTypeByName implements FinderFactory<MetricType, String, Map<Long, MetricType>>, Finder<MetricType, Map<Long, MetricType>> {
    private String name;

    @Override
    public Finder<MetricType, Map<Long, MetricType>> lookFor(String strings) {
        this.name = strings;
        return this;
    }

    @Override
    public MetricType find(Map<Long, MetricType> db) {
        for (MetricType type : db.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return new MetricType().setName(name);
    }
}
