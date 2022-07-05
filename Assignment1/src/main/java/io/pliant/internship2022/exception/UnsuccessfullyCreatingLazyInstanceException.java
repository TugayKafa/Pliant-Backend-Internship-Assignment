package io.pliant.internship2022.exception;

public class UnsuccessfullyCreatingLazyInstanceException extends ServiceLocatorException{
    public UnsuccessfullyCreatingLazyInstanceException(String clazzName) {
        super(String.format("Unsuccessfully creating lazy instance of %s.class!",clazzName));
    }
}
