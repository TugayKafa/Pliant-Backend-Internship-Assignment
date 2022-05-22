package io.pliant.internship2022.exception;

public class RegisterExistException extends ServiceLocatorException {
    public RegisterExistException(String name) {
        super(String.format("Register with name %s already exist!", name));
    }
}
