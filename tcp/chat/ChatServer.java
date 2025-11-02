package chat;

import java.util.*;
import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(8089);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
        ) {
            String clientMsg = "", serverMsg = " ";
            while (true) {
                clientMsg = reader.readLine();
                System.out.println("\nclient: " + clientMsg);

                if (clientMsg.equals("exit")) {
                    writer.println("exit");
                    break;
                }

                System.out.print("\nmessage: ");
                serverMsg = scan.nextLine();
                writer.println(serverMsg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


