import java.io.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
  *A server stand alone class
  *used as a chat application where people connected can send messages to each other
  *clients should be connected on same network when using this class
  *best used for remote connection
  *@author Segomotso Petlele (PTLLES002)
  *@author Kundai Chatambudza (CHTKUN003)
  *@author Brighton Tandabandu (TNDBRI001)
  */ 
public class ServerBox {
    protected static DatagramSocket socket = null;
    protected static DatagramSocket socketSender = null;
    protected static boolean on = true;
    static ArrayList<String> slist = new ArrayList<String>(); 
    static ArrayList<InetAddress> alist = new ArrayList<InetAddress>(); //stores addresses that connect to server
    static ArrayList<Integer> plist = new ArrayList<Integer>(); //stores ports that connect to server
    
    public static void main(String[] args) throws IOException {
        //get a datagram socket
        System.out.println("Server online..");
        socket = new DatagramSocket(4445);
        socketSender = new DatagramSocket(5555);
        DatagramPacket packet = null;
        StringTokenizer tokens = null;
        
        while (on){
            byte[] buf = new byte[1024];
            
            //recieve packet
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            
            System.out.println("Received packet");
            String received = new String(packet.getData(), 0, packet.getLength());                
            
            //Split up the message to get (r) or (m) if r than register, else, send message to everyone connected
            tokens = new StringTokenizer(received,":");
            String rm = tokens.nextToken(); //get the tag
            String name_token = tokens.nextToken();
            
            //registration and confirmation
            if (rm.equals("r")){
                     plist.add(packet.getPort());
                     alist.add(packet.getAddress());
                     
                     if (plist.isEmpty()){
                         byte[] buf2 = new byte[1024];
                         
                         String s = "joined Chat";
                         buf2 = s.getBytes(); 
                         DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length, packet.getAddress(), packet.getPort());
                         socketSender.send(packet2);
                    
                    }else{
                        int length = plist.size();
                        for( int i = 0; i <length; i++){
                            byte[] buf3 = new byte[1024];
                                                        
                             String s = name_token+"...connected to { ServerBox }...joined chat.";
                             buf3 = s.getBytes(); 
                             DatagramPacket packet3 = new DatagramPacket(buf3, buf3.length, alist.get(i), plist.get(i));
                             socketSender.send(packet3);
                            }}
                 }
            else {//Sends the message to everyone connected to the surver
                int length = plist.size();
                for( int i = 0; i <length; i++){
                    if (packet.getAddress().equals(alist.get(i))){
                        byte[] buf6 = new byte[1024];
                        String str = "{ ServerBox }..message recieved.";
                        buf6 = str.getBytes();
                        DatagramPacket packet6 = new DatagramPacket(buf6, buf6.length, alist.get(i), plist.get(i));
                        socketSender.send(packet6);
                        }
                    else{
                    DatagramPacket packet4 = new DatagramPacket(packet.getData(), packet.getLength(), alist.get(i), plist.get(i));
                    socketSender.send(packet4);
                    }}}
            }//while loop
            socketSender.close();
    }
}