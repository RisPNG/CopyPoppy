import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocker = new ServerSocket(8080);
        System.out.println("Server is Live!");
        int clientCnt = 0;
        while (true) {
            Socket clienSocket;

            try {
                Socket clientSocket = serverSocker.accept();
                System.out.println("A client has been accepted.");
                clientCnt++;
                System.out.println("Number of request server:" + clientCnt);
                ClientHandler ch = new ClientHandler(clientSocket);
                ch.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {

    Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        while (true) {
            try {
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                Scanner sc = new Scanner(System.in);
                char choice = dis.readChar();

                if (choice == 'E') {
                    dos.close();
                    dis.close();
                }
                int n;
                switch (choice) {
                    case 'A':
                        n = dis.readInt();
                        int fact = 1;
                        for (int i = 1; i <= n; i++)
                            fact *= i;
                        dos.writeInt(fact);
                        break;
                    case 'B':
                        n = dis.readInt();
                        dos.writeBoolean(n % 2 == 1);
                        break;
                    case 'C':
                        double r = dis.readDouble();
                        dos.writeDouble(Math.PI * r * r);
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            } catch (IOException e) {

            }
        }
    }
}
