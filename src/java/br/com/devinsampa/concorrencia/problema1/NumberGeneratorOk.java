package br.com.devinsampa.concorrencia.problema1;

import java.util.*;

public class NumberGeneratorOk {
    private static final int ITERS = 100000;
    private static final int THREADS = 3;

    private long current = 0;

    /**
     * Solution: synchronize to avoid race condition.
     */
    public synchronized long getNext() {
        return ++this.current;
    }

    public static void concurrencyTest() throws Exception {
        // Shared resource
        final NumberGeneratorOk gen = new NumberGeneratorOk();

        // Synchronized set to store the already generated numbers
        final Set<Long> numbers = Collections.synchronizedSet(new HashSet<Long>());

        // Thread implementation
        Runnable r = new Runnable() {
            public void run() {
                for (int i = 1; i <= 100000; i++) {
                    numbers.add(gen.getNext());

                    // Gives the other threads a chance to run
                    Thread.yield();
                }
            }
        };

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

        System.out.println("Number of unique elements: " + numbers.size());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Number of unique elements: " + THREADS * ITERS + " (expected)");
        for (int i = 0; i < 10; i++) {
            concurrencyTest();
        }
    }
}
