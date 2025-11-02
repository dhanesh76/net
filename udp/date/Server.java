package UDP.date;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.Instant;

public class Server {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket(8080);){
            byte[] requestBuffer = new byte[1024];
            DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
            socket.receive(requestPacket);

            String clientMsg = new String(requestPacket.getData(), 0, requestPacket.getLength());
            System.out.println("Client Message: " + clientMsg);

            String s = "Current Time: " + Instant.now();
            byte[] sendBuffer =  s.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                                            requestPacket.getAddress(),requestPacket.getPort());
            System.out.println(requestPacket.getPort());
            socket.send(sendPacket);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
