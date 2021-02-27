import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private String name;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;

    public ClientHandler(Integer numID, Server server, Socket socket) {
        name = "Client"+numID;
        this.socket = socket;
        this.server = server;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                server.subscribe(this);
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.equalsIgnoreCase("/end")) {
                            sendMessage("/end");    //чтобы закрыть поток клиента
                            break;
                        }
                        if (message.startsWith("/w")) {
                            //отрезаем /w, делим на имя получателя и сообщение
                            String[] msgParts = message.substring(3).split(" ",2);
                            server.sendPrivateMessage(name, msgParts[0], name+": "+msgParts[1]);
                        }
                        else
                            server.broadcastMessage(name+": "+message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    disconnectClient();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnectClient() {
        server.unsubscribe(this);
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(name+" is disconnected!");
    }

    public String getName() {
        return name;
    }
}
