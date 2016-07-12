package com.tailoredshapes.clobber.encoders;

import com.tailoredshapes.clobber.security.LongHash;
import com.tailoredshapes.clobber.serialisers.Serialiser;

import javax.inject.Inject;

public class LongHashEncoder<T> implements Encoder<T, LongHash> {

    private final Serialiser<T, String> serialiser;

    @Inject
    public LongHashEncoder(Serialiser<T, String> serialiser) {
        this.serialiser = serialiser;
    }

    @Override
    public Long encode(T object) {

        char[] value = serialiser.serialise(object).toCharArray();

        long h = 0L;

        if (value.length > 0) {
            for (char aValue : value) {
                h = 524287L * h + aValue;
            }
        }
        return h;
    }
}
