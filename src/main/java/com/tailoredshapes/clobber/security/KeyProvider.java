package com.tailoredshapes.clobber.security;

import java.security.KeyPair;

public interface KeyProvider<T> {
    KeyPair generate();
}

