package org.example.thread;

import java.util.logging.Logger;

public class OutputInformationThread extends Thread {
    private final SearchThread searchThread;
    static Logger LOGGER;

    public OutputInformationThread(SearchThread searchThread) {
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
            LOGGER = Logger.getLogger(OutputInformationThread.class.getName());
            LOGGER.info("Result files" + searchThread.getResultFiles());
        }
    }
}
