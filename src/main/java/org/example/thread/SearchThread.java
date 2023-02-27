package org.example.thread;

import java.util.List;
import java.io.File;
import org.example.service.Service;

public class SearchThread extends Thread {
    private final File rootFile;
    private final int depth;
    private final String mask;
    private List<File> resultFiles;

    public SearchThread(File rootFile, int depth, String mask) {
        this.rootFile = rootFile;
        this.depth = depth;
        this.mask = mask;
    }

    public synchronized List<File> getResultFiles() {
        return resultFiles;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
            Service service = new Service();

            List<File> files = service.searchNeededDepth(rootFile, depth);
            resultFiles = service.searchNeededFiles(files, mask);
        }
    }
}
