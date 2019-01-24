package server;



import lab1.DataFrame;

import lab1.Column;
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MiddleNode{

   
public static void main(String[] args) throws IOException  
        { 	
		System.out.println("i'm middlenode");
            // server is listening on port 6000 
            ServerSocket ss = new ServerSocket(6000); 
            int[]ports= {6001,6002,6003};
            // running infinite loop for getting 
            // client request 
            while (true)  
            { 
                Socket s = null; 
                try 
                { 
                    // socket object to receive incoming client requests 
                    s = ss.accept(); 
                      
                    System.out.println("A new client is connected : " + s); 
                      
                    // obtaining input and out streams 
                    ObjectInputStream dis = new ObjectInputStream(s.getInputStream()); 
                    System.out.println("A new client is connected : " + s); 
                    ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); 
                      
                    System.out.println("Assigning new thread for this client"); 
      
                    // create a new thread object 
                    
                    ClientHandler t = new ClientHandler(s,ports, dis, dos); 
      
                    // Invoking the start() method 
                    t.start(); 
                      
                } 
                catch (Exception e){ 
                    //s.close(); 
                    e.printStackTrace(); 
                } 
            } 
            
        } 
     
}