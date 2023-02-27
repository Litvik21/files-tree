package org.example.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.example.handler.ClientHandler;
import org.example.service.Service;

public class TelnetServer {
    private final int port;
    private final String rootPath;

    public TelnetServer(int port, String rootPath) {
        this.port = port;
        this.rootPath = rootPath;
    }

    public void start() {
        Service service = new Service();
        File rootDirectory = service.setMainDirectory(new File(rootPath));

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, rootDirectory);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
