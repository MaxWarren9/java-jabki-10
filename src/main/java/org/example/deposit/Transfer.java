package org.example.deposit;

import org.example.customException.InsufficientBalanceException;
import org.example.customException.InvalidTransferAmountException;

public class Transfer {

    public static void transfer(Account from, Account to, int amount) throws InsufficientBalanceException, InvalidTransferAmountException {
        if (amount <= 0) {
            throw new InsufficientBalanceException("Сумма перевода должна быть больше 0");
        }
        if (from.getBalance() < amount) {
            throw new InvalidTransferAmountException("Сумма перевода больше суммы баланса");
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }
}
