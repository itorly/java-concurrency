package org.itorly.jc.callback;

public class CallbackDemo {
    public static void main(String[] args) {
        Task task = new Task();
        Callback callback = new CallbackImpl();

        System.out.println("Starting task...");
        task.performTask(callback);
        System.out.println("Task is running asynchronously...");

    }
}
