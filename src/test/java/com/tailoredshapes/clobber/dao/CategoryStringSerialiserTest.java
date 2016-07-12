package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Category;
import com.tailoredshapes.clobber.model.builders.CategoryBuilder;
import com.tailoredshapes.clobber.serialisers.CategoryStringSerialiser;
import org.json.JSONObject;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CategoryStringSerialiserTest {

    @Test
    public void testSerialise() throws Exception {
        Category category = new CategoryBuilder().id(555l).build();

        CategoryStringSerialiser categoryStringSeriliser = new CategoryStringSerialiser();

        JSONObject jsonObject = new JSONObject(categoryStringSeriliser.serialise(category));
        assertThat(jsonObject.getLong("id")).isEqualTo(category.getId());
        assertThat(jsonObject.getString("name")).isEqualTo(category.getName());
        assertThat(jsonObject.getString("fullname")).isEqualTo(category.getFullname());
    }
}

