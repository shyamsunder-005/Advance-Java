import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final int PORT = 8000;
    private static final int BUFFER_SIZE = 100;
    private static Set<ObjectOutputStream> clientStreams = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat Server is running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientStreams.add(out);

                new Handler(clientSocket, out).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket, ObjectOutputStream out) {
            this.socket = socket;
            this.out = out;
        }

        public void run() {
            try {
                in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object message = in.readObject();
                    if (message instanceof String) {
                        System.out.println("Received: " + message);
                        broadcastMessage((String) message);
                    } else {
                        System.out.println("Unsupported message type.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected: " + socket);
            } finally {
                cleanup();
            }
        }

        private void broadcastMessage(String message) {
            for (ObjectOutputStream clientOut : clientStreams) {
                if (clientOut != out) {
                    try {
                        clientOut.writeObject(message);
                        clientOut.flush();
                    } catch (IOException e) {
                        System.err.println("Error sending message.");
                    }
                }
            }
        }

        private void cleanup() {
            clientStreams.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

