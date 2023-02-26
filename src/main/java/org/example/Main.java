package org.example;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();
        File rootFile = service.setMainDirectory(new File("/Users/litvik/Desktop/test"));
        int depth = service.setDepthOfSearching(2);
        String mask = service.setMask("litvik");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        SearchThread searchThread = new SearchThread(rootFile, depth, mask);
        SoutThread soutThread = new SoutThread(searchThread);
        executor.execute(searchThread);
        executor.execute(soutThread);
        Thread.sleep(300);
        executor.shutdownNow();
    }
}