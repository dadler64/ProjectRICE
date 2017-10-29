package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        int portNumber = 6625;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream())
        ) {

            String inputLine, outputLine;

            // Initiate conversation with client
            Protocol protocol = new Protocol();
            outputLine = protocol.processInput(null);
            out.writeUTF(outputLine);

            while (true) {
                inputLine = in.readUTF();
                System.out.println("ChatClient: " + inputLine);

                outputLine = protocol.processInput(inputLine);
                if (outputLine.equalsIgnoreCase("Ok. Have fun!")){
                    out.writeUTF(outputLine + "\nend.");
                    System.out.println("The session ended.");
                    serverSocket.close();
                    break;
                } else {
                    System.out.println(outputLine);
                    out.writeUTF(outputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}