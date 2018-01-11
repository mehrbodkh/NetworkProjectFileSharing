package ViewModel.controller;

import Model.Client;
import ViewModel.storage.ClientStorage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerManagerRunnable implements Runnable{
    //
    // client socket
    //
    private Socket clientSocket;

    //
    // client information
    //
    private String clientIp;
    private int clientPort;
    private String clientId;
    private String[] clientRequest;

    //
    // client variables
    //
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    //
    // clients storage
    //
    private ClientStorage clientStorage;

    public ServerManagerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.clientStorage = ClientStorage.getClientStorage();
        initConnectionInformation();
    }

    /**
     * finds client's ip and port
     */
    private void initConnectionInformation() {
        if (clientSocket != null) {
            clientIp = clientSocket.getInetAddress().getHostAddress();
            clientPort = clientSocket.getPort();
        }
    }

    @Override
    public void run() {
        try {
            parseRequest();
            determineClientId();
            respondToRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the clientRequest String array
     * @throws IOException
     */
    private void parseRequest() throws IOException {
        if (clientSocket != null) {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            clientRequest = dataInputStream.readUTF().split("\n");
        }
    }

    /**
     * initializes the client's id
     */
    private void determineClientId() {
        if (clientRequest.length != 0) {
            clientId = clientRequest[1];
        }
    }

    /**
     * determines the request
     * @return ADDME for adding member
     *         REMOVEME for removing
     *         REQUESTFILEOWNER for requesting one file owner
     *         REQUESTALLFILES for getting all shared files names list
     *         ADDFILES for adding new files to an already existed client
     */
    private String determineRequest() {
        if (clientRequest.length != 0) {
            return clientRequest[0];
        }
        return "";
    }

    /**
     * calls the methods related to every request
     */
    private void respondToRequest() {
        switch (determineRequest()) {
            case "ADDME":
                addMember();
                break;
            case "REMOVEME":
                removeMember();
                break;
            case "REQUESTFILEOWNER":
                sendFileOwner();
                break;
            case "REQUESTALLFILES":
                sendAllFilesNames();
                break;
            case "ADDFIELS":
                addNewFiles();
                break;
            default:
        }
    }

    /**
     * adds current client
     */
    private void addMember() {
        if (clientStorage.addClient(new Client(clientId, clientIp, clientPort))) {
            System.out.println(clientId + " has been added.");
        } else {
            System.out.println(clientId + " cannot be added. Already exists.");
        }
    }

    /**
     * removes current client
     */
    private void removeMember() {
        clientStorage.removeClient(new Client(clientId, clientIp, clientPort));
        System.out.println(clientId + " has been removed.");
    }

    private void sendAllFilesNames() {

    }

    private void sendFileOwner() {

    }

    private void addNewFiles() {

    }

}
