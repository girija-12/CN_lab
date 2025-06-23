import java.io.*;
import java.net.*;

public class ex_2 {
    public static void main(String[] args) {
        String host = "example.com";
        int port = 80;

        try (Socket socket = new Socket(host, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("GET / HTTP/1.1");
            writer.println("Host: " + host);
            writer.println("Connection: close");
            writer.println();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}