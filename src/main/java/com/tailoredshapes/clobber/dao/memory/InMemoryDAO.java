package com.tailoredshapes.clobber.dao.memory;

import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.dao.Saver;
import com.tailoredshapes.clobber.encoders.Encoder;
import com.tailoredshapes.clobber.model.Idable;

import javax.inject.Inject;
import java.util.Map;

public class InMemoryDAO<T extends Idable<T>> implements DAO<T> {

    private final Map<Long, T> db;
    private final Encoder<T, ?> encoder;
    private final Saver<T> saver;

    @Inject
    public InMemoryDAO(Map<Long, T> db, Encoder<T, ?> encoder, Saver<T> saver) {
        this.encoder = encoder;
        this.saver = saver;
        this.db = db;
    }

    @Override
    public T create(T object) {
        object = saver.saveChildren(object);

        Long sig = encoder.encode(object);
        object.setId(sig);
        db.put(sig, object);
        return object;
    }

    @Override
    public T read(T object) {
        return db.get(object.getId());
    }

    @Override
    public T update(T object) {

        if (!db.containsKey(object.getId())) {
            throw new RuntimeException("Object does not exist");
        }
        object = saver.saveChildren(object);
        Long sig = encoder.encode(object);
        object.setId(sig);
        db.put(sig, object);
        return object;
    }

    @Override
    public T delete(T object) {
        object = db.get(object.getId());
        db.remove(object.getId());
        return object;
    }
}

