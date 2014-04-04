package com.tailoredshapes.inventoryserver.repositories.hibernate;

import com.google.inject.Inject;
import com.tailoredshapes.inventoryserver.model.User;
import com.tailoredshapes.inventoryserver.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class HibernateUserRepository implements UserRepository {

    private EntityManager manager;

    @Inject
    public HibernateUserRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public User findById(Long user_id) {
        return manager.find(User.class, user_id);
    }
}
