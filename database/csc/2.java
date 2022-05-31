import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server_Side {

    public static void main(String[] args) {

        ServerSocket s = null;

        //Register your service on port 5432
        try {
            s = new ServerSocket(5432);
            System.out.println("Server is running....");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something wrong with your socket connecction");
        }

        while (true) {
            try {

                //Wait here and listen for a connection
                Socket s1 = s.accept();

                //Get output stream associated
                //with the socket
                DataOutputStream slout = new DataOutputStream(s1.getOutputStream());
                DataInputStream dis1 = new DataInputStream(s1.getInputStream());

                System.out.println("In/Loop");
                slout.writeUTF("Please choose : " + "\n A - Sequence from 0 of all the Fibonacci numbers up  to entered number" + "\n B - Calculate and return the sum of all the numbers  from the smallest to the largest number" + "\n C - Compare the length of two strings and return the  longest string" + "\n D - Calculate the volume of a box");


                slout.writeUTF("Choosen option: ");
                //Read iput from client
                String resp = (String) dis1.readUTF();
                char respond = resp.charAt(0);

                //A
                if (respond == 'A' || respond == 'a') {
                    slout.writeUTF("Please enter a positive integer number");
                    slout.writeUTF("Number: ");
                    String number1 = (String) dis1.readUTF();
                    int num1 = Integer.parseInt(number1);
                    System.out.print("Number: " + num1);

                    int fn = 0;
                    int firstTerm = 0;
                    int secondTerm = 1;

                    int i = 1;
                    slout.writeUTF("Fibonacci number: ");
                    while (i == 1) {
                        slout.writeUTF(firstTerm + " ");
                        //compute next term
                        int nextTerm = firstTerm + secondTerm;
                        firstTerm = secondTerm;
                        secondTerm = nextTerm;

                        if (firstTerm > num1) {
                            i = 0;
                        }
                    }
                } //B
                else if (respond == 'B' || respond == 'b') {
                    slout.writeUTF("Enter 2 number");

                    slout.writeUTF("Enter first number: ");
                    String number = (String) dis1.readUTF();
                    int num = Integer.parseInt(number);
                    System.out.println("Number 1: " + num);

                    slout.writeUTF("Enter second number: ");
                    String number2 = (String) dis1.readUTF();
                    int num2 = Integer.parseInt(number2);
                    System.out.println("Number 2: " + num2);

                    int smallest, biggest, sum = 0;
                    if (num <= num2) {
                        smallest = num;
                        biggest = num2;


                    } else {
                        smallest = num2;
                        biggest = num;
                    }

                    for (int i = smallest; i <= biggest; i++) {
                        sum = sum + i;
                    }
                    slout.writeUTF("The answer is: " + sum);
                } //C
                else if (respond == 'C' || respond == 'c') {
                    slout.writeUTF("Enter 2 string ");

                    slout.writeUTF("Enter string 1: ");
                    String str1 = (String) dis1.readUTF();
                    System.out.println("String 1: " + str1);

                    slout.writeUTF("Enter string 2: ");
                    String str2 = (String) dis1.readUTF();
                    System.out.println("String 2: " + str2);

                    int l1 = str1.length();
                    int l2 = str2.length();

                    if (l1 > l2) {
                        slout.writeUTF("The longest string is: " + str1);
                    } else {
                        slout.writeUTF("The longest string is: " + str2);
                    }
                } //D
                else if (respond == 'D' || respond == 'd') {
                    Box b = new Box();

                    slout.writeUTF("Width: ");
                    String w = (String) dis1.readUTF();
                    double width = Double.parseDouble(w);

                    slout.writeUTF("Depth: ");
                    String d = (String) dis1.readUTF();
                    double depth = Double.parseDouble(d);

                    slout.writeUTF("Length: ");
                    String l = (String) dis1.readUTF();
                    double length = Double.parseDouble(l);

                    b.getWidth(width);
                    b.getDepth(depth);
                    b.getLength(length);


                    slout.writeUTF("Volume: " + b.calcVol());
                }

                dis1.close();
                slout.close();
                s1.close();

            } catch (IOException e) {
                e.printStackTrace();
            } //END of try-catch
        }

    }

}