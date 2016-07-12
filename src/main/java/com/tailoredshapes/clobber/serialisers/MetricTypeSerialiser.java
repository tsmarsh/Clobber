package com.tailoredshapes.clobber.serialisers;

import com.tailoredshapes.clobber.model.MetricType;
import org.json.JSONObject;

public class MetricTypeSerialiser implements Serialiser<MetricType, byte[]> {
    @Override
    public byte[] serialise(MetricType object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", object.getName());
        return jsonObject.toString().getBytes();
    }
}
