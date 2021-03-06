package com.tailoredshapes.clobber.repositories.hibernate;

import com.google.inject.TypeLiteral;
import com.google.inject.servlet.RequestScoped;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Idable;
import com.tailoredshapes.clobber.repositories.Finder;
import com.tailoredshapes.clobber.repositories.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

@RequestScoped
public class HibernateRepository<T extends Idable<T>> implements Repository<T, EntityManager> {
    private final EntityManager manager;
    private final DAO<T> dao;
    private final Class<T> rawType;

    @Inject
    public HibernateRepository(EntityManager manager, TypeLiteral<T> type, DAO<T> dao) {
        this.manager = manager;
        this.dao = dao;
        //noinspection unchecked,unchecked
        this.rawType = (Class<T>) type.getRawType();
    }

    @Override
    public T findById(Long id) {
        return manager.find(rawType, id);
    }

    @Override
    public T findBy(Finder<T, EntityManager> finder) {
        return finder.find(manager);
    }

    @Override
    public Collection<T> listBy(Finder<Collection<T>, EntityManager> finder) {
        return finder.find(manager);
    }

    @Override
    public Collection<T> list() {
        String getAllTs = String.format("select t from %s t", rawType.getSimpleName());
        Query query = manager.createQuery(getAllTs);
        return query.getResultList();
    }

    @Override
    public T save(final T t) {
        return t.getId() == null ? dao.create(t) : dao.update(t);
    }
}
