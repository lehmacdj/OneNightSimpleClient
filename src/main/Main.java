package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import roles.*;

public class Main {
    static final int PORT = 9100;
    static final String HOST = "localhost";
    
    static String uuid;
    static final ArrayList<String> roles = new ArrayList<>();
    static final ArrayList<String> players = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
    	
    	//init objects to to use throughout the code
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
        ) {
        	
        	//get the message
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
            	System.out.println(fromServer);
                Scanner parse = new Scanner(fromServer);
                parse.useDelimiter("=");
                ArrayList<String> message = new ArrayList<>();
                while (parse.hasNext()) {
                    message.add(parse.next());
                }
                parse.close();
                if (!message.isEmpty()) {
                    if (message.get(0).equals(">>>")) {
                        out.println(uuid + " " + scan.nextLine());
                    } else if (message.get(0).equals("uuid")) {
                        uuid = message.get(1);
                    } else if (message.get(0).equals("set")) {
                        Scanner toRoles = new Scanner(message.get(1));
                        toRoles.useDelimiter(",");
                        while (toRoles.hasNext()) {
                            roles.add(toRoles.next());
                        }
                        toRoles.close();
                        System.out.println(roles);
                    } else {
                        
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