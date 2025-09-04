package org.example.customException;

public class InvalidRatingException extends Exception{

    public InvalidRatingException() {
        super("Рейтинг должен быть в диапазоне от 1 до 5");
    }
}
