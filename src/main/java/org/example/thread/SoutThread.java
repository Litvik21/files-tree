package org.example.thread;

public class SoutThread extends Thread {
    private final SearchThread searchThread;

    public SoutThread(SearchThread searchThread) {
        this.searchThread = searchThread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(searchThread.getResultFiles());
        }

    }
}
