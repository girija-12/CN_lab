import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("Connected to chat server.");

            Thread receiveThread = new Thread(() -> {
                String msgFromServer;
                try {
                    while ((msgFromServer = serverReader.readLine()) != null) {
                        System.out.println(msgFromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });

            receiveThread.start();

            String msgToSend;
            while ((msgToSend = consoleReader.readLine()) != null) {
                out.println(msgToSend);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}