package com.tailoredshapes.clobber.dao;

public interface DAO<T> {

    T create(T object);

    T read(T object);

    T update(T object);

    T delete(T object);
}

