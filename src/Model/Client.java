package Model;

import java.util.ArrayList;

/**
 * created by Mehrbod 1.10.2018
 * model of clients
 */
public class Client {
    //
    // client connection info
    //
    private String id;
    private String ip;
    private int port;

    //
    // client shared files info
    //
    private ArrayList<String> fileNames;

    //
    // client time to live
    //
    private int ttl;

    /**
     * initializes variables
     */
    public Client() {
        id = "";
        ip = "";
        port = 0;
        fileNames = new ArrayList<>();
        ttl = 60;
    }


    /**
     * should be checked whether the id has been taken before or not
     * @param id client's id
     * @param ip client's current ip
     * @param port client's current port
     */
    public Client(String id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;

        this.fileNames = new ArrayList<>();

        this.ttl = 60;
    }

    /**
     * be sure to check whether the id is taken before or not then call this method
     * @param id a String which shows the id of the client to refer to it in the future
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return current client's id
     */
    public String getId() {
        return this.id;
    }

    /**
     * sets the ttl of the current user
     * if a user hasn't been around for more than 60 seconds, it should be deleted from storage
     * @param ttl int time to live for the current client
     */
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    /**
     * gets the current user's ttl
     * @return int time to live
     */
    public int getTtl() {
        return this.ttl;
    }

    /**
     * reduces one point of current user's ttl
     * it should be called every 60 seconds
     */
    public void decreseTtl() {
        ttl--;
    }

    /**
     * for every connection, it should be changed
     * @param ip a String which shows the client's latest ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return current client's ip
     */
    public String getIp() {
        return this.ip;
    }


    /**
     * should be changed after every connection by every single client
     * @param port an int for the running port of the current client
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return int current client's port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * adds a String to the current client's shared files
     * @param fileName a String to be added to last files
     */
    public void addFileName(String fileName) {
        if (fileNames == null) {
            return;
        }

        if (!fileNames.contains(fileName)) {
            fileNames.add(fileName);
        }
    }


    /**
     * removes the name given from list of all shared files of the current client
     * it is case sensitive
     * @param fileName String of the file's name that should be removed
     */
    public void removeFileName(String fileName) {
        if (fileNames == null) {
            return;
        }

        if (fileNames.contains(fileName)) {
            fileNames.remove(fileName);
        }
    }

    /**
     * sets an ArrayList of Strings as the current client's shared files
     * @param fileNames ArrayList<String>
     */
    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    /**
     * @return list of current client's files
     */
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    /**
     * checks whether current client has requested file or not
     * case sensitive
     * @param fileName String
     * @return boolean
     */
    public boolean hasFile(String fileName) {
        return fileNames != null && fileNames.contains(fileName);
    }
}
