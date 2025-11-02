package echo;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServer {
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(8089);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in)
        ) {
            String clientMsg = "";
            while (!clientMsg.equals("exit")) {
                clientMsg = reader.readLine();
                System.out.println("\nmessage: " + clientMsg);
                writer.println("echo: " + clientMsg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
