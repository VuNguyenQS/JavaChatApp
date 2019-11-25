package client;

import java.net.*;
import java.io.*;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
    
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            ReadThread rdThrd = new ReadThread(socket, this);
            WriteThread wrThrd = new WriteThread(socket, rdThrd, this);
            rdThrd.start();
            wrThrd.start();

            wrThrd.join();
            rdThrd.join();
            
            socket.close();
            
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex){
            System.out.println("I?O error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Chat client interrupted");
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

    public static void main (String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}