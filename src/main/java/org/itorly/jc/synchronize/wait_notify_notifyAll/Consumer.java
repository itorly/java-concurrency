package org.itorly.jc.synchronize.wait_notify_notifyAll;

public class Consumer implements Runnable {
    private Message message;

    public Consumer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()) {
            System.out.println("Consumed: " + latestMessage);
            try {
                Thread.sleep(1000); // Simulate time taken to consume a message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }
}
