package client;

import java.io.*;
import java.net.*;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
    private ReadThread rdThrd;

    public WriteThread(Socket socket, ReadThread rdThrd, ChatClient client) {
        this.socket = socket;
        this.client = client;
        this.rdThrd = rdThrd;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void run() {
            Console console = System.console();

            String userName = console.readLine("\nEnter your name: ");
            client.setUserName(userName);
            writer.println(userName);

            String text;

            do {
                if (!rdThrd.isAlive()) {
                    System.out.println("Connection to the server has lost");
                    break;
                }
                text = console.readLine("[" + userName + "]: ");
                writer.println(text);
            } while (!text.equals("bye"));
        }

}