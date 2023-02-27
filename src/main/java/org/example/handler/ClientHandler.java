package org.example.handler;

import org.example.service.Service;
import org.example.thread.SearchThread;
import org.example.thread.SoutThread;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final File rootPath;
    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public ClientHandler(Socket clientSocket, File rootPath) {
        this.clientSocket = clientSocket;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        Service service = new Service();
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream();
             Scanner scanner = new Scanner(inputStream)) {
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            printWriter.println("Please enter the search criteria:");
            printWriter.print("Depth (number): ");
            printWriter.flush();
            int depth = service.setDepthOfSearching(Integer.parseInt(scanner.nextLine()));

            printWriter.print("Mask (string): ");
            printWriter.flush();
            String mask = service.setMask(scanner.nextLine());

            printWriter.println("Search criteria set to depth " + depth + " and mask " + mask);

            SearchThread searchThread = new SearchThread(rootPath, depth, mask);
            SoutThread soutThread = new SoutThread(searchThread);
            executor.execute(searchThread);
            executor.execute(soutThread);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executor.shutdownNow();

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
