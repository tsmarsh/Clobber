package com.tailoredshapes.clobber.dao.memory;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.GuiceTest;
import com.tailoredshapes.clobber.HibernateTest;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.InventoryBuilder;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDAOTest extends GuiceTest {

    private User user;
    private SimpleScope scope;

    @Before
    public void setUp() throws Exception {
        user = new User().setName("Archer");
        scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        scope.seed(Key.get(User.class, Names.named("current_user")), user);
    }

    @After
    public void tearDown() throws Exception {
        scope.exit();
    }

    @Test
    public void testInMemory() throws Exception {
        DAO<User> dao = GuiceTest.injector.getInstance(new Key<DAO<User>>() {});
        testSaveChildren(dao);
    }

    @Test
    public void testHibernate() throws Exception {
        user = new User().setName("Archer");
        scope = HibernateTest.hibernateInjector.getInstance(SimpleScope.class);
        scope.enter();
        scope.seed(Key.get(User.class, Names.named("current_user")), user);
        scope.seed(Key.get(Inventory.class, Names.named("current_inventory")), new InventoryBuilder().build());

        EntityManager instance = HibernateTest.hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = instance.getTransaction();
        transaction.begin();
        DAO<User> dao = HibernateTest.hibernateInjector.getInstance(new Key<DAO<User>>() {});
        testSaveChildren(dao);
        transaction.rollback();
    }

    private void testSaveChildren(DAO<User> dao) throws Exception {
        User savedUser = dao.create(user);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getPublicKey());
        assertNotNull(savedUser.getPrivateKey());

        User readUser = dao.read(new User().setId(savedUser.getId()));
        assertEquals(savedUser, readUser);
    }
}
