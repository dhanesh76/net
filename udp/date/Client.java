package UDP.date;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();){
            byte[] requestBuffer = "What's Time?".getBytes();
            DatagramPacket requestPacket = new DatagramPacket(requestBuffer,requestBuffer.length,
                                                            InetAddress.getByName("localhost"),8080);
            socket.send(requestPacket);

            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);
            
            String serverMsg = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Server Message: " + serverMsg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
