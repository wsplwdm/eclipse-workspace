package server;
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*;
import lab1.DataFrame;
class ClientHandler extends java.lang.Thread  
{ 
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    private ObjectInputStream clientIn; 
    private ObjectOutputStream clientOut;
    private Socket s;
    private int[] ports;
    
      
  
    // Constructor 
    public ClientHandler(Socket s,int[] ports, ObjectInputStream clientIn, ObjectOutputStream clientOut)  
    { 
        this.s = s; 
        this.clientIn = clientIn; 
        this.clientOut = clientOut; 
        this.ports = ports;
    } 
  
	@Override
    public void run()  
    {  	System.out.println("i'm client handler");
    	
    	long start;
    	boolean foundWorker = false;
    	String errorMessage;
    	ArrayList<DataFrame> groupbyRet;
    	DataFrame dfRet;
    	
		try {
			
			String operation = clientIn.readObject().toString();
			// in groupby case the first message contains
			// groupby#name
			// where name is name of column to group by
			String[] operationType = operation.split("#");
			DataFrame dfToCalc = (DataFrame) clientIn.readObject();
		
    	int i=0;
    	 while (!foundWorker) {
    		 
    		 	InetAddress ip = InetAddress.getByName("localhost");
    	        Socket sw = new Socket(ip, ports[i]);
    	        ObjectInputStream workerIn = new ObjectInputStream(sw.getInputStream()); 
    	        ObjectOutputStream workerOut = new ObjectOutputStream(sw.getOutputStream());
    	        
    	        
    	        workerOut.writeObject("hi");
 			
             start = System.nanoTime();
                if(workerIn.readObject().toString()=="hi"){
                   foundWorker=true;  
             	   workerOut.writeObject(operation);
             	   workerOut.writeObject(dfToCalc);
                           
                         String feedback =workerIn.readObject().toString();
                         if(feedback=="done"){
                        	 if(operationType[0]=="groupby") {
                         	groupbyRet=(ArrayList<DataFrame>) workerIn.readObject();
                         	clientOut.writeObject("finished");
                         	clientOut.writeObject(groupbyRet);
                         	System.out.println("Client " + this.s + " sends exit..."); 
                            System.out.println("Closing this connection."); 
                            this.s.close(); 
                            sw.close();
                            System.out.println("Connection closed"); 
                         	break;
                        	 }
                        	 else {
                        		 dfRet=(DataFrame) workerIn.readObject();
                              	clientOut.writeObject("finished");
                              	clientOut.writeObject(dfRet);
                              	System.out.println("Client " + this.s + " sends exit..."); 
                                System.out.println("Closing this connection."); 
                                this.s.close(); 
                                sw.close();
                                System.out.println("Connection closed"); 
                                
                              	break;
                        		 
                        	 }
                         }
                         if(feedback=="error"){
                         	errorMessage=workerIn.readObject().toString();
                         	clientOut.writeObject("error");
                         	clientOut.writeObject(errorMessage);
                         	System.out.println("Client " + this.s + " sends exit..."); 
                            System.out.println("Closing this connection."); 
                            this.s.close(); 
                            sw.close();
                            System.out.println("Connection closed"); 
                         	break;
                         }
                     }
 	             //timeout 10 ms
 	               if((System.nanoTime()-start)/1000000>=10){
 	            	   
 	                   break;
 	               }
 	               i++;
 	               if(i>ports.length) {
 	            	   i=0;
 	               }
 	            
                 
                 
    	 	} 
         } 
         catch (IOException | ClassNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
         
        
        
        try
        { 
            
            this.clientIn.close();
            this.clientOut.close(); 
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 