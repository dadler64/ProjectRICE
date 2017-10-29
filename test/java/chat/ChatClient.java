package chat;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    public static void main(String[] args) {

        String hostName = "127.0.0.1";
        int portNumber = 6625;

        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
        ) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while (true) {
                fromServer = in.readUTF();
                System.out.println("ChatServer: " + fromServer);
                if (fromServer.equals("Ok. Have fun!")) {
                    System.out.println("end");
                    break;
                }

                fromUser = userInput.readLine();
                if (fromUser != null) {
                    out.writeUTF(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}