package com.tailoredshapes.clobber.repositories;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Category;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.CategoryBuilder;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Map;

import static com.tailoredshapes.clobber.GuiceTest.injector;
import static com.tailoredshapes.clobber.HibernateTest.hibernateInjector;
import static org.junit.Assert.assertEquals;

public class CategoryRepositoryTest {
    private SimpleScope scope;

    @Test
    public void memoryFindByName() throws Exception {
        scope = injector.getInstance(SimpleScope.class);
        scope.enter();

        Repository<Category, Map<Long, Category>> repo = injector.getInstance(new Key<Repository<Category, Map<Long, Category>>>() {});
        FinderFactory<Category, String, Map<Long, Category>> findByFullName = injector.getInstance(new Key<FinderFactory<Category, String, Map<Long, Category>>>() {});

        testFindByName(injector, repo, findByFullName, new Runnable() {
            @Override
            public void run() {
            }
        });

        scope.exit();
    }

    @Test
    public void hibernateFindByName() throws Exception {
        scope = hibernateInjector.getInstance(SimpleScope.class);
        scope.enter();

        final EntityManager manager = hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Repository<Category, EntityManager> repo = hibernateInjector.getInstance(new Key<Repository<Category, EntityManager>>() {});
        FinderFactory<Category, String, EntityManager> findByFullName = hibernateInjector.getInstance(new Key<FinderFactory<Category, String, EntityManager>>() {});

        testFindByName(hibernateInjector, repo, findByFullName, new Runnable() {
            @Override
            public void run() {
                manager.flush();
                manager.clear();
            }
        });

        transaction.rollback();

        scope.exit();
    }

    public <T> void testFindByName(Injector injector, Repository<Category, T> repo, FinderFactory<Category, String, T> findByFullName, Runnable reset) throws Exception {
        scope.seed(Key.get(User.class, Names.named("current_user")), new User());

        Category category = new CategoryBuilder().build();
        DAO<Category> dao = injector.getInstance(new Key<DAO<Category>>() {});
        Category savedCategory = dao.create(category);

        reset.run();

        Category byId = repo.findBy(findByFullName.lookFor(savedCategory.getFullname()));
        assertEquals(savedCategory, byId);
    }


    @Test
    public void hibernateMissByName() throws Exception {
        scope = hibernateInjector.getInstance(SimpleScope.class);
        scope.enter();
        final EntityManager manager = hibernateInjector.getInstance(EntityManager.class);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Repository<Category, EntityManager> repo = hibernateInjector.getInstance(new Key<Repository<Category, EntityManager>>() {});
        FinderFactory<Category, String, EntityManager> findByFullName = hibernateInjector.getInstance(new Key<FinderFactory<Category, String, EntityManager>>() {});

        testMissByName(repo, findByFullName);

        transaction.rollback();
        scope.exit();
    }


    @Test
    public void memoryMissByName() throws Exception {
        scope = injector.getInstance(SimpleScope.class);
        scope.enter();

        Repository<Category, Map<Long, Category>> repo = injector.getInstance(new Key<Repository<Category, Map<Long, Category>>>() {});
        FinderFactory<Category, String, Map<Long, Category>> findByFullName = injector.getInstance(new Key<FinderFactory<Category, String, Map<Long, Category>>>() {});

        testMissByName(repo, findByFullName);

        scope.exit();
    }

    public <T> void testMissByName(Repository<Category, T> repo, FinderFactory<Category, String, T> findByFullName) throws Exception {
        scope.seed(Key.get(User.class, Names.named("current_user")), new User());

        Category byId = repo.findBy(findByFullName.lookFor("brian"));
        assertEquals("brian", byId.getFullname());
    }
}
