package com.tailoredshapes.clobber.parsers;

public interface Parser<T> {
    T parse(String s);
}
