import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {
    public static void main(String[] args) {
        startConsoleServer();
    }

    private static void startConsoleServer() {
        try (ServerSocket serverSocket = new ServerSocket(8180)){
            System.out.println("Server started");

            try (Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream());
                 Scanner scanner = new Scanner(System.in)){

                System.out.println("Client connected");
                output.println("Connected to server");
                output.flush();

                Thread readerClientMessages = new Thread(() -> {
                    String inputMessage;
                    try {
                        do{
                            inputMessage = input.readLine();
                            System.out.println("Client: " + inputMessage);
                        }while (!inputMessage.equals("exit"));
                        output.println("Server closed socket");
                        output.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                readerClientMessages.start();

                String outputMessage;
                do{
                    outputMessage = scanner.nextLine();
                    output.println(outputMessage);
                    output.flush();
                }while(readerClientMessages.isAlive());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
