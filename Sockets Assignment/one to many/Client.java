import java.io.*;
import java.net.*;

public class Client {
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the chat server. Type 'exit' to disconnect.");

            // Thread for receiving messages
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        Object message = in.readObject();
                        if (message instanceof String) {
                            System.out.println("\nMessage: " + message);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            receiveThread.start();

            // Sending messages
            while (true) {
                System.out.print("You: ");
                String message = reader.readLine();
                
                if ("exit".equalsIgnoreCase(message)) {  // Exit condition
                    System.out.println("Disconnecting from server...");
                    break;
                }
                
                out.writeObject(message);
                out.flush();
            }

            // Close the socket and exit
            socket.close();
            receiveThread.interrupt();  // Stop the receiving thread
            System.out.println("Client stopped.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

