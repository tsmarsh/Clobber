package com.tailoredshapes.clobber.urlbuilders;

public interface UrlBuilder<T> {
    String build(T t);
}

