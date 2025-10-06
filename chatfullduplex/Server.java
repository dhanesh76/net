package chatfullduplex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    	public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.handleChat(8080);        
    }
}

class ChatServer{

    private ServerSocket serverSocket;
    private Socket socket; 
    private PrintWriter writer;
    private BufferedReader reader;
    private Scanner scanner;
    volatile boolean isExit;

    public void handleChat(int port){
        try{
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);

            Thread senderThread = new Thread(this::send);
            Thread readerThread = new Thread(() -> {
                try {
                    this.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            senderThread.start();
            readerThread.start();

            senderThread.join();
            readerThread.join();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                serverSocket.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(){
        while (!isExit) {
            System.out.print("you: ");
            String message = scanner.nextLine();
            writer.println(message);

            if(message.equalsIgnoreCase("exit")) isExit=true;
        }
    }

    public void read() throws IOException{
        while (!isExit) {
            String message = reader.readLine();
            System.out.println("cleint: " + message);

            if(message.equalsIgnoreCase("exit")) isExit=true;
        }
    }
}

