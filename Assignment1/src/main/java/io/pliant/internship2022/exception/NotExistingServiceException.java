package io.pliant.internship2022.exception;

public class NotExistingServiceException extends ServiceLocatorException {
    public NotExistingServiceException(String name) {
        super(String.format("Not existing serviceClass with name %s!", name));
    }
}
