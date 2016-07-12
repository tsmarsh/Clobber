package com.tailoredshapes.clobber.dao;

import com.tailoredshapes.clobber.model.Idable;

public abstract class Saver<T> {
    <Z extends Idable<Z>> Z upsert(Z object, DAO<Z> dao) {
        if (object != null) {
            if (null == object.getId()) {
                return dao.create(object);
            } else {
                return dao.update(object);
            }
        }
        return null;
    }

    public abstract T saveChildren(T object);
}
