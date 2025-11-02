package echo;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 8089);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in)
        ) {
            String clientMsg = "", serverMsg;
            while (!clientMsg.equals("exit")) {

                System.out.print("message: ");
                clientMsg = scan.nextLine();
                writer.println(clientMsg);
                
                if (clientMsg.equals("exit")) break;
                
                serverMsg = reader.readLine();
                System.out.println("\n" + serverMsg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
