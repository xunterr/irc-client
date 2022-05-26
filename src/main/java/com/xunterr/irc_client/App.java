package com.xunterr.irc_client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Your nickname: ");
        String nickname = scanner.nextLine();

        System.out.print("Your username: ");
        String username = scanner.nextLine();

        System.out.print("Your real name: ");
        String realName = scanner.nextLine();

        IRCClient client = new IRCClient("irc.libera.chat");

        client.connect(nickname, username, realName);

        Runnable r = () -> client.listen();
        Thread listener = new Thread(r);
        listener.start();

        client.join("#linux");

        while(true) {
            String message = scanner.nextLine();
            client.send(message);
        }
    }
}
