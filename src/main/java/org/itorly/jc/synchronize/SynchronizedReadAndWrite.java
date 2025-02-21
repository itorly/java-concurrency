package org.itorly.jc.synchronize;

public class SynchronizedReadAndWrite {

    private int ticket = 100;

    public synchronized int getTicket() {
        return this.ticket;
    }

    public synchronized void buy(int number) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ticket = ticket - number;
    }
}
