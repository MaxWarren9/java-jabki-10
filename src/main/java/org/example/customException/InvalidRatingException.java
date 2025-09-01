package org.example.customException;

public class InvalidRatingException extends Exception{
    public InvalidRatingException(String message) {
        super(message);
    }
}
