package org.example.customException;

import java.security.spec.ECFieldF2m;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException() {
    }
}
