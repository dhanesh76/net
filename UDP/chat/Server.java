package UDP.chat;

import java.net.*;
import java.util.Scanner;

public class Server{
        private static final int  PORT = 8080;
        public static void main(String[] args){
            try(DatagramSocket socket  = new DatagramSocket(PORT);
            Scanner scanner = new Scanner(System.in)){
                while (true) {
                    byte[] requestBuffer = new byte[1024];
                    DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                    socket.receive(requestPacket);
                    String receive = new String(requestPacket.getData(), 0, requestPacket.getLength());
                    System.out.println("cleint: " + receive);
                    
                    if (receive.equalsIgnoreCase("exit")) break;

                    System.out.print("message: ");
                    String msg = scanner.nextLine();
                    byte[] responseBuffer = msg.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, 
                                    requestPacket.getAddress(),requestPacket.getPort());
                    socket.send(responsePacket);
                    System.out.println();
                    if(msg.equalsIgnoreCase("exit")) break;
                }
            }catch(Exception e){
                    e.printStackTrace();
            }
        }
}