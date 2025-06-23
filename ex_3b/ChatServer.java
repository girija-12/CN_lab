import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("New client connected: " + client.getInetAddress());
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                out = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(out);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            if (writer != out) {
                                writer.println(message);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected.");
            } finally {
                if (out != null) {
                    clientWriters.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}