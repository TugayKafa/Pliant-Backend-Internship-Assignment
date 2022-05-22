package io.pliant.internship2022.exception;

public abstract class ServiceLocatorException extends RuntimeException {
    protected ServiceLocatorException(String message) {
        super(message);
    }
}
