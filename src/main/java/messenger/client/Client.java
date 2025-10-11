package messenger.client;
import javafx.scene.layout.VBox;
import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating Client!");
            e.printStackTrace();
            closeEverything(socket, objectInputStream, objectOutputStream);
        }
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

    public void sendMessageToServer(String messageToServer) {
        try {
            objectOutputStream.writeObject(messageToServer);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Server!");
            closeEverything(socket, objectInputStream, objectOutputStream);
        }
    }

    public void receiveMessageFromServer(VBox vbox_messages) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String messageFromServer = (String) objectInputStream.readObject();
                    ClientController.addLabel(messageFromServer, vbox_messages);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from the Server!");
                    closeEverything(socket, objectInputStream, objectOutputStream);
                    break;
                }
            }
        }).start();
    }
}
