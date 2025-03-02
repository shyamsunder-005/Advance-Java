import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Read message from client
                String clientMessage = reader.readLine();
                if (clientMessage == null || clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                System.out.println("Client: " + clientMessage);

                // Send response to client
                System.out.print("Server: ");
                String response = consoleReader.readLine();
                output.write((response + "\n").getBytes()); // Convert to bytes + add newline
                output.flush(); // Ensure message is sent immediately
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

