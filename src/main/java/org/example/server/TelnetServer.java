package org.example.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import org.example.handler.ClientHandler;
import org.example.service.TreeService;
import org.example.service.TreeServiceImpl;

public class TelnetServer {
    private final int port;
    private final String rootPath;
    static Logger LOGGER;

    public TelnetServer(int port, String rootPath) {
        this.port = port;
        this.rootPath = rootPath;
    }

    public void start() {
        TreeService service = new TreeServiceImpl();
        LOGGER = Logger.getLogger(TelnetServer.class.getName());
        File rootDirectory = service.setMainDirectory(new File(rootPath));

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, rootDirectory);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error starting server: ", e);
        }
    }
}
