package chatfullduplex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.handleChat("localhost", 8080);
    }
}

class ChatClient{
    private Socket socket; 
    private PrintWriter writer;
    private BufferedReader reader;
    private Scanner scanner;
    volatile boolean isExit;

    public void handleChat(String serverAddress, int serverPort){
        try {
            
            socket = new Socket(serverAddress, serverPort);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);

            Thread readerThread = new Thread(() -> {
                try {
                    read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread senderThread = new Thread(this::send);

            readerThread.start();
            senderThread.start();

            senderThread.join();
            readerThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
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
