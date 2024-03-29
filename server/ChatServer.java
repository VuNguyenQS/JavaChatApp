package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user is connected");

                UserThread newuser = new UserThread(socket, this);
                userThreads.add(newuser);
                newuser.start();
            }
        }
        catch (IOException ex) {
            System.out.println("Error in the server:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("we don't recieve any port-number");
            System.exit(0);
        }

        ChatServer server = new ChatServer(Integer.parseInt(args[0]));
        server.execute();
    }

    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser: userThreads) {
            if (aUser != excludeUser)
                aUser.sendMessage(message);
        }
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }
    
    void removeUser(String userName, UserThread aUser) {
        boolean  removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    
}
