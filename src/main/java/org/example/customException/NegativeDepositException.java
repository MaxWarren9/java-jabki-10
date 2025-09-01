package org.example.customException;

public class NegativeDepositException extends Exception {

    public NegativeDepositException(String message) {
        super(message);
    }
}
