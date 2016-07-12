package com.tailoredshapes.clobber.dao;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.GuiceTest;
import com.tailoredshapes.clobber.model.Category;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.CategoryBuilder;
import com.tailoredshapes.clobber.model.builders.InventoryBuilder;
import com.tailoredshapes.clobber.model.builders.MetricBuilder;
import com.tailoredshapes.clobber.model.builders.UserBuilder;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import com.tailoredshapes.clobber.serialisers.InventorySerialiser;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class InventorySerialiserTest {

    private Inventory parent;
    private Category build;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new UserBuilder().build();
        parent = new InventoryBuilder().id(-111111111111l).build();
        build = new CategoryBuilder().fullname("archer.face").build();
    }


    @Test
    public void testSerialiseACompeteInventory() throws Exception {
        SimpleScope scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        try {
            scope.seed(Key.get(User.class, Names.named("current_user")), user);

            InventorySerialiser inventorySerialiser = GuiceTest.injector.getInstance(InventorySerialiser.class);
            UrlBuilder<Inventory> inventoryUrlBuilder = GuiceTest.injector.getInstance(new Key<UrlBuilder<Inventory>>() {});
            Inventory inventory = new InventoryBuilder().id(777777777777777l)
                    .category(build)
                    .parent(parent).build();


            JSONObject jsonObject = new JSONObject(new String(inventorySerialiser.serialise(inventory)));
            assertEquals(build.getFullname(), jsonObject.getString("category"));
            assertEquals(-111111111111l, jsonObject.getLong("parent"));
            assertEquals(0, jsonObject.getJSONArray("metrics").length());
        } finally {
            scope.exit();
        }
    }

    @Test
    public void testSerialiseAnInventoryWithMetrics() throws Exception {
        SimpleScope scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        try {
            scope.seed(Key.get(User.class, Names.named("current_user")), user);

            Inventory inventory = new InventoryBuilder().id(777777777777777l)
                    .category(build)
                    .metrics(new ArrayList<Metric>() {{
                        add(new MetricBuilder().id(1l).build());
                        add(new MetricBuilder().id(2l).build());
                    }})
                    .parent(parent).build();

            InventorySerialiser inventorySerialiser = GuiceTest.injector.getInstance(InventorySerialiser.class);
            UrlBuilder<Inventory> inventoryUrlBuilder = GuiceTest.injector.getInstance(new Key<UrlBuilder<Inventory>>() {});

            JSONObject jsonObject = new JSONObject(new String(inventorySerialiser.serialise(inventory)));
            assertEquals(build.getFullname(), jsonObject.getString("category"));
            assertEquals(-111111111111l, jsonObject.getLong("parent"));
            assertEquals(2, jsonObject.getJSONArray("metrics").length());
            assertEquals(1l, jsonObject.getJSONArray("metrics").getJSONObject(0).getLong("id"));
            assertEquals(2l, jsonObject.getJSONArray("metrics").getJSONObject(1).getLong("id"));
        } finally {
            scope.exit();
        }
    }
}
