package org.example.handler;

import org.example.service.TreeService;
import org.example.service.TreeServiceImpl;
import org.example.thread.SearchThread;
import org.example.thread.OutputInformationThread;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final File rootPath;
    private static final int COUNT_OF_THREAD = 2;
    private static final ExecutorService executor = Executors.newFixedThreadPool(COUNT_OF_THREAD);

    public ClientHandler(Socket clientSocket, File rootPath) {
        this.clientSocket = clientSocket;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        TreeService service = new TreeServiceImpl();
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
            OutputInformationThread outputInformationThread = new OutputInformationThread(searchThread);
            executor.execute(searchThread);
            executor.execute(outputInformationThread);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executor.shutdownNow();

        } catch (IOException e) {
            throw new RuntimeException("Error handling client: ", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
