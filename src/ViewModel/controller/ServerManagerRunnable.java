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
    private String response;

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
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the clientRequest String array
     * @throws IOException for DataInputStream
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
     *         REMOVEFILES for removing files
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
            case "REMOVEFILES":
                removeOldFiles();
                break;
            default:
        }
        sendResponse();
    }

    /**
     * adds current client
     */
    private void addMember() {
        StringBuilder result = new StringBuilder();
        if (clientStorage.addClient(new Client(clientId, clientIp, clientPort))) {
            System.out.println(clientId + " has been added.");
            result.append("ADDMEMBERRESPONSE")
                    .append("\n")
                    .append(clientId)
                    .append("\n")
                    .append(clientId)
                    .append(" has been added.")
                    .append("\n");
        } else {
            System.out.println(clientId + " cannot be added. Already exists.");
            result.append("ADDMEMBERRESPONSE")
                    .append("\n")
                    .append(clientId)
                    .append("\n")
                    .append(clientId)
                    .append(" cannot be added. Already exists.")
                    .append("\n");
        }
        response = result.toString();
    }

    /**
     * removes current client
     */
    private void removeMember() {
        StringBuilder result = new StringBuilder();
        clientStorage.removeClient(new Client(clientId, clientIp, clientPort));
        System.out.println(clientId + " has been removed.");
        result
                .append("REMOVEMEMBERRESPONSE")
                .append("\n")
                .append(clientId)
                .append("\n")
                .append(clientId)
                .append(" has been removed.")
                .append("\n");

        response = result.toString();
    }

    private void sendAllFilesNames() {

    }

    private void sendFileOwner() {

    }

    private void addNewFiles() {

    }

    private void removeOldFiles() {

    }

    /*
        closes current connection
     */
    private void closeConnection() {
        if (!clientSocket.isConnected()) {
            try {
                clientSocket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * sends the respond to a request
     */
    private void sendResponse() {
        try {
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
