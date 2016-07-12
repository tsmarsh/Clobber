package com.tailoredshapes.clobber.repositories.hibernate;

import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Idable;
import com.tailoredshapes.clobber.repositories.Repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.function.Predicate;

public class HibernateNestedRepository<T extends Idable<T>, P> extends HibernateRepository<T> {

    private final Provider<P> parent;
    private final DAO<T> dao;
    private final Repository<P, ?> parentRepo;
    private final Predicate<T> filter;

    @Inject
    public HibernateNestedRepository(EntityManager manager,
                                     TypeLiteral<T> type,
                                     Provider<P> parent,
                                     DAO<T> dao,
                                     Repository<P, ?> parentRepo,
                                     Predicate<T> filter) {
        super(manager, type, dao);
        this.parent = parent;
        this.dao = dao;
        this.parentRepo = parentRepo;
        this.filter = filter;
    }

    @Override
    public T save(final T t) {
        T savedT = t.getId() == null ? dao.create(t) : dao.update(t);

        P p = this.parent.get();
        Collection<T> ts = list();
        ts.removeIf(filter);

        ts.add(savedT);
        parentRepo.save(p);
        System.out.println("Parent: " + System.identityHashCode(p));
        return savedT;
    }
}

