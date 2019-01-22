package przycisk_plik.controller;
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
import lab1.Integer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;




public class StackPaneController {
	


    @FXML
    private Button button2; //wczytywanie df  z pliku
    @FXML
    private Button db; // przycisk do bazdanych
    
    @FXML
    private Button button3; // przycisk do porównania
    
    @FXML
    private Button viewbutton; // przycisk do porównania
    
    @FXML
    private Button statsbutton; // przycisk do porównania

    @FXML
    private Label stats;//czasy wykonania

    @FXML
    private Button button; //rysowanie wykresu

    @FXML
    private LineChart lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label stats1;//output wyj¹tków
    
    @FXML
    private Label stats2;//statystki datafram'a

    @FXML
    private TextArea textarea;//statystki datafram'a
    @FXML
    private ChoiceBox choiceX;

    @FXML
    private ChoiceBox choiceY;
    
    public static String nazwapliku;
    DataFrame plik2;
    String[] nazwy;
    String DataFrameToTextArea="";
    String path="";
    LineChart.Series<?,?> series = new XYChart.Series();
    int xaxis,yaxis;
    Value[] xl;
    Value[] yl;
    

	public StackPaneController() {
		
		
	}
	
	
	@FXML
	void onActionview(){
		
	    	//drukuje datafram'a
			textarea.clear();
			DataFrameToTextArea="";
	    	for(int i=0;i<nazwy.length;i++) {
	    	DataFrameToTextArea +=nazwy[i]+" 		  ";
	    	}
	    	DataFrameToTextArea +="\n";
	        for (int i=0; i<plik2.size(); i++){
	            for (Column col:plik2.df){
	            	DataFrameToTextArea+=col.list.get(i)+"   ";
	            }
	            DataFrameToTextArea+="\n";
	        }
	        textarea.setText(DataFrameToTextArea);
	    }
	    
		
		
	@FXML
	void onActionStats(){
		String[] stat;
		try {
			stat = plik2.dfStats(path);
		
        String stat2="data frame stats:\n \n 		";
        for(String s:nazwy) {
        	stat2+=s+"			";
        }
        
        stat2+=" \n"+"max:   "+stat[0]+"\n"+"min:   "+stat[1]+"\n"+"mean: "+stat[2]+"\n"+"var:   "+stat[3]+"\n"+"sum:   "+stat[4]+"\n"+"std: 	"+stat[5];
        
        stats2.setText(stat2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void onActonCompare(){
		int group= 0;
		
		
	    long startTime1 = System.nanoTime();
	    //DataFrame plik1 = new DataFrame(path);
	    ArrayList<DataFrame> gb1=plik2.groupby(group);
	    for(DataFrame df:gb1) {
	    df.max();
	    df.min();
	    }
	    long time1 = (System.nanoTime()-startTime1)/ 1000000;
	    
	    
	    System.out.println("ju¿ przed db");

	    long startTime2 = System.nanoTime();
	    DB databaseframe= new DB(plik2);
	    //databaseframe.groupby("id");
	   // databaseframe.max();
	   // databaseframe.min();
	   
    	long time2 = (System.nanoTime()-startTime2)/ 1000000;
	    
System.out.println("ju¿ po db");
    	long startTime3 = System.nanoTime();
    	ArrayList<DataFrame> gb3=plik2.groupbyThread(group);
	    for(DataFrame df31:gb3) {
	    	df31.maxthrd();
	    	df31.minthrd();
	    	
	    }
    	long time3 = (System.nanoTime()-startTime3) / 1000000;
	    
        stats.setText("wyniki: \n normalny df:  "+time1+"\n"+"database df:"	 +time2+"\n"+"thread df:	"+time3	);
        String path2=path.replace(".csv", "_wyniki.csv");
	       
		
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path2));
		
        writer.write("basic df,database df,thread df"+"\n");
        writer.write(time1+","+time2+","+time3);
        writer.close();
        stats1.setText("results saved to "+path2.toString());
		} catch (IOException e) {
					
					e.printStackTrace();
				}
	}
	
