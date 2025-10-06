import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(
            Socket socket = new Socket("localhost", 8080);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            System.out.println("Data from Server: " + reader.readLine());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
