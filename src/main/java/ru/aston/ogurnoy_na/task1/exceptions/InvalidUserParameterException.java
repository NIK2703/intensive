package ru.aston.ogurnoy_na.task1.exceptions;

public class InvalidUserParameterException extends IllegalArgumentException {
    public InvalidUserParameterException(String message) {
        super(message);
    }
}