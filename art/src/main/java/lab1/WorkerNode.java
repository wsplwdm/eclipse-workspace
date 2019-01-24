package lab1;


import lab1.DataFrame;
import lab1.Value;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerNode {
   private Socket thisSocket =null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private DataFrame dfDone;

    public WorkerNode(String name,int port){
        try {
            thisSocket=new Socket(name,port);//tworzy port
           ///in =new BufferedReader(new InputStreamReader(thisSocket.getInputStream()));
           ////out=new PrintWriter(thisSocket.getOutputStream(), true);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"));
                //outputStream.writeObject(Integer.valueOf(dataframe));
             
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"));
                Integer number = (Integer) inputStream.readObject();
                System.out.println(number);
                number = (Integer) inputStream.readObject();
                System.out.println(number);
            


                //outputStream.writeObject("I am a client node that calculates");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void listening() throws IOException {
        String inputline;
        boolean imDone = false;
       
        while(!imDone){
        	try {
				inputline=inputStream.readObject().toString();
			
        	if(inputline=="hi"){
                
                	outputStream.writeObject("hi");
                    
                    imDone=true;
               
        	}
        	
            
            
                String operationType=inputStream.readObject().toString();
                DataFrame dataFrameToCalculate =  (DataFrame) inputStream.readObject();
                //GsonBuilder gsonBilder = new GsonBuilder();
                //gsonBilder.registerTypeAdapter(Value.class, new InterfaceAdapter<Value>());
                //gsonBilder.registerTypeAdapter(Type.class, new InterfaceAdapter<Type>());
                //Gson gson = gsonBilder.create();
                //System.out.println("licze");
                //messageToDo=gson.fromJson(inputline, Message.class);
                //dfDone = new DataFrame();
                if(operationType=="max"){
                    dfDone = dataFrameToCalculate.maxthrd();
                }
                else if(operationType=="min"){
                    dfDone = dataFrameToCalculate.minthrd();

                }
                else if(operationType=="var"){
                    dfDone = dataFrameToCalculate.varthrd();

                }
                else if(operationType=="std"){
                    dfDone = dataFrameToCalculate.stdthrd();

                }
                else if(operationType=="sum"){
                    dfDone = dataFrameToCalculate.sumthrd();

                }
                else if(operationType=="mean"){
                    dfDone = dataFrameToCalculate.meanthrd();

                }
                outputStream.writeObject("done");
                outputStream.writeObject(dfDone);
                closeSocket();
        }catch(Exception e) {
        	 outputStream.writeObject("error");
             outputStream.writeObject(e.getMessage());
			e.printStackTrace();
		}
        }
        } 
          

    
        


    

    public Socket getThisSocket() {
        return thisSocket;
    }

    public ObjectOutputStream getOut() {
        return outputStream;
    }

    public ObjectInputStream getIn() {
        return inputStream;
    }
    public void closeSocket() throws IOException {
        thisSocket.close();
    }

}
