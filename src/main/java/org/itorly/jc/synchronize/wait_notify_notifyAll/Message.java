package org.itorly.jc.synchronize.wait_notify_notifyAll;

public class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            try {
                wait(); // Wait until a message is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        empty = true;
        notifyAll(); // Notify all waiting threads that the message has been consumed
        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait(); // Wait until the message has been consumed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        empty = false;
        this.message = message;
        notifyAll(); // Notify all waiting threads that a new message is available
    }
}
