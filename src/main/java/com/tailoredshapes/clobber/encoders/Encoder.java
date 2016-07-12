package com.tailoredshapes.clobber.encoders;

import com.tailoredshapes.clobber.security.Algorithm;

public interface Encoder<T, R extends Algorithm> {
    Long encode(T object);
}
