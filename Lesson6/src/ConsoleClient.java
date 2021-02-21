import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {
    public static void main(String[] args) {
        startConsoleClient();
    }

    private static void startConsoleClient() {
        try (Socket socket = new Socket("localhost",8180);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in))
        {
            Thread readerServerMessages = new Thread(() -> {
                try {
                    String inputMessage;
                    while (!socket.isClosed()){
                        inputMessage = input.readLine();
                        System.out.println("Server: "+inputMessage);
                        if (inputMessage.equals("Server closed socket"))
                            break;
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerServerMessages.start();

            String outputMessage;
            do{
                outputMessage = scanner.nextLine();
                output.println(outputMessage);
                output.flush();
            }while(!outputMessage.equals("exit"));
            readerServerMessages.join();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
