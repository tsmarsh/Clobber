package com.tailoredshapes.clobber.dao.hibernate;

import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.dao.Saver;
import com.tailoredshapes.clobber.encoders.Encoder;
import com.tailoredshapes.clobber.model.Idable;
import com.tailoredshapes.clobber.model.ShallowCopy;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
public class HibernateDAO<T extends Cloneable & Idable<T> & ShallowCopy<T>> implements DAO<T> {
    private final Class<? super T> rawType;
    private final EntityManager manager;
    private final Saver<T> saver;
    private final Encoder<T, ?> encoder;


    @Inject
    public HibernateDAO(TypeLiteral<T> type, EntityManager manager, Saver<T> saver, Encoder<T, ?> encoder) {
        this.manager = manager;
        this.saver = saver;
        this.encoder = encoder;
        rawType = type.getRawType();
    }

    @Override
    public T create(T object) {
        object = saver.saveChildren(object);

        Long sig = encoder.encode(object);

        object.setId(sig);
        T read = read(object);
        T out;
        if (read == null) {
            manager.persist(object);
            manager.flush();
            out = object;
        } else {
            out = read;
        }

        return out;
    }

    @Override
    public T read(T object) {
        return (T) manager.find(rawType, object.getId());
    }

    @Override
    public T update(T object) {
        T clone = object.shallowCopy();
        clone = saver.saveChildren(clone);

        Long sig = encoder.encode(clone);
        T out;
        if (!sig.equals(object.getId())) {
            clone.setId(sig);
            T read = read(clone);
            if (read == null) {
                manager.persist(clone);
                try {
                    manager.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                out = clone;
            } else {
                out = read;
            }
        } else {
            out = object;
        }

        return out;
    }

    @Override
    public T delete(T object) {
        object = (T) manager.find(rawType, object.getId());
        manager.remove(object);
        return object;
    }
}
