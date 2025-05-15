package ru.football.exceptions;

public class DuplicateMatchException extends RuntimeException {
    public DuplicateMatchException(String message) {
        super(message);
    }
}
