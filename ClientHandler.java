package main.java.com.chat;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username = "Guest";
    private boolean readOnly = false;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public String getUsername() { return username; }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String name = in.readUTF().trim();
            if (name.isEmpty()) {
                readOnly = true;
                username = "Guest-" + socket.getPort();
            } else {
                username = name;
            }

            send("Welcome " + username + (readOnly ? " (READ-ONLY)" : ""));
            server.broadcast(username + " has joined the chat", this);

            while (true) {
                String msg = in.readUTF().trim();

                if (msg.equalsIgnoreCase("allUsers")) {
                    send(server.getAllUsers());
                } else if (msg.equalsIgnoreCase("bye") || msg.equalsIgnoreCase("end")) {
                    server.remove(this);
                    server.broadcast(username + " has left the chat", this);
                    break;
                } else {
                    if (!readOnly) {
                        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                        server.broadcast("[" + username + " " + time + "]: " + msg, this);
                    } else {
                        send("READ-ONLY: you cannot send messages.");
                    }
                }
            }
        } catch (EOFException e) {
            // client closed abruptly
        } catch (IOException e) {
            // connection lost
        } finally {
            server.remove(this);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    public void send(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException ignored) {}
    }
}