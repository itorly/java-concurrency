package org.itorly.jc.callback;

public class CallbackImpl implements Callback {
    @Override
    public void onComplete(Object result) {
        String s = String.valueOf(result);
        System.out.println("Task completed with result: " + s);
    }
}
