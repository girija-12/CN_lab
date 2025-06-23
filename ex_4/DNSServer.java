import java.net.*;
import java.util.*;

public class DNSServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] buffer = new byte[1024];
        Map<String, String> dnsTable = new HashMap<>();
        dnsTable.put("example.com", "93.184.216.34");
        dnsTable.put("google.com", "142.250.183.206");
        dnsTable.put("openai.com", "104.18.12.123");

        System.out.println("DNS Server is running...");

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String domain = new String(request.getData(), 0, request.getLength());
            String ip = dnsTable.getOrDefault(domain.trim(), "Domain not found");

            byte[] responseData = ip.getBytes();
            DatagramPacket response = new DatagramPacket(
                responseData, responseData.length,
                request.getAddress(), request.getPort());

            socket.send(response);
        }
    }
}