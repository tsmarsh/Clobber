package com.tailoredshapes.clobber.repositories;

public interface FinderFactory<T, V, Z> {
    Finder<T, Z> lookFor(V ts);
}
