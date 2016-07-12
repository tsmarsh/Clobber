package com.tailoredshapes.clobber.repositories;

public interface Finder<T, Z> {
    T find(Z db);
}



