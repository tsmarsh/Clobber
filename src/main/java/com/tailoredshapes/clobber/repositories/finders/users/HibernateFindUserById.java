package com.tailoredshapes.clobber.repositories.finders.users;

import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import javax.persistence.EntityManager;

public class HibernateFindUserById implements FinderFactory<User, Long, EntityManager>, Finder<User, EntityManager> {
    private Long id;

    @Override
    public User find(EntityManager db) {
        return db.find(User.class, id);
    }

    @Override
    public Finder<User, EntityManager> lookFor(Long ts) {
        this.id = ts;
        return this;
    }
}

