package View;

import Server.Server;

/**
 * created by Mehrbod 1.17.2018
 */
public class MainServer {
    public static void main(String[] args) {
        Server.getServerInstance().startServer();
    }
}
