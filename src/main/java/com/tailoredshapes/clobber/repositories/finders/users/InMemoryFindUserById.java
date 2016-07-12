package com.tailoredshapes.clobber.repositories.finders.users;

import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import java.util.Map;

public class InMemoryFindUserById implements FinderFactory<User, Long, Map<Long, User>>, Finder<User, Map<Long, User>> {
    private Long id;

    @Override
    public User find(Map<Long, User> db) {
        return db.get(id);
    }

    @Override
    public Finder<User, Map<Long, User>> lookFor(Long ts) {
        this.id = ts;
        return this;
    }
}
