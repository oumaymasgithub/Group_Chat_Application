package main.java.com.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final int port;
    private final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Server Started on port " + port);

        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for Client...");
                Socket socket = ss.accept();

                ClientHandler handler = new ClientHandler(socket, this);
                clients.add(handler);

                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String msg, ClientHandler sender) {
        for (ClientHandler c : clients) {
            if (c != sender) {
                c.send(msg);
            }
        }
    }

    public void remove(ClientHandler handler) {
        clients.remove(handler);
    }

    public String getAllUsers() {
        StringBuilder sb = new StringBuilder("Users: ");
        for (ClientHandler c : clients) {
            sb.append(c.getUsername()).append(" ");
        }
        return sb.toString().trim();
    }
}