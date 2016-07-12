package com.tailoredshapes.clobber.model;

import com.tailoredshapes.clobber.model.builders.MetricTypeBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetricTypeTest {
    @Test
    public void shouldOverideEqualsCorrectly() throws Exception {
        MetricType farts = new MetricTypeBuilder().name("farts").build();
        MetricType spam = new MetricTypeBuilder().name("spam").build();
        MetricType farts1 = new MetricTypeBuilder().name("farts").build();

        assertFalse(farts.equals(spam));
        assertTrue(farts.equals(farts1));
    }

    @Test
    public void shouldGenerateAUsefulString() throws Exception {
        MetricType farts = new MetricTypeBuilder().name("farts").build();
        assertEquals("MetricType{id=null, name='farts'}", farts.toString());
    }
}