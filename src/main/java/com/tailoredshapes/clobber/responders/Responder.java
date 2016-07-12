package com.tailoredshapes.clobber.responders;

import java.io.Writer;

public interface Responder<T> {
    String respond(T object, Writer writer);
}

