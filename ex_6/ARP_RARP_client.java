import java.io.*;
import java.net.*;

public class ARP_RARP_client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9999);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose an option:");
        System.out.println("1. ARP (IP → MAC)");
        System.out.println("2. RARP (MAC → IP)");
        System.out.print("Enter choice (1 or 2): ");
        int choice = Integer.parseInt(userInput.readLine());

        String requestType = (choice == 1) ? "ARP" : "RARP";
        String input;

        if (choice == 1) {
            System.out.print("Enter IP Address: ");
            input = userInput.readLine();
        } else {
            System.out.print("Enter MAC Address: ");
            input = userInput.readLine();
        }

        out.println(requestType);
        out.println(input);

        String response = in.readLine();
        System.out.println("Response from Server: " + response);

        socket.close();
    }
}