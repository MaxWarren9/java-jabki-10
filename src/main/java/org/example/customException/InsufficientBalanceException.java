package org.example.customException;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException() {
        super("Сумма перевода должна быть больше 0");
    }
}
