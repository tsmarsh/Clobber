package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.MetricType;
import com.tailoredshapes.clobber.serialisers.MetricTypeStringSerialiser;
import org.json.JSONObject;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MetricTypeSerialiserTest {
    @Test
    public void testSerialise() throws Exception {
        MetricType mt = new MetricType().setId(141211l).setName("string");

        MetricTypeStringSerialiser serialiser = new MetricTypeStringSerialiser();
        String serialise = serialiser.serialise(mt);
        JSONObject jsonObject = new JSONObject(serialise);
        assertThat(jsonObject.getString("name")).isEqualTo("string");
        assertThat(jsonObject.getLong("id")).isEqualTo(141211l);
    }
}
