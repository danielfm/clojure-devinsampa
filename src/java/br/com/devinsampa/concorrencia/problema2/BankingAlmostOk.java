package br.com.devinsampa.concorrencia.problema2;

import java.util.*;

public class BankingAlmostOk {

    public static void concurrencyTest() throws Exception {
        // Shared resources
        final AccountAlmostOk a1 = new AccountAlmostOk(100);
        final AccountAlmostOk a2 = new AccountAlmostOk(100);

        // Transfer money from a1 to a2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                AccountAlmostOk.transfer(a1, a2, 10);
            }
        });

        // Transfer money from a2 to a1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                AccountAlmostOk.transfer(a2, a1, 20);
            }
        });

        // Starts the threads
        t1.start();
        t2.start();

        // Waits for the threads to die
        t1.join();
        t2.join();

        System.out.println("Account 1 balance: " + a1.getBalance());
        System.out.println("Account 2 balance: " + a2.getBalance());
    }

    public static void main(String[] args) throws Exception {
       concurrencyTest();
    }
}

class AccountAlmostOk {
    private boolean active;
    private double balance;

    public AccountAlmostOk(boolean active, double balance) {
        this.active = active;
        this.balance = balance;
    }

    public AccountAlmostOk(double balance) {
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
     * A hack to solve the deadlock problem would be to use the hashCode()
     * value of Object to always lock both accounts in the same order.
     */
    public static void transfer(AccountAlmostOk from, AccountAlmostOk to, double amount) throws BankingException {
        AccountAlmostOk a1, a2;

        // Works as long the two accounts have a different hashcode. So there's
        // a very slightly slight possibility of deadlock! In that case, you
        // must use a third lock! ffuuuuu
        if (System.identityHashCode(from) > System.identityHashCode(to)) {
            a1 = from;
            a2 = to;
        } else {
            a1 = to;
            a2 = from;
        }

        synchronized (a1) {
            try {
                // Gives the other threads a chance to run
                Thread.sleep(500);
            } catch (Exception e) {}

            synchronized (a2) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}

