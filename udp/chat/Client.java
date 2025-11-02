package UDP.chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);) {
            while (true) {
                System.out.print("message: ");
                String msg = scanner.nextLine();
                byte[] requesBuffer = msg.getBytes();
                DatagramPacket requesPacket = new DatagramPacket(requesBuffer, requesBuffer.length, 
                                InetAddress.getByName("localhost"), 8080);
                socket.send(requesPacket);

                if(msg.equalsIgnoreCase("exit")) break;
                
                byte[] reponseBuffer = new byte[100];
                DatagramPacket reponsePacket = new DatagramPacket(reponseBuffer, reponseBuffer.length);
                socket.receive(reponsePacket);
                String serverMsg = new String(reponsePacket.getData(), 0, reponsePacket.getLength());
                
                if(serverMsg.equalsIgnoreCase("exit")) break;
                System.out.println("server: " + serverMsg+"\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}