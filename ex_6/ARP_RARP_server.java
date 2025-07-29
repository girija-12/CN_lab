import java.io.*;
import java.net.*;
import java.util.*;

public class ARP_RARP_server {
    public static void main(String[] args) throws IOException {
        Map<String, String> ipToMac = new HashMap<>();
        Map<String, String> macToIp = new HashMap<>();

        ipToMac.put("192.168.0.1", "AA:BB:CC:11:22:33");
        ipToMac.put("192.168.0.2", "AA:BB:CC:11:22:44");
        ipToMac.put("192.168.0.3", "AA:BB:CC:11:22:55");

        // Populate reverse map for RARP
        for (Map.Entry<String, String> entry : ipToMac.entrySet()) {
            macToIp.put(entry.getValue(), entry.getKey());
        }

        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server is running and waiting for client connection...");

        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String requestType = in.readLine(); // "ARP" or "RARP"
        String inputValue = in.readLine();

        String result;
        if (requestType.equalsIgnoreCase("ARP")) {
            result = ipToMac.getOrDefault(inputValue, "MAC address not found for IP: " + inputValue);
        } else if (requestType.equalsIgnoreCase("RARP")) {
            result = macToIp.getOrDefault(inputValue, "IP address not found for MAC: " + inputValue);
        } else {
            result = "Invalid request type.";
        }

        out.println(result);
        socket.close();
        serverSocket.close();
    }
}