package br.com.devinsampa.concorrencia.problema2;

import java.util.*;

public class BankingWrong2 {

    public static void concurrencyTest() throws Exception {
        // Shared resources
        final AccountWrong2 a1 = new AccountWrong2(100);
        final AccountWrong2 a2 = new AccountWrong2(100);

        // Transfers money from a1 to a2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                a1.transferTo(a2, 10);
            }
        });

        // Transfers money from a2 to a1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                a2.transferTo(a1, 10);
            }
        });

        // Starts the threads
        t1.start();
        t2.start();

        // Waits for the threads to die
        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Honey, don't wait for me for dinner... I found a deadlock!");
        concurrencyTest();
    }
}

class AccountWrong2 {
    private boolean active;
    private double balance;

    public AccountWrong2(boolean active, double balance) {
        this.active = active;
        this.balance = balance;
    }

    public AccountWrong2(double balance) {
        this(true, balance);
    }

    /**
     * Synchronized to avoid race conditions.
     */
    public synchronized void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Synchronized to avoid race conditions.
     */
    public synchronized double getBalance() {
        return this.balance;
    }

    /**
     * Synchronized to avoid race conditions.
     */
    public synchronized void withdraw(double amount) throws BankingException {
        if (!this.active) {
            throw new BankingException("Inactive account");
        }
        if (amount > this.balance) {
            throw new BankingException("Insufficient funds");
        }

        try {
            // Gives the other threads a chance to run
            Thread.sleep(500);
        } catch (Exception e) {}

        this.balance -= amount;
    }

    /**
     * Synchronized to avoid race conditions.
     */
    public synchronized void deposit(double amount) throws BankingException {
        if (!this.active) {
            throw new BankingException("Inactive account");
        }
        this.balance += amount;
    }

    /**
     * Synchronized method to transfer money from this account to another.
     * It looks thread safe, but it'll lead to a big fat deadlock!
     */
    public synchronized void transferTo(AccountWrong2 to, double amount) throws BankingException {
        double oldBalance = this.getBalance();
        this.withdraw(amount);

        try {
            to.deposit(amount);
        } catch (BankingException e) {
            // Restores the balance
            this.setBalance(oldBalance);

            throw e;
        }
    }
}

