package ru.aston.ogurnoy_na.task1.exceptions;

public class InvalidHairColorException extends InvalidServiceParameterException {
    public InvalidHairColorException(String message) {
        super(message);
    }
}