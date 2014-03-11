package com.tailoredshapes.inventoryserver.dao.hibernate;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.inventoryserver.dao.DAO;
import com.tailoredshapes.inventoryserver.dao.Saver;
import com.tailoredshapes.inventoryserver.model.Idable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class HibernateDAO<T extends Idable<T>> implements DAO<T> {
    private TypeLiteral<T> type;
    private SessionFactory factory;
    private final Class<? super T> rawType;
    private Saver<T> saver;

    @Inject
    public HibernateDAO(TypeLiteral<T> type, SessionFactory factory, Saver<T> saver) {
        this.saver = saver;
        rawType = type.getRawType();
        this.factory = factory;
    }

    @Override
    public T create(T object) {
        Session currentSession = factory.getCurrentSession();
        object = saver.saveChildren(object);
        currentSession.save(object);
        return object;
    }

    @Override
    public T read(T object) {
        Session currentSession = factory.getCurrentSession();

        return (T) currentSession.get(rawType, object.getId());
    }

    @Override
    public T update(T object) {
        Session currentSession = factory.getCurrentSession();
        object = saver.saveChildren(object);
        currentSession.saveOrUpdate(object);
        return object;
    }

    @Override
    public T delete(T object) {
        Session currentSession = factory.getCurrentSession();
        object = (T) currentSession.get(rawType, object.getId());
        currentSession.delete(object);
        return object;
    }
}