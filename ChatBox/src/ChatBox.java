import java.io.*;
import java.net.*;
import java.util.*;

/**
  *A Chat application which uses the Receiver Thread class
  *used as a chat application where people connected can send messages to each other
  *Use this class to connect to a ServerBox server 
  *best used for remote connection
  *@author Segomotso Petlele (PTLLES002)
  *@author Kundai Chatambudza (CHTKUN003)
  *@author Brighton Tandabandu (TNDBRI001)
  */ 
public class ChatBox { 
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        String name = "user";
        String input = null;
        String server =null;
        System.out.println("################################{ CHATBOX APP }##########################");
        
        System.out.print("Enter your name: ");
        name = keyboard.nextLine();
        
        System.out.print("Enter server Ip address: ");
        server = keyboard.nextLine();
        
        
        System.out.print("Enter server port number(4445): ");
        int port = keyboard.nextInt();
        
        RecieverThread recieverThread = new RecieverThread(server, port, name);
        recieverThread.start();
                
        input = keyboard.nextLine();
        
        while(true){
            switch(input){
                case "m" :    
                    System.out.print("Enter message, press enter to send(no colons) :");
                    String message = keyboard.nextLine();
 
                    //combine the message parts
                    String combined_message = "m:"+"["+name+"]: "+message;
                    
                    // get a datagram socket
                    DatagramSocket socketC = new DatagramSocket();
 
                    // send request
                    byte[] buf = new byte[1024];
                    buf = combined_message.getBytes();
                    InetAddress address = InetAddress.getByName(server);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                    socketC.send(packet);
     
                    socketC.close();
                    break;

                case "e":
                    String bye =  "m:"+"["+name+"]: has logged out";
                    // get a datagram socket
                    DatagramSocket socketD = new DatagramSocket();
                    
                    System.out.println("You logged out");
                    byte[] buf2 = new byte[1024];
                    buf2 = bye.getBytes();
                    InetAddress address2 = InetAddress.getByName(server);
                    DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length, address2, 4445);
                    socketD.send(packet2);

                    socketD.close();
                    System.exit(0);
                    break;
                
                default :
                    break;
                }
                System.out.println("");
                input = keyboard.nextLine();
        
        }                    
    }
    
}