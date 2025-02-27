package org.itorly.jc.wait_notify_notifyAll;

public class Producer implements Runnable {
    private Message message;

    public Producer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {"Message 1", "Message 2", "Message 3", "Message 4"};
        for (String msg : messages) {
            message.write(msg);
            System.out.println("Produced: " + msg);
            try {
                Thread.sleep(1000); // Simulate time taken to produce a message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
        message.write("Finished"); // Signal that no more messages will be produced
    }
}
