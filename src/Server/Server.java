package Server;

import ViewModel.controller.ServerManagerRunnable;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by Mehrbod on 1.17.2018
 * manages the server
 */
public class Server {
    //
    // server port
    //
    private static final int SERVER_PORT = 47021;

    //
    // server info
    //
    private ServerSocket serverSocket = null;
    private String ip;

    private boolean finished = false;

    //
    // server singleton instance
    //
    private static Server serverInstance;

    //
    // thread handler
    //
    private ExecutorService executorService = null;

    private Server() {
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * to be called from main thread
     */
    public void startServer() {
        try {
            initServer();
            listenForConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the server for connections
     * and shows the connection info
     */
    private void initServer() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(SERVER_PORT);
            serverSocket.setReceiveBufferSize(10);
        }

        ip = Inet4Address.getLocalHost().getHostAddress();

        System.out.println("Server has been initialized in port: " + SERVER_PORT + " with ip: " + ip);
    }

    /**
     * listens for connections and starts a new thread
     */
    private void listenForConnections() throws IOException {
        while (!finished) {
            if (serverSocket != null && executorService != null) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ServerManagerRunnable(clientSocket));
            } else if (executorService == null) {
                executorService = Executors.newCachedThreadPool();
            }
        }
    }
}
