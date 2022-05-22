package io.pliant.internship2022.exception;

public class ZeroRegistersException extends ServiceLocatorException {
    public ZeroRegistersException(String name) {
        super(String.format("Zero registers which is instances or descendants of %s class!", name));
    }
}
