package server;

import java.io.*; 
import java.net.*;
import java.util.List;
import java.util.Scanner;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter; 
import lab1.DataFrame;
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        try
        { 	String[] names= {"jeden","dwa"};
        	String[] tnames= {"jeden","dwa"};
			DataFrame df = new DataFrame(names,tnames);
            Scanner scn = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 6000); 
      
            // obtaining input and out streams 
            
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream()); 
            System.out.println("in"); 
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); 
            System.out.println("out");
            // the following loop performs the exchange of 
            // information between client and client handler 
            //dos.writeObject("min"); 
            //dos.writeObject(df); 
            while (true)  
            { 
                System.out.println(dis.readUTF()); 
                String tosend = scn.nextLine(); 
                
                  
                // If client sends exit,close this connection  
                // and then break from the while loop 
                if(tosend.equals("Exit")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // printing date or time as requested by client 
                String received = dis.readUTF(); 
                System.out.println(received); 
            } 
              
            // closing resources 
            scn.close(); 
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 