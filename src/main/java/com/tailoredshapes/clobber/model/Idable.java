package com.tailoredshapes.clobber.model;

public interface Idable<T> {
    Long getId();

    T setId(Long id);
}
