// ============================================
// TCP
// ============================================

// SERVER SIDE
ServerSocket ss = new ServerSocket(8080);
Socket s = ss.accept();

BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

reader.readLine();
writer.println();

// CLIENT SIDE
Socket s = new Socket(server, portnumber);

BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
PrintWriter writer = new PrintWriter(s.getOutputStream(), true);


// ============================================
// UDP
// ============================================

// SERVER SIDE
DatagramSocket socket = new DatagramSocket(PORT);

// Receive the request
byte[] buffer = new byte[1024];
DatagramPacket reqPacket = new DatagramPacket(buffer, buffer.length);
socket.receive(reqPacket);

String req = new String(reqPacket.getData(), 0, reqPacket.getLength());

// Reply
byte[] resBuffer = "response".getBytes();
DatagramPacket resPacket = new DatagramPacket(
    resBuffer,
    resBuffer.length,
    reqPacket.getAddress(),
    reqPacket.getPort()
);

socket.send(resPacket);

// CLIENT SIDE
DatagramSocket clientSocket = new DatagramSocket();

// Send the request
byte[] sendBuffer = "request".getBytes();
DatagramPacket sendPacket = new DatagramPacket(
    sendBuffer,
    sendBuffer.length,
    InetAddress.getByName("localhost"),
    8080
);
clientSocket.send(sendPacket);

// Receive reply
byte[] receiveBuffer = new byte[1024];
DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
clientSocket.receive(receivePacket);

System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));


// ============================================
// DNS
// ============================================

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


// ============================================
// Ping & Traceroute
// ============================================

static boolean ping(String host) throws IOException {
    return InetAddress.getByName(host).isReachable(100);
}

System.out.print("host: ");
String host = scan.nextLine();

System.out.println("Pinging: ");
System.out.println(host + " is " + (ping(host) ? "reachable" : "unreachable"));

String cmd = System.getProperty("os.name").startsWith("Linux") ? "traceroute" : "tracert";

ProcessBuilder pb = new ProcessBuilder(cmd, host);
Process p = pb.start();

BufferedReader reader2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
String line;
while ((line = reader2.readLine()) != null) {
    System.out.println(line);
}
reader2.close();


// ============================================
// Subnetting
// ============================================

System.out.println("CIDR Subnet Calculator");
System.out.print("Enter IP in CIDR format (e.g., 192.168.18.1/24): ");
String input = scan.nextLine();
scan.close();

// Split IP and Prefix
String[] parts = input.split("/");
if (parts.length != 2) {
    System.out.println("Invalid format! Use IP/Prefix, e.g., 192.168.1.1/24");
    return;
}

String ipAddr = parts[0];
int prefix = Integer.parseInt(parts[1]);
if (prefix < 0 || prefix > 32) {
    System.out.println("Invalid prefix! Must be 0-32.");
    return;
}

int hostBits = 32 - prefix;
int ipInt = ipToInt(ipAddr);
int mask = (0xFFFFFFFF << hostBits);

int network = ipInt & mask;
int broadcast = network | ~mask;

int totalHosts = (int) Math.pow(2, hostBits);
int usableHosts = Math.max(0, totalHosts - 2);

String firstUsable = usableHosts > 0 ? intToIp(network + 1) : "N/A";
String lastUsable = usableHosts > 0 ? intToIp(broadcast - 1) : "N/A";

System.out.println("\nSubnet Details");
System.out.println("IP Address       : " + ipAddr);
System.out.println("Prefix Length    : /" + prefix);
System.out.println("Subnet Mask      : " + intToIp(mask));
System.out.println("Network Address  : " + intToIp(network));
System.out.println("Broadcast Address: " + intToIp(broadcast));
System.out.println("Total Hosts      : " + totalHosts);
System.out.println("Usable Hosts     : " + usableHosts);
System.out.println("Usable Range     : " + firstUsable + " - " + lastUsable);

scan.close();


// ============================================
// Helper Functions
// ============================================

private static int ipToInt(String ip) {
    String[] octets = ip.split("\\.");
    int result = 0;
    for (String octet : octets) {
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

// ============================================
// ARP And RARP 
// ============================================
to get entries for the program arp & rarp table: arp -a
