package com.tailoredshapes.clobber.serialisers;

import com.tailoredshapes.clobber.model.Metric;
import org.json.JSONObject;

public class MetricSerialiser implements Serialiser<Metric, byte[]> {

    @Override
    public byte[] serialise(Metric object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", object.getValue());
        jsonObject.put("type", object.getType().getName());
        return jsonObject.toString().getBytes();
    }
}

