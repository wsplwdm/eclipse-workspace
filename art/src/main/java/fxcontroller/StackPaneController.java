package fxcontroller;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lab1.*;
import valueTypes.Integer;
import valueTypes.SValue;
import valueTypes.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;




public class StackPaneController {
	


    @FXML
    private Button buttonOpenFile; //wczytywanie df  z pliku
    @FXML
    private Button buttonDB; // przycisk do bazdanych
    
    @FXML
    private Button buttonCompare; // przycisk do por�wnania
    
    @FXML
    private Button buttonView; 
    
    @FXML
    private Button buttonStats; // 

    @FXML
    private Label labelStatsTime;//czasy wykonania

    @FXML
    private Button buttonDraw; //rysowanie wykresu
    @FXML
    private Button buttonServer;

    @FXML
    private LineChart lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label labelExceptions;
    
    @FXML
    private Label labelStats;

    @FXML
    private TextArea textarea;
    @FXML
    private ChoiceBox choiceX;

    @FXML
    private ChoiceBox choiceY;
    @FXML
    private ChoiceBox<String> operations;
    
    //public static String nazwapliku;
    DataFrame dataFrameFromFile;
    String[] nazwy;
    String DataFrameToTextArea="";
    String path="";
    LineChart.Series<?,?> series = new XYChart.Series();
    int xaxis,yaxis;
    Value[] xl;
    Value[] yl;
    

    
    // zapisywanie dataframa do pliku .csv
    //plik2.saveToCSV(path);
    
    
	public StackPaneController() {
	
	}
	@FXML
	void onActionServer(){
		System.out.println("sending request to server");
		try {
			InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 6000); 
            System.out.println("stackpane s");
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
            System.out.println("stack in");
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); 
            System.out.println("stack out");
           dos.writeObject("hello");
            String h =dis.readObject().toString();
            if(h =="hello") {
            	String choosenOperation = operations.getValue().toString();
            	if(choosenOperation=="groupby") {
            		choosenOperation+="#"+choiceX.getValue();
            	}
            	dos.writeObject(choosenOperation);
            	dos.writeObject(dataFrameFromFile);
            }
            String r =dis.readObject().toString();
            System.out.println(r);
            DataFrame outputFrame = (DataFrame) dis.readObject();
            outputFrame.print();
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			labelExceptions.setText("error: "+e.getMessage());
		}
	}
	
	@FXML
	void onActionview(){
		
	    	
			textarea.clear();
			DataFrameToTextArea="";
	    	for(int i=0;i<nazwy.length;i++) {
	    	DataFrameToTextArea +=nazwy[i]+" 		  ";
	    	}
	    	DataFrameToTextArea +="\n";
	        for (int i=0; i<dataFrameFromFile.size(); i++){
	            for (Column col:dataFrameFromFile.getListOfColumns()){
	            	DataFrameToTextArea+=col.listOfValues.get(i)+"   ";
	            }
	            DataFrameToTextArea+="\n";
	        }
	        textarea.setText(DataFrameToTextArea);
	    }
	    
		
		
	@FXML
	void onActionStats(){
		String[] stat;
		try {
			stat = dataFrameFromFile.statistics(path);
		
        String stat2="data frame stats:\n \n 		";
        for(String s:nazwy) {
        	stat2+=s+"			";
        }
        
        stat2+=" \n"+"max:   "+stat[0]+"\n"+"min:   "+stat[1]+"\n"+"mean: "+stat[2]+"\n"+"var:   "+stat[3]+"\n"+"sum:   "+stat[4]+"\n"+"std: 	"+stat[5];
        
        labelStats.setText(stat2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			labelExceptions.setText("error: "+e.getMessage());
		}
	}
	
	@FXML
	void onActonCompare(){
		int group= 0;
		
		
	    long startTime1 = System.nanoTime();
	    //DataFrame plik1 = new DataFrame(path);
	    ArrayList<DataFrame> gb1=dataFrameFromFile.groupby(group);
	    for(DataFrame df:gb1) {
		    df.max();
		    df.min();
		    df.mean();
		    df.sum();
		    df.var();
		    df.std();
	    }
	    long time1 = (System.nanoTime()-startTime1)/ 1000000;
	    
	
	    /*
	   long startTime2 = System.nanoTime();
	    DB databaseframe= new DB(plik2);
	    //DB databaseframe = new DB("1groupby");
	    databaseframe.groupby("id");
	    databaseframe.max();
	    databaseframe.min();
	  
    	long time2 = (System.nanoTime()-startTime2)/ 1000000;
	     */
	    String time2="---";

    	long startTime3 = System.nanoTime();
    	ArrayList<DataFrame> gb3=dataFrameFromFile.groupbyThread(group);
	    for(DataFrame df:gb3) {
	    	df.maxthrd();
	    	df.minthrd();
	    	df.meanthrd();
	    	df.sumthrd();
	    	df.varthrd();
	    	df.stdthrd();
	    	
	    }
    	long time3 = (System.nanoTime()-startTime3) / 1000000;
	    
        labelStatsTime.setText("wyniki:[ms] \n standard df:  "+time1+"\n"+"database df:"	 +time2+"\n"+"thread df:	"+time3	);
        String path2=path.replace(".csv", "_wyniki.csv");
	       
		
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path2));
		
        writer.write("basic df,database df,thread df,");
        writer.write(time1+","+time2+","+time3);
        writer.close();
        labelExceptions.setText("results saved to "+path2.toString());
		} catch (IOException e) {
					
			labelExceptions.setText("error: "+e.getMessage());
				}
		
		
		
		
		
	}
	
    @FXML
    void onActionDraw(ActionEvent event) {
    	
    	
    	//rysuje wykres z wybranych kolumn
		labelExceptions.setText("");
	    lineChart.getData().clear();
	    series.getData().clear();
	    	try {
	    		
	    	ArrayList<String> nazwy1=new ArrayList<>(Arrays.asList(nazwy));
	    	xaxis =nazwy1.indexOf( choiceX.getValue());

	    	yaxis =nazwy1.indexOf( choiceY.getValue());  
	    	if(dataFrameFromFile.get(xaxis).getVType() instanceof SValue !=true && dataFrameFromFile.get(yaxis).getVType() instanceof SValue !=true ) {
	    	xl= new Value[dataFrameFromFile.size()];
	        yl= new Value[dataFrameFromFile.size()];
	        for(int i =0;i<dataFrameFromFile.get(xaxis).listOfValues.size();i++) {
	      
	        	xl[i]=(dataFrameFromFile.getListOfColumns().get(xaxis).listOfValues.get(i));
	        
	        		        	
	        }
	        for(int i =0;i<dataFrameFromFile.get(yaxis).listOfValues.size();i++) {
			      
	        	yl[i]=(dataFrameFromFile.getListOfColumns().get(yaxis).listOfValues.get(i));
	        	
	        		        	
	        }
	        for(int i=0;i<xl.length;i++) {
	        series.getData().add(new XYChart.Data(xl[i].GetValue(), yl[i].GetValue()));
	        
	        }
	      
	        lineChart.getData().addAll(series);
    	}
	    	else {
	    		labelExceptions.setText("invalid column data format");}
	    	
	    }
    	catch(Exception e) {
    		
    		labelExceptions.setText("error: "+e.getMessage());
    	
	    }
    }
    @FXML
    public void onActionDB() {
    	//tworzy bazodanowy dataframe z wczytanego pliku
    	labelExceptions.setText("");
    	try {
    	DB databaseframe= new DB(dataFrameFromFile);
    	labelExceptions.setText("database:	"+databaseframe.toString()+"	name:	"+databaseframe.getDbname());
    	DataFrame df = databaseframe.getDataFrame();
    	
    	System.out.print("max \n");
    	databaseframe.max().print();
    	System.out.print("min \n");
    	databaseframe.min().print();
    	
    	}
    	catch(Exception e) {
    		labelExceptions.setText("error: "+e.getMessage());
    	}
    	
    }
   
	
  
    
    
    
    
    
	@FXML
	public void onActonOpenFile() {
		System.out.println("dupa");
		String[] operationList= {"max","min","mean","sum","var","std","groupby"};
		operations.getItems().addAll(operationList);
		
		//wczytuje datafram'a z pliku .csv liczy statystyki i por�wnuje czasy wykonania max i min r�nymi metodami i zapisuje do pliku .csv o nazwie 
		// r�wne nazwie pliku wej�ciowego + "_wyniki"
		labelExceptions.setText("");
		String max = " ",min= " ",mean= " ",var= " ",sum= " ",std =" ";
		
		try {
			choiceX.getItems().clear();
			choiceY.getItems().clear();
		
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("csv Files", "*.csv"));
			File file = fc.showOpenDialog(null);
		
			
			
    		path = file.getAbsolutePath();
    		
        	BufferedReader br = new BufferedReader(new FileReader(path)); 
        	System.out.println(path);
        	
        
        	
        	//nazwapliku=file.getName().replaceAll(".csv", "");
        	dataFrameFromFile = new DataFrame(path);
        	
        	String firstLine = br.readLine();
        	nazwy = firstLine.split(",");
        	//print();
        	

			choiceY.getItems().addAll(nazwy);
			choiceX.getItems().addAll(nazwy);
			 	
		        
		        
		        
		        //stats2.setText("data frame stats:\n \n"+"max:  "+max+"\n"+"min:  "+min+"\n"+"mean: "+mean+"\n"+"var:  "+var+"\n"+"sum:  "+sum+"\n"+"std:	"+std);
		        lineChart.getData().clear();
		    	series.getData().clear();
		        br.close();
		        
		        //DataFrame plik = new DataFrame(file.getAbsolutePath(),nazwy,types);
		        
		        
		        
		        
		
		} catch (NullPointerException e) {
			labelExceptions.setText("failed opening or choosing file");
			e.printStackTrace();
			
		}catch (Exception e) {
			labelExceptions.setText("error: "+e.getMessage());
		}
        
		
		
	}

	
}	
	
	