package org.example;

import org.example.server.TelnetServer;

public class Main {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String rootPath = args[1];
        System.out.println("port " + port);
        System.out.println("rootPath " + rootPath);

        TelnetServer server = new TelnetServer(port, rootPath);
        server.start();
    }
}