    @FXML
    void onActionDraw(ActionEvent event) {
    	//rysuje wykres z wybranych kolumn
		stats1.setText("");
	    lineChart.getData().clear();
	    series.getData().clear();
	    	try {
	    		
	    	ArrayList<String> nazwy1=new ArrayList<>(Arrays.asList(nazwy));
	    	xaxis =nazwy1.indexOf( choiceX.getValue());

	    	yaxis =nazwy1.indexOf( choiceY.getValue());  
	    	if(plik2.get(xaxis).getVType() instanceof SValue !=true && plik2.get(yaxis).getVType() instanceof SValue !=true ) {
	    	xl= new Value[plik2.size()];
	        yl= new Value[plik2.size()];
	        for(int i =0;i<plik2.get(xaxis).list.size();i++) {
	      
	        	xl[i]=(plik2.df.get(xaxis).list.get(i));
	        
	        		        	
	        }
	        for(int i =0;i<plik2.get(yaxis).list.size();i++) {
			      
	        	yl[i]=(plik2.df.get(yaxis).list.get(i));
	        	
	        		        	
	        }
	        for(int i=0;i<xl.length;i++) {
	        series.getData().add(new XYChart.Data(xl[i].GetValue(), yl[i].GetValue()));
	        
	        }
	      
	        lineChart.getData().addAll(series);
    	}
	    	else {
	    		stats1.setText("invalid column data format");}
	    	
	    }
    	catch(Exception e) {
    		errormessage(e);
    		stats1.setText(e.getMessage());
    	
	    }
    }
    @FXML
    public void onActionDB() {
    	//tworzy bazodanowy dataframe z wczytanego pliku
    	stats1.setText("");
    	try {
    	DB databaseframe= new DB(plik2);
    	stats1.setText("database:	"+databaseframe.toString()+"	name:	"+databaseframe.dbname);
    	DataFrame df = databaseframe.getDataFrame();
    	
    	System.out.print("max \n");
    	databaseframe.max().print();
    	System.out.print("min \n");
    	databaseframe.min().print();
    	
    	}
    	catch(Exception e) {
    		errormessage(e);
    	}
    	
    }
   
	
    ///wypisywanie wyj¹tków w gui
    public void errormessage(Exception e) {
    	stats1.setText(e.toString());
    	e.printStackTrace();
    }
    
    
    
    
    
	@FXML
	public void onActonButton2() {
		//wczytuje datafram'a z pliku .csv liczy statystyki i porównuje czasy wykonania max i min ró¿nymi metodami i zapisuje do pliku .csv o nazwie 
		// równe nazwie pliku wejœciowego + "_wyniki"
		stats1.setText("");
		String max = " ",min= " ",mean= " ",var= " ",sum= " ",std =" ";
		FileChooser fc = new FileChooser();
		try {
			choiceX.getItems().clear();
			choiceY.getItems().clear();
        	
		fc.getExtensionFilters().add(new ExtensionFilter("csv Files", "*.csv"));
		List<File> f = fc.showOpenMultipleDialog(null);
		for(File file: f) {
			
			
    		path = file.getAbsolutePath();
    		
        	BufferedReader br = new BufferedReader(new FileReader(path)); 
        	System.out.println(path);
        	
        
        	
        	nazwapliku=file.getName().replaceAll(".csv", "");
        	plik2 = new DataFrame(path);
        	
        	String firstLine = br.readLine();
        	nazwy = firstLine.split(",");
        	//print();
        	
        	choiceX.getItems().addAll(nazwy);
			choiceY.getItems().addAll(nazwy);
        	
			 	
		        
		        
		        
		        //stats2.setText("data frame stats:\n \n"+"max:  "+max+"\n"+"min:  "+min+"\n"+"mean: "+mean+"\n"+"var:  "+var+"\n"+"sum:  "+sum+"\n"+"std:	"+std);
		        lineChart.getData().clear();
		    	series.getData().clear();
		        br.close();
		        
		        //DataFrame plik = new DataFrame(file.getAbsolutePath(),nazwy,types);
		        
		        
		        
		        
		}
		} catch (NullPointerException e) {
			stats1.setText("failed opening or choosing file");
			e.printStackTrace();
		}catch (Exception e) {
			errormessage(e);
		}
        
		
		
	}

	
}	
	
	
