package ru.aston.ogurnoy_na.task1.exceptions;

public class InvalidServiceParameterException extends IllegalArgumentException {
    public InvalidServiceParameterException(String message) {
        super(message);
    }
}