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
        try { 	
        	//sample client for testing purpouse
        	//for more developed version check 
        	// StacPaneController.onActionServer
        	// (GUI button 'operation by server')
        	String[] names= {"jeden","dwa"};
        	String[] tnames= {"jeden","dwa"};
			DataFrame df = new DataFrame(names,tnames);
            Scanner scn = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 6000); 
      
           
            
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream()); 
            System.out.println("in"); 
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); 
            System.out.println("out");
            
            // loop to connect to middleman in case he was busy
            //
            while (true)  
            { 
            	dos.writeObject("hello");
                String h =dis.readObject().toString();
                if(h =="hello") {
                	
                	//choose operation to make
                	
                	String choosenOperation = "max";
                	
                	dos.writeObject(choosenOperation);
                	dos.writeObject(df);
                	break;
                }
            } 
                String r =dis.readObject().toString();
                System.out.println(r);
                DataFrame outputFrame = (DataFrame) dis.readObject();
                outputFrame.print(); 
           
              
            // closing resources 
            scn.close(); 
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 