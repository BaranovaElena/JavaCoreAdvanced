import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Map<String,ClientHandler> clients;  //заменила на map тк удобно для личных сообщений
    private AuthService authService = new DBAuthService();

    public Server() {
        this.clients = new HashMap<>();

        try (ServerSocket serverSocket = new ServerSocket(8180)) {
            System.out.println("Server is listening");
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler client) {
        clients.put(client.getName(),client);
        broadcastClientsList();
    }
    public void unsubscribe(ClientHandler client) {
        clients.remove(client.getName());
        broadcastClientsList();
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client: clients.values())
            client.sendMessage(message);
    }
    public void broadcastClientsList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/clients ");
        for (ClientHandler client: clients.values())
            stringBuilder.append(client.getName()).append(" ");

        broadcastMessage(stringBuilder.toString());
    }

    public void sendPrivateMessage(String sender, String destination, String message) {
        if (clients.containsKey(destination)) {
            if (!sender.equalsIgnoreCase(destination))
                clients.get(destination).sendMessage(message);
            clients.get(sender).sendMessage(message);
        }
        else
            clients.get(sender).sendMessage("User "+destination+" does not exist!");
    }

    public AuthService getAuthService() {
        return authService;
    }
}
