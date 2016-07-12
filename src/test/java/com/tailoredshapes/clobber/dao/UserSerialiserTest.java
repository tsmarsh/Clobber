package com.tailoredshapes.clobber.dao;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.GuiceTest;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.InventoryBuilder;
import com.tailoredshapes.clobber.model.builders.UserBuilder;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import com.tailoredshapes.clobber.serialisers.UserSerialiser;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class UserSerialiserTest {


    private User user;
    private SimpleScope scope;

    @Before
    public void setUp() throws Exception {
        Inventory inventory = new InventoryBuilder().build();
        HashSet<Inventory> inventories = new HashSet<>();
        inventories.add(inventory);
        user = new UserBuilder().inventories(inventories).build();
        scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        scope.seed(Key.get(User.class, Names.named("current_user")), user);
    }

    @After
    public void tearDown() throws Exception {
        scope.exit();
    }

    @Test
    public void testShouldSerializeAUser() throws Exception {
        UserSerialiser userSerialiser = GuiceTest.injector.getInstance(UserSerialiser.class);
        JSONObject jsonObject = new JSONObject(new String(userSerialiser.serialise(user)));


        assertEquals(user.getName(), jsonObject.getString("name"));

        assertTrue(jsonObject.has("inventories"));
        assertEquals(1, jsonObject.getJSONArray("inventories").length());
    }
}
