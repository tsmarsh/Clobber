package com.tailoredshapes.clobber.validators;

public interface Validator<T> {
    Boolean validate(T t);
}
