import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    //Socket for every client processes
    Socket c;

    Server(Socket c) {
        this.c = c;
    }

    public static void main(String[] args) {
        ServerSocket s;
        try {
            //register service on unused port
            s = new ServerSocket(5433);
            s.setReuseAddress(true);
            System.out.println("Server is running...");
            //run the listen/accept loop indefinitely
            while (true) {
                //wait and listen for connection, and create new thread for every client connected
                Socket server = s.accept();
                System.out.println("New client connected: " + s.getInetAddress().getHostAddress());
                new Thread(new Server(server)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //Get output stream associated with the socket
            DataInputStream input = new DataInputStream(c.getInputStream());
            DataOutputStream output = new DataOutputStream(c.getOutputStream());
            //send string
            output.writeUTF("A. Fibonacci Numbers\nB. Sum Smallest to Largest\nC. Compare Two Strings for Longest\nD. Calculate Box Volume\nChoose your choice: ");
            output.flush();
            String choice = input.readUTF();
            switch (choice) {
                case "A": //Return in sequence from 0 of all the Fibonacci numbers up to the number given by the client
                    output.writeUTF("Enter a number for the Fibonacci to count up to: ");
                    output.flush();
                    int numA = Integer.parseInt(input.readUTF());

                    //Fibonacci Sequence Formula
                    int n1 = 0, n2 = 1, n3 = 0;
                    output.writeUTF(n1 + ", " + n2); //Print the first 2 numbers in the Fibonacci Sequence
                    output.flush();

                    n3 = n1 + n2;
                    do {
                        output.writeUTF(", " + n3);
                        output.flush();
                        n1 = n2;
                        n2 = n3;
                        n3 = n1 + n2;
                        output.writeUTF(String.valueOf(n3));
                        output.flush();
                    } while (n3 < numA);
                    break;
                case "B": //Calculate and return the sum of all numbers from the smallest to the largest number given by the client
                    //Receive two number inputs
                    output.writeUTF("Enter first number: ");
                    output.flush();
                    int numB = Integer.parseInt(input.readUTF());
                    output.writeUTF("Enter second number: ");
                    output.flush();
                    int numB2 = Integer.parseInt(input.readUTF());

                    //Variable for smallest number (startB) and largest number (limitB)
                    int startB, limitB;

                    //Compare for smallest and largest number
                    if (numB < numB2) {
                        startB = numB;
                        limitB = numB2;
                        output.writeUTF("Smallest number is: " + numB + "\nHighest number is: " + numB2);
                        output.flush();
                    } else {
                        startB = numB2;
                        limitB = numB;
                        output.writeUTF("Smallest number is: " + numB2 + "\nHighest number is: " + numB);
                        output.flush();
                    }

                    //Calculate and output to the client the sum of all the numbers from the smallest number to the largest number with loop
                    int sum = startB;
                    while (startB < limitB) {
                        startB++;
                        sum += startB;
                    }
                    output.writeUTF("Sum of all the numbers from the smallest number to the largest number is: " + sum);
                    output.flush();
                    break;
                case "C": //Compare the length of two strings given by the client and return the longest string to the client
                    //Receive two string inputs
                    output.writeUTF("Enter first string: ");
                    output.flush();
                    String strC = input.readUTF();
                    output.writeUTF("Enter second string: ");
                    output.flush();
                    String strC2 = input.readUTF();

                    //Compare for the longest string and output to the client
                    if (strC.length() < strC2.length()) {
                        output.writeUTF("Longest string is: " + strC2);
                        output.flush();
                    } else {
                        output.writeUTF("Longest string is: " + strC);
                        output.flush();
                    }
                    break;
                case "D": //Calculate and return the volume of a box based on the Box object (encapsulate the width, length and depth in an object type Box) given by the client
                    output.writeUTF("Enter box width, length, and depth separated by space: ");
                    output.flush();
                    double widthD = Double.parseDouble(input.readUTF());
                    double lengthD = Double.parseDouble(input.readUTF());
                    double depthD = Double.parseDouble(input.readUTF());

                    ServerBox box = new ServerBox(widthD, lengthD, depthD, (widthD * lengthD * depthD)); //Call and assign value to ServerBox object

                    output.writeUTF("The volume of the box is: " + box.getVolume());
                    output.flush();
                    break;
                default:
                    break;
            }

            output.flush();
            //close connection, but not socket
            input.close();
            output.close();
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} //end