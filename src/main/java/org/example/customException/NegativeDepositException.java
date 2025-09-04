package org.example.customException;

public class NegativeDepositException extends Exception {

    public NegativeDepositException() {
        super("Введено отрицательное число");
    }
}
