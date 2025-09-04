package org.example.deposit;

import org.example.customException.NegativeDepositException;

public class Account {
    private String id;
    private Integer balance;

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return this.id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "id: "+ id + "\nbalance: " + balance.toString();
    }

    public void deposit(int amount) throws NegativeDepositException {
        if (amount < 0) {
            throw new NegativeDepositException("Введено отрицательное число");
        }
        balance += amount;
    }

}
