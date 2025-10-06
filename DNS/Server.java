package DNS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Server{
    public static void main(String args[]) throws UnknownHostException{
        Scanner scan = new Scanner(System.in);

        System.out.print("domain: ");
        String domain = scan.nextLine();

        InetAddress addressByDomain = InetAddress.getByName(domain);
        System.out.println(addressByDomain.getHostAddress());

        System.out.print("ip: ");
        String ip = scan.nextLine();

        InetAddress addressByIp = InetAddress.getByName(ip);
        System.out.println(addressByIp.getHostName());

        scan.close();
    }
}