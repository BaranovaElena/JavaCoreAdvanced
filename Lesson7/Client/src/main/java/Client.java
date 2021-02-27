import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;
    Thread readerMessages;

    public Client() {
        try {
            socket = new Socket("localhost",8180);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            scanner = new Scanner(System.in);

            readerMessages = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.equalsIgnoreCase("/end"))
                            break;
                        else if (message.equalsIgnoreCase("/clients")){
                            System.out.println(message);
                        }
                        else {
                            System.out.println(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    disconnect();
                }
            });
            readerMessages.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getMessages();
    }

    private void getMessages() {
        String outputMessage;
        try {
            do{
                outputMessage = scanner.nextLine();
                out.writeUTF(outputMessage);
                out.flush();
            } while (!outputMessage.equals("/end"));
            readerMessages.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
