import java.util.Scanner;

public class Subnet {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("CIDR Subnet Calculator");
        System.out.print("Enter IP in CIDR format (e.g., 192.168.18.1/24): ");
        String input = scan.nextLine();
        scan.close();

        // Split IP and Prefix
        String[] parts = input.split("/");
        if(parts.length != 2) {
            System.out.println("Invalid format! Use IP/Prefix, e.g., 192.168.1.1/24");
            return;
        }

        String ip = parts[0];
        int prefix;
        prefix = Integer.parseInt(parts[1]);
        if(prefix < 0 || prefix > 32){
            System.out.println("Invalid prefix! Must be 0-32.");
            return;
        }
        

        int hostBits = 32 - prefix;
        int ipInt = ipToInt(ip);
        int mask = (0xFFFFFFFF << hostBits);

        int network = ipInt & mask;
        int broadcast = network | ~mask;

        int totalHosts = (int)Math.pow(2, hostBits);
        int usableHosts = Math.max(0, totalHosts - 2);

        String firstUsable = usableHosts > 0 ? intToIp(network + 1) : "N/A";
        String lastUsable = usableHosts > 0 ? intToIp(broadcast - 1) : "N/A";

        System.out.println("\nSubnet Details");
        System.out.println("IP Address       : " + ip);
        System.out.println("Prefix Length    : /" + prefix);
        System.out.println("Subnet Mask      : " + intToIp(mask));
        System.out.println("Network Address  : " + intToIp(network));
        System.out.println("Broadcast Address: " + intToIp(broadcast));
        System.out.println("Total Hosts      : " + totalHosts);
        System.out.println("Usable Hosts     : " + usableHosts);
        System.out.println("Usable Range     : " + firstUsable + " - " + lastUsable);

        scan.close();
    }

    private static int ipToInt(String ip) {
        String[] octets = ip.split("\\.");
        int result = 0;
        for(String octet : octets) {
            result = (result << 8) | Integer.parseInt(octet);
        }
        return result;
    }

    private static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }
}
