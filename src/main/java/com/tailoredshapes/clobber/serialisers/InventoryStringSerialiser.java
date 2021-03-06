package com.tailoredshapes.clobber.serialisers;

import com.google.inject.servlet.RequestScoped;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

@RequestScoped
public class InventoryStringSerialiser implements Serialiser<Inventory, String> {

    private final UrlBuilder<Inventory> inventoryUrlBuilder;
    private final Serialiser<Metric, String> metricSerializer;

    @Inject
    public InventoryStringSerialiser(UrlBuilder<Inventory> inventoryUrlBuilder, Serialiser<Metric, String> metricSerializer) {

        this.inventoryUrlBuilder = inventoryUrlBuilder;
        this.metricSerializer = metricSerializer;
    }

    @Override
    public String serialise(Inventory object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", inventoryUrlBuilder.build(object));
        jsonObject.put("category", object.getCategory().getFullname());
        JSONArray metrics = new JSONArray();
        for (Metric metric : object.getMetrics()) {
            metrics.put(new JSONObject(metricSerializer.serialise(metric)));
        }

        jsonObject.put("metrics", metrics);

        if (null != object.getParent()) {
            jsonObject.put("parent", inventoryUrlBuilder.build(object.getParent()));
        }
        return jsonObject.toString();
    }
}
