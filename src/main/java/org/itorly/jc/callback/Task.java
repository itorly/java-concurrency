package org.itorly.jc.callback;

public class Task {

    public void performTask(Callback callback)
    {
        new Thread(()-> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callback.onComplete("Success");
        }).start();
    }
}
