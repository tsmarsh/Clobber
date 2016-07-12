package com.tailoredshapes.clobber.repositories.finders.users;

import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import java.util.Map;

public class InMemoryFindUserByName implements FinderFactory<User, String, Map<Long, User>>, Finder<User, Map<Long, User>> {
    private String name;

    @Override
    public Finder<User, Map<Long, User>> lookFor(String strings) {
        this.name = strings;
        return this;
    }

    @Override
    public User find(Map<Long, User> db) {
        for (User type : db.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return new User().setName(name);
    }
}
