package com.tailoredshapes.clobber.serialisers;

import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Base64;

public class UserStringSerialiser implements Serialiser<User, String> {

    private final UrlBuilder<User> urlBuilder;
    private final Serialiser<Inventory, String> inventorySerialiser;

    @Inject
    public UserStringSerialiser(UrlBuilder<User> urlBuilder, Serialiser<Inventory, String> inventorySerialiser) {
        this.urlBuilder = urlBuilder;
        this.inventorySerialiser = inventorySerialiser;
    }

    @Override
    public String serialise(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", urlBuilder.build(user));
        jsonObject.put("name", user.getName());

        JSONArray inventories = new JSONArray();
        for (Inventory inventory : user.getInventories()) {
            inventories.put(new JSONObject(inventorySerialiser.serialise(inventory)));
        }

        jsonObject.put("inventories", inventories);

        return jsonObject.toString();
    }
}
