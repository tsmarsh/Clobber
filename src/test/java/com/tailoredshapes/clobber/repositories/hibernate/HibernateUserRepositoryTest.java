package com.tailoredshapes.clobber.repositories.hibernate;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.UserBuilder;
import com.tailoredshapes.clobber.repositories.Repository;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static com.tailoredshapes.clobber.HibernateTest.hibernateInjector;
import static org.junit.Assert.assertEquals;

public class HibernateUserRepositoryTest {
    private SimpleScope scope;

    @Before
    public void setUp() throws Exception {
        scope = hibernateInjector.getInstance(SimpleScope.class);
        scope.enter();
        scope.seed(Key.get(User.class, Names.named("current_user")), new User());
    }

    @After
    public void tearDown() throws Exception {
        scope.exit();
    }

    @Test
    public void shouldFindUserById() throws Exception {
        EntityManager manager = hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        DAO<User> dao = hibernateInjector.getInstance(new Key<DAO<User>>() {});
        User storedUser = dao.create(new UserBuilder().build());
        Repository<User, EntityManager> repository = hibernateInjector.getInstance(new Key<Repository<User, EntityManager>>() {});

        User byId = repository.findById(storedUser.getId());
        assertEquals(storedUser, byId);

        transaction.rollback();
    }
}
