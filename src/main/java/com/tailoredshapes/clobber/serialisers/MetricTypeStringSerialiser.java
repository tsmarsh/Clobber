package com.tailoredshapes.clobber.serialisers;

import com.tailoredshapes.clobber.model.MetricType;
import org.json.JSONObject;

public class MetricTypeStringSerialiser implements Serialiser<MetricType, String> {
    @Override
    public String serialise(MetricType object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", object.getId());
        jsonObject.put("name", object.getName());
        return jsonObject.toString();
    }
}
