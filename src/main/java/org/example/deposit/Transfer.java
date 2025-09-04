package org.example.deposit;

import org.example.customException.InsufficientBalanceException;
import org.example.customException.InvalidTransferAmountException;

public class Transfer {

    public static void transfer(Account from, Account to, double amount) throws InsufficientBalanceException, InvalidTransferAmountException {
        if (amount <= 0) {
            throw new InvalidTransferAmountException();
        }
        if (from.getBalance() < amount) {
            throw new InsufficientBalanceException();
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }
}
