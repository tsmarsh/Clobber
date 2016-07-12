package com.tailoredshapes.clobber.validators;

import com.tailoredshapes.clobber.model.User;

public class UserValidator implements Validator<User> {
    @Override
    public Boolean validate(User user) {
        return user.getName() != null;
    }
}

