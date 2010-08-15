package br.com.devinsampa.concorrencia.problema2;

import java.util.*;

public class BankingWrong4 {

    public static void concurrencyTest() throws Exception {
        // Shared resources
        final AccountWrong4 a1 = new AccountWrong4(100);
        final AccountWrong4 a2 = new AccountWrong4(100);

        // Transfer money from a1 to a2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                AccountWrong4.transfer(a1, a2, 10);
            }
        });

        // Transfer money from a2 to a1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                AccountWrong4.transfer(a2, a1, 20);
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

class AccountWrong4 {
    private boolean active;
    private double balance;

    public AccountWrong4(boolean active, double balance) {
        this.active = active;
        this.balance = balance;
    }

    public AccountWrong4(double balance) {
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
    public static void transfer(AccountWrong4 from, AccountWrong4 to, double amount) throws BankingException {
        synchronized (from) {
            try {
                // Gives the other threads a chance to run
                Thread.sleep(500);
            } catch (Exception e) {}

            synchronized (to) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}

