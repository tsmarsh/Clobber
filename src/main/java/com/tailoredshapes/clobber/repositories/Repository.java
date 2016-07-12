package com.tailoredshapes.clobber.repositories;

import java.util.Collection;

public interface Repository<T, Z> {
    T findById(Long extract);

    T findBy(Finder<T, Z> finder);

    Collection<T> listBy(Finder<Collection<T>, Z> finder);

    Collection<T> list();

    T save(T t);
}
