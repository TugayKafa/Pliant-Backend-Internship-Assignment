package io.pliant.internship2022.exception;

public class NotExistingRegisterException extends ServiceLocatorException {
    public NotExistingRegisterException(String name) {
        super(String.format("Not existing register with name %s!", name));
    }
}
