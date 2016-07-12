package com.tailoredshapes.clobber.validators;

import com.tailoredshapes.clobber.model.Inventory;

public class InventoryValidator implements Validator<Inventory> {

    @Override
    public Boolean validate(Inventory user) {
        return user.getCategory() != null;
    }
}
