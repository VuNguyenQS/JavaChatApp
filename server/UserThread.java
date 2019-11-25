package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    private String userName;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run () {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            this.userName = userName;
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName; 
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]:" + clientMessage;
                server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));
            
            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);

        }
        catch (IOException ex) {
            System.out.println("Error in UserThread:" + ex.getMessage());
            ex.printStackTrace();
        }
        catch (NullPointerException ex) {
            System.out.println(userName + " has lost connection.");
            server.broadcast(userName + " has lost connection.", this);
        }
        
        try {
            server.removeUser(userName, this);
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            e.printStackTrace();
        }
    }

    void printUsers() {
        if (server.hasUsers()) 
            writer.println("Connected users:" + server.getUserNames());
        else 
            writer.println("No other users connected");
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}