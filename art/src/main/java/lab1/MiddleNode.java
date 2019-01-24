package lab1;



import lab1.DataFrame;

import lab1.Column;

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

public class MiddleNode implements Runnable {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inStream = null;
    private Column colToCount;
    private DataFrame dfRet,dfToCalc;
    private ArrayList<ObjectOutputStream> outs;
    private ArrayList<ObjectInputStream> ins;
    private String operation,errorMessage;

    public MiddleNode(ArrayList<ObjectInputStream> nodesIn, ArrayList<ObjectOutputStream> out) {
        ins=nodesIn;
        outs = out;

     
    }

    @Override
    public void run() {
        //szukanie wolnego node'a
    	/*
    	 * ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"));
                //outputStream.writeObject(Integer.valueOf(dataframe));
             
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"));
                Integer number = (Integer) inputStream.readObject();
                System.out.println(number);
                number = (Integer) inputStream.readObject();
                System.out.println(number);
                */
        String inputLine;
        boolean foundWorker = false;
        boolean clientRequest=false;
        int i=0;
        int j=0;
        long start;
        Column[] col = new Column[1];
        col[0]=colToCount;
        DataFrame outputFrame = new DataFrame(col);
        //czekanie na zadanie od klienta
        //i - numer portu serwera klienta
while(true) {
        while(!clientRequest) {
        	try {
				if(ins.get(i).readObject().toString()=="hello"){
					outs.get(i).writeObject("hello");
					operation = ins.get(i).readObject().toString();
					dfToCalc=(DataFrame) ins.get(i).readObject();
					clientRequest=true;
				}
				else {
				i++;
				if(i>=ins.size()){
				    i=0;
				}
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //szukanie wolnego serwera obliczeniowego
        //j - numer portu serwera obliczeniowego
        while (!foundWorker) {
            try {
				outs.get(j).writeObject("hi");
			
            start = System.nanoTime();
               if(ins.get(j).readObject().toString()=="hi"){
            	   outs.get(j).writeObject(operation);
            	   outs.get(j).writeObject(dfToCalc);
                        foundWorker=true;    
                        String feedback =ins.get(j).readObject().toString();
                        if(feedback=="done"){
                        	dfRet=(DataFrame) ins.get(j).readObject();
                        	outs.get(i).writeObject("finished");
                        	outs.get(i).writeObject(dfRet);
                        	clientRequest=false;
                        }
                        if(feedback=="error"){
                        	errorMessage=ins.get(i).readObject().toString();
                        	outs.get(i).writeObject("error");
                        	outs.get(i).writeObject(errorMessage);
                        	clientRequest=false;
                        }
                    }
	             //przerwanie jeœli trwa wiêcej ni¿ 10 milisekund
	               if((System.nanoTime()-start)/1000000>=10){
	                   break;
	               }
	               j++;
	               if(j>=outs.size()){
	                   j=0;
	               }
                
                
            
        } 
        catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        } 
		}
    }
}
