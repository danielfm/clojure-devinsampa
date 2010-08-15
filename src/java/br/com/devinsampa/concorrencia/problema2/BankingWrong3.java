package br.com.devinsampa.concorrencia.problema2;

import java.util.*;

public class BankingWrong3 {

    public static void concurrencyTest() throws Exception {
        // Shared resources
        final AccountWrong3 a1 = new AccountWrong3(100);
        final AccountWrong3 a2 = new AccountWrong3(false, 100);

        // Transfers money from a1 to a2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                AccountWrong3.transfer(a1, a2, 10);
            }
        });

        // Gets the current balance of a1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Account 1 balance: " + a1.getBalance());
            }
        });

        // Starts the threads
        t1.start();
        t2.start();

        // Waits for the threads to die
        t1.join();
        t2.join();

        System.out.println("Account 1 balance: " + a1.getBalance());
    }

    public static void main(String[] args) throws Exception {
        concurrencyTest();
    }
}

class AccountWrong3 {
   private boolean active;
    private double balance;

    public AccountWrong3(boolean active, double balance) {
        this.active = active;
        this.balance = balance;
    }

    public AccountWrong3(double balance) {
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
        try {
            // Gives the other threads a chance to run
            Thread.sleep(500);
        } catch (Exception e) {}
    
        if (!this.active) {
            throw new BankingException("Inactive account");
        }
        this.balance += amount;
    }

    /**
     * Synchronized, but still have a race condition: the lock of 'from' account
     * is released after 'withdraw', but since the 'deposit' on the other
     * account might fail, another thread might request the balance of 'from'
     * and get the wrong result.
     */
    public synchronized static void transfer(AccountWrong3 from, AccountWrong3 to, double amount) throws BankingException {
        double oldBalance = from.getBalance();
        from.withdraw(amount);

        try {
            to.deposit(amount);
        } catch (BankingException e) {
            // Restores the balance
            from.setBalance(oldBalance);

            throw e;
        }
    }
}

