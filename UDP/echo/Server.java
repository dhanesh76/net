package UDP.echo;

import java.net.*;

public class Server{
        private static final int  PORT = 8080;
        public static void main(String[] args){
            try(DatagramSocket socket  = new DatagramSocket(PORT);){
                    while(true){
                        byte[] requestBuffer = new byte[1024];
                        DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                        socket.receive(requestPacket);
                        String receive = new String(requestPacket.getData(), 0, requestPacket.getLength());
                        System.out.println("cleint: " + receive);
                        DatagramPacket response = new DatagramPacket(requestPacket.getData(),requestPacket.getLength(),
                                                                requestPacket.getAddress(),requestPacket.getPort());
                        System.out.println(receive);
                        if(receive.equalsIgnoreCase("exit")) break;
                        socket.send(response);
                    }
            }catch(Exception e){
                    e.printStackTrace();
            }
        }
}