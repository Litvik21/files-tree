package org.example;

public class SoutThread extends Thread {
    private final SearchThread searchThread;

    SoutThread(SearchThread searchThread) {
        this.searchThread = searchThread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(searchThread.getResultFiles());
        }

    }
}
