package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.model.MetricType;

import javax.inject.Inject;

public class MetricSaver extends Saver<Metric> {

    private final DAO<MetricType> dao;

    @Inject
    public MetricSaver(DAO<MetricType> dao) {
        this.dao = dao;
    }

    @Override
    public Metric saveChildren(Metric object) {
        object.setType(upsert(object.getType(), dao));
        return object;
    }
}
