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
                    
                    //stop
                    if (receive.equalsIgnoreCase("exit")) break;
                    
                    //extract operands and operator 
                    String[] operation = receive.split(" ");
                    int a = Integer.parseInt(operation[0]);
                    int b = Integer.parseInt(operation[1]);
                    String operand = operation[2];

                    int result = switch(operand){
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        case "/" -> a / b;
                        default -> 0;
                    };
                    
                    byte[] responseBuffer = String.valueOf(result).getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, 
                                    requestPacket.getAddress(),requestPacket.getPort());
                    socket.send(responsePacket);
                }
            }catch(Exception e){
                    e.printStackTrace();
            }
        }
}