import java.io.*;
import java.net.*;

public class FileClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 54321;
    private static final String FILE_TO_SAVE = "received_testfile.txt";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             InputStream is = socket.getInputStream();
             FileOutputStream fos = new FileOutputStream(FILE_TO_SAVE);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            System.out.println("Connected to server, receiving file...");

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.flush();
            System.out.println("File received and saved as " + FILE_TO_SAVE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}