package ViewModel.storage;


import Model.Client;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

/**
 * created by Mehrbod 1.10.2018
 * database of all clients with JSON
 * only one instance is needed so make a singleton class for it
 */
public class ClientStorage {
    //
    // Singleton instance
    //
    private static ClientStorage singletonInstance;

    //
    // all clients
    //
    private ArrayList<Client> clients;

    //
    // storage file name
    //
    private String jsonStorageName;

    /**
     * loads all the clients
     */
    private ClientStorage() {
        clients = new ArrayList<>();
        singletonInstance = new ClientStorage();
        loadClients();
    }

    /**
     * method for using this class
     * @return ClientStorage instance
     */
    public static ClientStorage getClientStorage() {
        return singletonInstance;
    }

    /**
     * checks if the client exists or not
     * then if the client was new, adds the client
     * @param newClient new client to the system
     * @return whether client add or not
     */
    public boolean addClient(Client newClient) {
        if (clients != null && !isClientExists(newClient.getId())) {
            clients.add(newClient);
            return true;
        }
        return false;
    }

    /**
     * first checks whether the client exists or not
     * then if the client exists, removes it
     * @param oldClient the client which has to be removed from the system
     */
    public void removeClient(Client oldClient) {
        if (clients != null && !clients.isEmpty()) {
            for (Client c : clients) {
                if (c.getId().equals(oldClient.getId())) {
                    clients.remove(c);
                    break;
                }
            }
        }
    }

    /**
     * first checks whether the client exists or not
     * if exists, updates the clients
     * mainly for updating ip, port and shared files
     * @param updatedClient existing client which has to be updated
     * @return whether the update occurred or not
     */
    public boolean updateClient(Client updatedClient) {
        if (clients != null && !clients.isEmpty()) {
            for (Client c : clients) {
                if (c.getId().equals(updatedClient.getId())) {
                    c.setId(updatedClient.getIp());
                    c.setPort(updatedClient.getPort());
                    c.setFileNames(updatedClient.getFileNames());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gets the owner of wanted file to start getting it
     * @param fileName wanted file name
     * @return Client which has the file
     */
    @Nullable
    public Client getFileOwnerClient(String fileName) {
        if (clients != null && !clients.isEmpty()) {
            for (Client c : clients) {
                if (c.hasFile(fileName)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * checks whether the client exists or not
     * @param id client to be checked
     * @return whether it exists or not
     */
    private boolean isClientExists(String id) {
        if (clients != null && !clients.isEmpty()) {
            for (Client c : clients) {
                if (c.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * loads all clients to the clients list from JSON with name jsonStorageName
     */
    private void loadClients() {
        if (clients == null) {
            return;
        }

        // TODO: 1/10/2018 Mehrbod complete reading from jsonStorageName and make it async
    }

    /**
     * writes all clients to a json file with name jsonStorageName
     * call this method before closing the program or after every update
     */
    private void writeClients() {
        if (clients == null || clients.size() == 0) {
            return;
        }

        // TODO: 1/10/2018 Mehrbod completing writing to the jsonStorageName and make it async
    }

    /**
     * shows all files name in separate lines
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (clients != null && !clients.isEmpty()) {
            for (Client c : clients) {
                ArrayList<String> filesList = c.getFileNames();

                if (filesList != null) {
                    for (String aFilesList : filesList) {
                        result.append(aFilesList).append("\n");
                    }
                }
            }
        }

        return result.toString();
    }
}
