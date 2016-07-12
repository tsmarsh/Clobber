package com.tailoredshapes.clobber.serialisers;

public interface Serialiser<T, Z> {
    public Z serialise(T object);
}

