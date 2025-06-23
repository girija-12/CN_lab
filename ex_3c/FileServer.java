import java.io.*;
import java.net.*;

public class FileServer {
    private static final int PORT = 54321; // Port number for the server
    private static final String FILE_TO_SEND = "testfile.txt"; // File in server directory

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for client...");

            try (Socket socket = serverSocket.accept()) {
                System.out.println("Client connected: " + socket.getInetAddress());

                File file = new File(FILE_TO_SEND);
                System.out.println("File exists: " + file.exists());
                System.out.println("File length: " + file.length());
                byte[] fileBytes = new byte[(int) file.length()];

                try (FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        OutputStream os = socket.getOutputStream()) {

                    bis.read(fileBytes, 0, fileBytes.length);
                    os.write(fileBytes, 0, fileBytes.length);
                    os.flush();

                    System.out.println("File sent successfully.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
