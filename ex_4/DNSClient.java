import java.net.*;
import java.util.Scanner;

public class DNSClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter domain name: ");
        String domain = scanner.nextLine();

        byte[] requestData = domain.getBytes();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        DatagramPacket request = new DatagramPacket(
            requestData, requestData.length, serverAddress, 9999);
        socket.send(request);

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);

        String ip = new String(response.getData(), 0, response.getLength());
        System.out.println("Resolved IP: " + ip);

        socket.close();
    }
}