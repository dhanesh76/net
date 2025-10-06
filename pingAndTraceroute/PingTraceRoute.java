import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;
public class PingTraceRoute {

    static boolean ping(String host) throws IOException{
        return InetAddress.getByName(host).isReachable(100);
    }
    
    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)){
            System.out.print("host: ");
            String host = scan.nextLine();

            //ping
            System.out.println("Pinging: ");
            System.out.println(host + " is" + (ping(host) ? " reachable" : "unreachable"));

            String cmd = System.getProperty("os.name").startsWith("Linux") ? "traceroute" : "tracert";

            ProcessBuilder pb = new ProcessBuilder(cmd, host);
            Process p = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
