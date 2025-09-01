package org.example.customException;

public class InvalidTransferAmountException extends Exception {

    public InvalidTransferAmountException() {
    }

    public InvalidTransferAmountException(String message) {
        super(message);
    }
}
