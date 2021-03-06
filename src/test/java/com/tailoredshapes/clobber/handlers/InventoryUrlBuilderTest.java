package com.tailoredshapes.clobber.handlers;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.GuiceTest;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.InventoryBuilder;
import com.tailoredshapes.clobber.model.builders.UserBuilder;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InventoryUrlBuilderTest {

    private SimpleScope scope;


    @Before
    public void init() {
        scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        final User user = new UserBuilder().name("Cassie").id(51284l).build();
        scope.seed(Key.get(User.class, Names.named("current_user")), user);
    }

    @After
    public void tearDown() throws Exception {
        scope.exit();
    }

    @Test
    public void testShouldReturnTheCorrectUrlForAnInventory() throws Exception {
        Inventory inventory = new InventoryBuilder()
                .id(141211l).build();

        UrlBuilder<Inventory> inventoryUrlBuilder = GuiceTest.injector.getInstance(new Key<UrlBuilder<Inventory>>() {});
        String url = inventoryUrlBuilder.build(inventory);
        assertEquals("http://localhost:5555/users/Cassie/51284/inventories/141211", url);
    }

    @Test
    public void testShouldReturnNullIfIdNotSet() throws Exception {

        Inventory inventory = new InventoryBuilder()
                .id(null).build();
        UrlBuilder<Inventory> inventoryUrlBuilder = GuiceTest.injector.getInstance(new Key<UrlBuilder<Inventory>>() {});
        String url = inventoryUrlBuilder.build(inventory);
        assertNull(url);

    }
}
