package com.tailoredshapes.clobber.serialisers;

public interface Serialiser<T, Z> {
    Z serialise(T object);
}

