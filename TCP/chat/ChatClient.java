package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    public static void main(String args[]) {
        try (
            Socket socket = new Socket("localhost", 8089);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
        ) {
            String clientMsg = " ", serverMsg = "";
            while (true) {
                if (serverMsg.equals("exit")) 
                    break;

                System.out.print("\nmessage: ");
                clientMsg = scan.nextLine();
                writer.println(clientMsg);

                serverMsg = reader.readLine();
                System.out.println("\nserver: " + serverMsg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/**
 * 
 * SERVER SIDE 
 * 
 * ServerSocket ss = new ServerSocket(8080);
 * Socket s = ss.accept();
 * 
 * BufferedReader reader = new BufferedReader(new InputStream(s.getInputStream()));
 * PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
 * 
 * reader.readLine();
 * writer.println();
 * 
 * CLIENT SIDE 
 * Socket s = new Socket(server, portnumber);
 * 
 * BufferedReader reader = new BufferedReader(new InputStream(s.getInputStream()));
 * PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
 */