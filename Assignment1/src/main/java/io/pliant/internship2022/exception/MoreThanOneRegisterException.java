package io.pliant.internship2022.exception;

public class MoreThanOneRegisterException extends ServiceLocatorException{
    public MoreThanOneRegisterException(String name) {
        super(String.format("More than 1 register which is instance or descendant of %s class!",name));
    }
}
