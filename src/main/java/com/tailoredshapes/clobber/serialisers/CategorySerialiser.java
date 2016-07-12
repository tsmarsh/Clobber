package com.tailoredshapes.clobber.serialisers;

import com.tailoredshapes.clobber.model.Category;
import org.json.JSONObject;

public class CategorySerialiser implements Serialiser<Category, byte[]> {
    @Override
    public byte[] serialise(Category object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", object.getName());
        jsonObject.put("fullname", object.getFullname());
        if (object.getParent() != null) {
            jsonObject.put("parent", serialise(object.getParent()));
        }
        return jsonObject.toString().getBytes();
    }
}
