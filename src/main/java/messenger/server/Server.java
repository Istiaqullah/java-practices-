package messenger.server;
import javafx.scene.layout.VBox;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating Server!");
            e.printStackTrace();
            closeEverything(socket, objectInputStream, objectOutputStream);
        }
    }

    public void sendMessageToClient(String messageToClient) {
        try {
            objectOutputStream.writeObject(messageToClient);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            closeEverything(socket, objectInputStream, objectOutputStream);
        }
    }

    public void receiveMessageFromClient(VBox vBox) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String messageFromClient = (String) objectInputStream.readObject();
                    ServerController.addLabel(messageFromClient, vBox);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from the Client!");
                    closeEverything(socket, objectInputStream, objectOutputStream);
                    break;
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
