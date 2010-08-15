package br.com.devinsampa.concorrencia.problema2;

import java.util.*;

public class BankingWrong1 {
    private static final int THREADS = 3;

    public static void concurrencyTest() throws Exception {
        // Shared resource
        final AccountWrong1 a = new AccountWrong1(100);

        // Withdraw $10 from account
        Runnable r = new Runnable() {
            public void run() {
                System.out.println("Withdraw: $10");
                a.withdraw(10);
            }
        };

        System.out.println("Starting transaction...");
        System.out.println("Account balance: " + a.getBalance());

        // Starts some threads to fight for that shared resource
        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(r);
            threads[i].start();
        }

        // Waits for the threads to finish
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println("Account balance: " + a.getBalance());
        System.out.println("Transaction completed");
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        concurrencyTest();
    }
}

class AccountWrong1 {
    private boolean active;
    private double balance;

    public AccountWrong1(boolean active, double balance) {
        this.active = active;
        this.balance = balance;
    }

    public AccountWrong1(double balance) {
        this(true, balance);
    }

    /**
     * Not thread safe.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Not thread safe.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Not thread safe.
     */
    public void withdraw(double amount) throws BankingException {
        if (!this.active) {
            throw new BankingException("Inactive account");
        }
        if (amount > this.balance) {
            throw new BankingException("Insufficient funds");
        }

        // The bytecode expands into something like this
        double local = this.balance;

        try {
            // Gives the other threads a chance to run
            Thread.sleep(500);
        } catch (Exception e) {}

        // Debits the amount
        local -= amount;
        this.balance = local;
    }
}

