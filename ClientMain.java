package com.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        String host = (args.length >= 1) ? args[0] : "localhost";
        int port = (args.length >= 2) ? Integer.parseInt(args[1]) : 3000;

        Scanner sc = new Scanner(System.in);
        System.out.print("Username (blank = read-only): ");
        String username = sc.nextLine();

        Socket socket = new Socket(host, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(username);
        out.flush();

        Thread listener = new Thread(() -> {
            try {
                while (true) System.out.println(in.readUTF());
            } catch (IOException e) {
                System.out.println("Disconnected.");
            }
        });
        listener.setDaemon(true);
        listener.start();

        while (true) {
            String msg = sc.nextLine();
            out.writeUTF(msg);
            out.flush();
            if (msg.equalsIgnoreCase("bye") || msg.equalsIgnoreCase("end")) break;
        }

        socket.close();
    }
}