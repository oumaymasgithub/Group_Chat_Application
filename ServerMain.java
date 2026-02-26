package main.java.com.chat;

public class ServerMain {
    public static void main(String[] args) {
        int port = 3000;
        new Server(port).start();
    }
}