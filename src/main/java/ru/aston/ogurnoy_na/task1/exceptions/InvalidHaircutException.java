package ru.aston.ogurnoy_na.task1.exceptions;

public class InvalidHaircutException extends InvalidServiceParameterException {
    public InvalidHaircutException(String message) {
        super(message);
    }
}