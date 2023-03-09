package org.example;

import java.util.logging.Logger;
import org.example.server.TelnetServer;

public class Main {
    static Logger LOGGER;
    private static final int PORT_INDEX = 0;
    private static final int ROOT_PATH_INDEX = 1;

    public static void main(String[] args) {
        LOGGER = Logger.getLogger(Main.class.getName());

        int port = Integer.parseInt(args[PORT_INDEX]);
        String rootPath = args[ROOT_PATH_INDEX];
        LOGGER.info("port " + port);
        LOGGER.info("rootPath " + rootPath);

        TelnetServer server = new TelnetServer(port, rootPath);
        server.start();
    }
}