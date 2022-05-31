import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public static void main(String[] args) {

        ServerSocket serverSocket1 = null;

        try {
            serverSocket1 = new ServerSocket(5432);
            System.out.println("Server is running...");

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) try {
            Socket socket1 = serverSocket1.accept();


            DataOutputStream serverOutputStream1 = new DataOutputStream(socket1.getOutputStream());
            DataInputStream serverInputStream1 = new DataInputStream(socket1.getInputStream());


            Scanner scan = new Scanner(System.in);
            serverOutputStream1.writeUTF(" Please choose option below" + "\nA. Showing the sequence of Fibonacci number" + "\nB. Calculating two integer based on lowest and highest number" + "\nC. Comparing the length of two string" + "\nD. Calculate the volume of box" + "\n Your choice: ");
            serverOutputStream1.flush();


            String clientChoice = (String) serverInputStream1.readUTF();


            if (clientChoice.equals("A")) {
                serverOutputStream1.writeUTF("\nReturning the sequence of Fibonacci number based on the number given");
                serverOutputStream1.flush();
                serverOutputStream1.writeUTF("Enter any positive number below than 30: ");
                serverOutputStream1.flush();
                //Declaration then input the number that clint enter at the declaration
                String FibonacciNum = (String) serverInputStream1.readUTF();
                int Fibonacci = Integer.parseInt(FibonacciNum); //convert string to int

                if (Fibonacci <= 30) {
                    serverOutputStream1.writeUTF("Fibonacci series up to " + Fibonacci + ": ");
                    serverOutputStream1.flush();
                    int t1 = 0, t2 = 1;

                    //Fibonacci Sequence Formula
                    while (t1 <= Fibonacci) {
                        String T1 = String.valueOf(t1);
                        serverOutputStream1.writeUTF(T1 + " "); //showing in sequence order
                        serverOutputStream1.flush();
                        int sum = t1 + t2;
                        t1 = t2;
                        t2 = sum;
                    }
                } else {
                    serverOutputStream1.writeUTF("WRONG INPUT");
                    serverOutputStream1.flush();
                    serverOutputStream1.writeUTF("\nThe fibonacci number that you request are more than 30");
                    serverOutputStream1.flush();
                }
            } //Close A option


            else if (clientChoice.equals("B")) {
                serverOutputStream1.writeUTF("\nCalculate and return the number from the smallest to the largest");
                serverOutputStream1.flush();

                serverOutputStream1.writeUTF("1. Enter first integer number: ");
                serverOutputStream1.flush();
                String number1 = (String) serverInputStream1.readUTF();
                int num1 = Integer.parseInt(number1);

                serverOutputStream1.writeUTF("2. Enter second integer: ");
                serverOutputStream1.flush();
                String number2 = (String) serverInputStream1.readUTF();
                int num2 = Integer.parseInt(number2);
                if (num1 > num2) //if first int more large than num2
                {
                    serverOutputStream1.writeUTF("\nThe largest number is : " + number1);
                    serverOutputStream1.flush();

                    int sum = num1 + num2;
                    serverOutputStream1.writeUTF("Sum for number 2 (" + number2 + ") plus number 1 (" + number1 + ") : " + sum);
                    serverOutputStream1.flush();
                    // int sum2 = 0;
                    for (int i = num2; i <= num1; ++i) {
                        // sum = sum + i;
                        sum2 += i;
                        serverOutputStream1.writeUTF(" " + i);
                        serverOutputStream1.flush();
                    }


                    serverOutputStream1.writeUTF("\nSum for all number from " + num2 + " until " + num1 + " :" + sum2);
                    serverOutputStream1.flush();
                    //
                } else //if num2 are more large than num1
                {
                    serverOutputStream1.writeUTF("\nThe largest number is " + number2);
                    serverOutputStream1.flush();

                    int sum = num1 + num2;
                    serverOutputStream1.writeUTF("\nSum for number 1 (" + number1 + ") plus number 2 (" + number2 + ") : " + sum);
                    serverOutputStream1.flush();
                    // int sum2 = 0;
                    for (int i = num2; i <= num1; ++i) {
                        // sum = sum + i;
                        sum2 += i;
                        serverOutputStream1.writeUTF(" " + i);
                        serverOutputStream1.flush();
                    }


                    serverOutputStream1.writeUTF("\n\nSum for all number from " + num1 + " until " + num2 + " :" + sum2);
                    serverOutputStream1.flush();
                    //

                }
            }//close B

            else if (clientChoice.equals("C")) {
                serverOutputStream1.writeUTF("Comparing the length of two string ");
                serverOutputStream1.flush();

                serverOutputStream1.writeUTF("1. Enter any word or sentance: ");
                serverOutputStream1.flush();
                String word1 = (String) serverInputStream1.readUTF();

                serverOutputStream1.writeUTF("2. Enter any word or sentance: ");
                serverOutputStream1.flush();
                String word2 = (String) serverInputStream1.readUTF();

                if (word1.length() < word2.length()) {
                    serverOutputStream1.writeUTF("\nThe longest string is: " + word2);
                    serverOutputStream1.flush();
                    serverOutputStream1.writeUTF("Word 2 are longer than word 1 ");
                    serverOutputStream1.flush();
                } else if (word1.length() > word2.length()) {
                    serverOutputStream1.writeUTF("\nThe longest string is: " + word1);
                    serverOutputStream1.flush();
                    serverOutputStream1.writeUTF("Word 1 are longer than word 2 ");
                    serverOutputStream1.flush();
                } else {
                    serverOutputStream1.writeUTF("\nThe length of two string is SAME");
                    serverOutputStream1.flush();
                    serverOutputStream1.writeUTF("Word 1 and word 2 is same");
                    serverOutputStream1.flush();
                }

            }//close C

            else if (clientChoice.equals("D")) {
                serverOutputStream1.writeUTF("Calculate the volume of the BOX ");
                serverOutputStream1.flush();

                //object for encapsulation
                Box_Encapsulation BE = new Box_Encapsulation();

                serverOutputStream1.writeUTF("Please enter width: ");
                serverOutputStream1.flush();
                String widthBox = (String) serverInputStream1.readUTF();
                double width = Double.parseDouble(widthBox);
                BE.setDepth(width);
                serverOutputStream1.writeUTF("Please enter length ");
                serverOutputStream1.flush();
                String lengthBox = (String) serverInputStream1.readUTF();
                double length = Double.parseDouble(lengthBox);
                BE.setLength(length);

                serverOutputStream1.writeUTF("Please enter depth: ");
                serverOutputStream1.flush();
                String depthBox = (String) serverInputStream1.readUTF();
                double depth = Double.parseDouble(depthBox);
                BE.setDepth(depth);

                //Formula
                double totalvolume = BE.TotalVolume();
                String volumeOfBox = Double.toString(totalvolume);

                //Showing output to the client
                serverOutputStream1.writeUTF("Total volume of box is: " + volumeOfBox);
                serverOutputStream1.flush();
            } else {
                serverOutputStream1.writeUTF("\nWrong choice");
                serverOutputStream1.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}