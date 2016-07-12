package com.tailoredshapes.clobber.extractors;

import com.sun.net.httpserver.HttpExchange;

public interface IdExtractor<S, T> {
    S extract(HttpExchange exchange);

    S extract(String path);
}

