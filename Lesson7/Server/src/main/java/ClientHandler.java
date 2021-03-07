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

    public ClientHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                boolean continueChat = true;
                try {
                    long start = System.currentTimeMillis();
                    while (continueChat) { // цикл авторизации
                        //при прошествии 120с отключаемся
                        if ((System.currentTimeMillis() - start) > 120000) {
                            continueChat = false;
                            sendMessage("/error timeout");
                        }
                        //чтобы не застрять на readUTF, заходим туда только если появилось сообщение в буфере
                        //те пока нет данных в буфере, крутим в цикле только условие про 120с
                        else if (in.available() > 0)
                        {
                            String message = in.readUTF();
                            if (message.startsWith("/auth")) {
                                String[] tokens = message.split(" ");
                                name = server.getAuthService().getNicknameByLoginAndPassword(tokens[1], tokens[2]);
                                if (name != null) {
                                    sendMessage("/authok");
                                    server.subscribe(this);
                                    System.out.println(name + " is connected!");
                                    break;
                                } else {
                                    sendMessage("/error"); //тут можно еще текст ошибки писать, а на клиенте его ловить и печатать.
                                }
                            } else if (message.equalsIgnoreCase("/end")) {
                                continueChat = false;
                            }
                        }
                    }
                    while (continueChat) {
                        String message = in.readUTF();
                        if (message.equalsIgnoreCase("/end")) {
                            sendMessage("/end");    //чтобы закрыть поток клиента
                            continueChat = false;
                        } else if (message.startsWith("/w")) {
                            String[] msgParts = message.substring(3).split(" ", 2);
                            server.sendPrivateMessage(name, msgParts[0], name + ": " + msgParts[1]);
                        } else
                            server.broadcastMessage(name + ": " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
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
        if (name != null)
            System.out.println(name + " is disconnected!");
    }

    public String getName() {
        return name;
    }
}
