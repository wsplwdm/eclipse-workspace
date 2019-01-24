package server;


import lab1.DataFrame;
import valueTypes.Value;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerNode  {
   


public static void main(String[] args) throws IOException  
    { 	
		System.out.println("i'm workernode");
        // server is listening on port 5000 
        ServerSocket ss = new ServerSocket(6001); 
        String inputline="";
        boolean ready = true;
        Socket s = null; 
        try {
            
        while(true){
        	 s = ss.accept(); 
        	 ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream()); 
        	 ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
        	if(ready==true)
        	
				inputline=inputStream.readObject().toString();
			
        	if(inputline=="hi"){
                
                	outputStream.writeObject("hi");
                    
                    ready=false;
               
        	}
        		ArrayList<DataFrame> groupbyRet=new ArrayList<DataFrame>();
            	DataFrame dfDone;
            	String operation = inputStream.readObject().toString();
                String[] operationType=operation.split("#");
                DataFrame dataFrameToCalculate =  (DataFrame) inputStream.readObject();
                if(operationType[0]=="max"){
                    dfDone = dataFrameToCalculate.maxthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);
                }
                else if(operationType[0]=="min"){
                    dfDone = dataFrameToCalculate.minthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);

                }
                else if(operationType[0]=="var"){
                    dfDone = dataFrameToCalculate.varthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);

                }
                else if(operationType[0]=="std"){
                    dfDone = dataFrameToCalculate.stdthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);

                }
                else if(operationType[0]=="sum"){
                    dfDone = dataFrameToCalculate.sumthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);

                }
                else if(operationType[0]=="mean"){
                    dfDone = dataFrameToCalculate.meanthrd();
                    outputStream.writeObject("done");
                    outputStream.writeObject(dfDone);

                }
                else if(operationType[0]=="groupby"){
                	
                	for(int i=0;i<dataFrameToCalculate.getNumberOfCOlumns();i++) {
                		if(dataFrameToCalculate.get(i).getName()==operationType[1]) {
                	
                			groupbyRet = dataFrameToCalculate.groupbyThread(i);
                		}
                	}
                	outputStream.writeObject("done");
                    outputStream.writeObject(groupbyRet);
                }
                
                ready=true;
        } 
        }catch(Exception e) {
        	//s.close(); 
            e.printStackTrace(); 
		}
             
}
}