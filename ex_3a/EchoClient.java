// EchoClient.java
import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverHost, port);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the echo server. Type messages to send:");

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                out.println(userInput);
                String response = in.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}