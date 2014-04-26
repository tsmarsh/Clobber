package com.tailoredshapes.inventoryserver.repositories.hibernate;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.inventoryserver.dao.DAO;
import com.tailoredshapes.inventoryserver.model.Category;
import com.tailoredshapes.inventoryserver.model.User;
import com.tailoredshapes.inventoryserver.model.builders.CategoryBuilder;
import com.tailoredshapes.inventoryserver.repositories.FinderFactory;
import com.tailoredshapes.inventoryserver.repositories.Repository;
import com.tailoredshapes.inventoryserver.scopes.SimpleScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static com.tailoredshapes.inventoryserver.HibernateTest.hibernateInjector;
import static org.junit.Assert.assertEquals;

public class HibernateCategoryRepositoryTest {
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
    public void testFindByName() throws Exception {
        EntityManager manager = hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Category category = new CategoryBuilder().build();
        DAO<Category> dao = hibernateInjector.getInstance(new Key<DAO<Category>>() {});
        Repository<Category, EntityManager> repo = hibernateInjector.getInstance(new Key<Repository<Category, EntityManager>>(){});
        Category savedCategory = dao.create(category);

        manager.flush();
        manager.clear();

        FinderFactory<Category, String, EntityManager> findByFullName= hibernateInjector.getInstance(new Key<FinderFactory<Category, String, EntityManager>>(){});

        Category byId = repo.findBy(findByFullName.lookFor(savedCategory.getFullname()));
        assertEquals(savedCategory, byId);

        transaction.rollback();
    }

    @Test
    public void testMissByName() throws Exception {
        EntityManager manager = hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Repository<Category, EntityManager> repo = hibernateInjector.getInstance(new Key<Repository<Category, EntityManager>>(){});
        FinderFactory<Category, String, EntityManager> findByFullName= hibernateInjector.getInstance(new Key<FinderFactory<Category, String, EntityManager>>(){});


        Category byId = repo.findBy(findByFullName.lookFor("brian"));
        assertEquals("brian", byId.getFullname());

        transaction.rollback();
    }
}
