package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static final int PORT = 9100;
    static final String HOST = "localhost";
    static String uuid;
    
    public static void main(String[] args) throws IOException {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
        ) {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                Scanner parse = new Scanner(fromServer);
                parse.useDelimiter("=");
                ArrayList<String> message = new ArrayList<>();
                while (parse.hasNext()) {
                    message.add(parse.next());
                }
                if (!message.isEmpty()) {
                    if (message.get(0).equals(">>>")) {
                        System.out.print(">>>");
                        out.println(uuid + " " + scan.nextLine());
                    } else if (message.get(0).equals("uuid")) {
                        uuid = message.get(1);
                    } else {
                        System.out.println(fromServer);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + HOST);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Server is offline or not responding.  Please try again later.");
            System.exit(0);
        }
    }    
}