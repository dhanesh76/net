import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);) {
            int option=0, a = 0, b=0;
            while(true){
                System.out.println("Calculator");
                System.out.println("\t1.add 2.sub 3.mul 4.div 5.exit");
                System.out.print("\tchoice: ");
                option = scanner.nextInt();
                
                if(option != 5){
                    System.out.print("\n\tnum1: ");
                    a = scanner.nextInt();
                    
                    System.out.print("\tnum2: ");
                    b = scanner.nextInt();
                }

                String operand  = switch(option){
                    case 1 -> "+";
                    case 2 -> "-";
                    case 3 -> "*";
                    case 4 -> "/";
                    case 5 -> "exit";
                    default -> "_";
                };
                
                if(operand.equals("_")) {System.out.println("invalid operator");continue;};
                String operation = a + " " + b + " " + operand; 
                byte[] requestBuffer = operand.equals("exit") ? "exit".getBytes() : operation.getBytes();

                DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length,
                                InetAddress.getByName("localhost"), 8080);
                socket.send(requestPacket);

                if(operand.equals("exit")) break;

                byte[] responseBuffer = new byte[100];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);

                int result = Integer.parseInt(new String(responsePacket.getData(), 0, responsePacket.getLength()));
                System.out.println("\n\tresult:" + result+"\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * SERVER SIDE 
 * 
 * DatagramSocket socket = new DatagramSocket(PORT);
 * 
 * //recive the request  
 * DatagramPacket reqPacket = new DatagramPacket(buffer, buffer.length);
 * socket.receive(packet);
 * 
 * String req = String(packet.getData(), 0, packet.getLength());
 * 
 * //reply 
 * byte[] buffer = "response".getBytes();
 * DatagramPacket resPacket = new DatagramPacket(buffer, buffer.length, 
 *  reqPacket.getAddress(), reqPacket.getPort());
 * 
 * socket.send(resPacket);
 * 
 * 
 * CLIENT SIDE 
 * DatagramSocket socket = new DatagramSocket();
 * 
 * //send the request  
 * byte[] buffer = "request".getBytes();
 * DatagramPacket reqPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 8080);
 * socket.send(reqPacket);
 *  * 
 * //receive reply  
 * DatagramPacket resPacket = new DatagramPacket(buffer, buffer.length); 
 * socket.receive(resPacket);
 * 
 * System.out.println(new String(resPacket.getData(), 0, resPacket.getLength()));
 */