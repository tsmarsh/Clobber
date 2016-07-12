package com.tailoredshapes.clobber.repositories.finders.users;

import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.FinderFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class HibernateFindUserByName implements FinderFactory<User, String, EntityManager>, Finder<User, EntityManager> {
    private String name;

    @Override
    public User find(EntityManager manager) {
        Query cq = manager.createQuery("select u from User u where u.name = :name")
                .setParameter("name", name);

        User type;
        try {
            type = manager.merge((User) cq.getSingleResult());
        } catch (Exception e) {
            type = new User().setName(name);
        }
        return type;
    }

    @Override
    public Finder<User, EntityManager> lookFor(String strings) {
        this.name = strings;
        return this;
    }
}


