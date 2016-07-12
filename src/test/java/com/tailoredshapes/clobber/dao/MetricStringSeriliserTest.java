package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.model.builders.MetricBuilder;
import com.tailoredshapes.clobber.serialisers.MetricStringSerialiser;
import junit.framework.TestCase;
import org.json.JSONObject;

import static org.fest.assertions.api.Assertions.assertThat;

public class MetricStringSeriliserTest extends TestCase {
    public void testSerialise() throws Exception {
        Metric metric = new MetricBuilder().id(555l).build();
        MetricStringSerialiser metricSeriliser = new MetricStringSerialiser();
        JSONObject jsonObject = new JSONObject(metricSeriliser.serialise(metric));
        assertEquals(metric.getValue(), jsonObject.getString("value"));
        assertThat(jsonObject.getString("type")).isEqualTo(metric.getType().getName());
        assertThat(jsonObject.getLong("id")).isEqualTo(metric.getId());
    }
}
