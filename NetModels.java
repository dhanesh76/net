//TCP
//SERVER SIDE 
  
  ServerSocket ss = new ServerSocket(8080);
  Socket s = ss.accept();
  
  BufferedReader reader = new BufferedReader(new InputStream(s.getInputStream()));
  PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
  
  reader.readLine();
  writer.println();
  
//CLIENT SIDE 
  Socket s = new Socket(server, portnumber);
  
  BufferedReader reader = new BufferedReader(new InputStream(s.getInputStream()));
  PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

//-----------------------------------------------

//UDP
//SERVER SIDE 
  
  DatagramSocket socket = new DatagramSocket(PORT);
  
  //recive the request  
  DatagramPacket reqPacket = new DatagramPacket(buffer, buffer.length);
  socket.receive(packet);
  
  String req = String(packet.getData(), 0, packet.getLength());
  
  //reply 
  byte[] buffer = "response".getBytes();
  DatagramPacket resPacket = new DatagramPacket(buffer, buffer.length, 
   reqPacket.getAddress(), reqPacket.getPort());
  
  socket.send(resPacket);
  
  
//CLIENT SIDE 
  DatagramSocket socket = new DatagramSocket();
  
  //send the request  
  byte[] buffer = "request".getBytes();
  DatagramPacket reqPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 8080);
  socket.send(reqPacket);
    
  //receive reply  
  DatagramPacket resPacket = new DatagramPacket(buffer, buffer.length); 
  socket.receive(resPacket);
  
  System.out.println(new String(resPacket.getData(), 0, resPacket.getLength()));

// --------------------------------------------------------------
//DNS

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
 
//---------------------------------------------------------------
//ping


static boolean ping(String host) throws IOException{
    return InetAddress.getByName(host).isReachable(100);
}

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

//----------------------------------------
//subnetting

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