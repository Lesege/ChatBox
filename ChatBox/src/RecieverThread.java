import java.io.*;
import java.net.*;
import java.util.*;

/**
  *Connects to ServerBox and than recieves packets continuesly
  *helper class for ChatBox
  *best used for remote connection
  *@author Segomotso Petlele (PTLLES002)
  *@author Kundai Chatambudza (CHTKUN003)
  *@author Brighton Tandabandu (TNDBRI001)
  */
public class RecieverThread extends Thread{
    int port = 0;
    String address = null;
    String name =null;
    private boolean on = true;
    DatagramSocket socket = new DatagramSocket(9999);
    boolean b = true;
    
    RecieverThread(String address, int port, String name) throws IOException{
        super();
        this.address = address;
        this.port = port;
        this.name = name;  
        }
    
    /**
      *used to recieve packets from the ServerBox class continuesly
      */
    public void run(){
        try{
        byte buf[] = new byte[1024];
        InetAddress ad = InetAddress.getByName(address);
        String r = "r:"+this.name+":";
        buf = r.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ad, this.port);
        socket.send(packet);
        
        while (on){
                byte[] buf2 = new byte[1024];
            //recieve packet
                packet = new DatagramPacket(buf2, buf2.length);
                if( b){
                    b = false;
                    //socket sends error message to user if connection to server takes longer than 3 seconds
                    try{
                        socket.setSoTimeout(3000);
                        System.out.println("Waiting for ServerBox...");
                        socket.receive(packet);
                        socket.setSoTimeout(0);
                        
                        //unpack message
                        String received = new String(packet.getData(), 0, packet.getLength());
            
                        System.out.println("You recieved a message: ");
                        System.out.println("***********************"); 
                        System.out.println(received);
                        System.out.println("***********************");

                        System.out.println("");
                        System.out.print("type [m] to send a message or [e] to exit :");
                        }catch (SocketTimeoutException e){
                           
                            System.out.println("Connection time out.. Press e to end program and try connectin to server again");
                            }
            }else{
                socket.receive(packet);
              
                //unpack message
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("You recieved a message: ");
                System.out.println("***********************"); 
                System.out.println(received);
                System.out.println("***********************");
            
                System.out.println("");
                System.out.print("type [m] to send a message or [e] to exit :");
           
                }}}catch (IOException e){
                    e.printStackTrace();
                    }
        
    }
 }       
        