package com.xunterr.irc_client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class IRCClient {
    private final String hostname;
    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    public boolean closed;
    private String channel;

    public IRCClient(String hostname) throws IOException {
        this.hostname = hostname;
        this.socket = new Socket(this.hostname, 6667);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new Scanner(socket.getInputStream());
    }

    public void connect(String nickname, String username, String realName){
        write("NICK", nickname);
        write("USER", username + " 0 * :" + realName);
        this.closed = false;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        this.closed = true;
    }

    public void listen(){
        while(in.hasNext()){
            String servermessage = in.nextLine();
            System.out.println("<<< " + servermessage);
            if(servermessage.startsWith("PING")){
                out.print("PONG " + servermessage.split(" ", 2)[1]);
                out.flush();
            }
        }
    }

    public void join(String channel){
        this.channel = channel;
        write("JOIN", channel);
    }

    private void write(String command, String message){
        String finalMessage = command + " " + message;
        System.out.println(">>> " + finalMessage);
        out.print(finalMessage + "\r\n");
        out.flush();
    }

    public void send(String message) {
        write("PRIVMSG " + channel + " :", message);
    }
}
