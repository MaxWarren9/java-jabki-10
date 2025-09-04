package org.example.customException;

public class InvalidTransferAmountException extends Exception {

    public InvalidTransferAmountException() {
        super("Сумма перевода больше суммы баланса");
    }
}
