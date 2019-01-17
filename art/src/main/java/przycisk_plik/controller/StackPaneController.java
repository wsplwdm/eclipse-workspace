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
import lab1.*;
import lab1.Integer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;




public class StackPaneController {
	


    @FXML
    private Button button2;
    @FXML
    private Button db;

    @FXML
    private Label stats;//czasy wykonania

    @FXML
    private Button button;

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
    private ChoiceBox choiceX;

    @FXML
    private ChoiceBox choiceY;
    
    public static String nazwapliku;
    DataFrame plik2;
    String[] nazwy;
    LineChart.Series<?,?> series = new XYChart.Series();
    int xaxis,yaxis;
    Value[] xl;
    Value[] yl;
    

	public StackPaneController() {
		
		
	}
	
	
	
    @FXML
    void onActionDraw(ActionEvent event) {
		stats1.setText("");
	    lineChart.getData().clear();
	    series.getData().clear();
	    	try {
	    		
	    	ArrayList<String> nazwy1=new ArrayList<>(Arrays.asList(nazwy));
	    	xaxis =nazwy1.indexOf( choiceX.getValue());

	    	yaxis =nazwy1.indexOf( choiceY.getValue());  
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
    	catch(Exception e) {
    		errormessage(e);
    	}
    }
    @FXML
    public void onActionDB() {
    	stats1.setText("");
    	try {
    	DB databaseframe= new DB(plik2);
    	stats1.setText("database:	"+databaseframe.toString()+"	name:	"+databaseframe.dbname);
    	DataFrame df = databaseframe.getDataFrame();
    	df.print();
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
		stats1.setText("");
		String max = " ",min= " ",mean= " ",var= " ",sum= " ",std =" ";
		FileChooser fc = new FileChooser();
		try {
			choiceX.getItems().clear();
			choiceY.getItems().clear();
        	
		fc.getExtensionFilters().add(new ExtensionFilter("csv Files", "*.csv"));
		List<File> f = fc.showOpenMultipleDialog(null);
		for(File file: f) {
			
    	boolean header = true;
    		String path = file.getAbsolutePath();
        	BufferedReader br = new BufferedReader(new FileReader(path)); 
        	System.out.println(path);
        	
        	String firstLine = br.readLine();
        	nazwy = firstLine.split(",");
        	
        	nazwapliku=file.getName().replaceAll(".csv", "");
        	
        	choiceX.getItems().addAll(nazwy);
			choiceY.getItems().addAll(nazwy);
        	
        	int colnumber = nazwy.length;
        	String secondLine = br.readLine();
        	String[] typ = secondLine.split(",");
        	Value[] types = new Value[colnumber];
        	
        	for(int i =0;i<colnumber;i++) {
        		try {
        			int result = java.lang.Integer.parseInt(typ[i]);
        			Integer resulttype = new lab1.Integer(result);	
        			types[i]=resulttype;
        			}
        		catch(NumberFormatException ei){
        			try {
        				double result = java.lang.Double.parseDouble(typ[i]);
        				lab1.Double resulttype = new lab1.Double(result);
        				types[i]=resulttype;
        				}
        			catch(NumberFormatException ed) {
        				try {
            				float result = java.lang.Float.parseFloat(typ[i]);
            				lab1.Float resulttype = new lab1.Float(result);
            				types[i]=resulttype;            				}
            			catch(NumberFormatException ef) {
            				String result = typ[i];
            				lab1.SValue resulttype = new lab1.SValue(result);
            				types[i]=resulttype;
            				}
        			}
        		}
        		
        	}
        	
        	
			
			plik2 = new DataFrame(file.getAbsolutePath(),nazwy,types);
			
			
			
			 int group= 0;
			 String sgroup=nazwy[0];
				
			   /* for(int i =0; i<colnumber;i++) {
			    	
			    	DataFrame plik = new DataFrame(file.getAbsolutePath(),nazwy,types);
			    	
			    	//max += plik.max().df.get(i).list.get(0).toString() +"		";
			    	//min += plik.min().df.get(i).list.get(0).toString() +"		";
			    	//mean += plik.mean().df.get(i).list.get(0).toString() +" 	 ";
			    	//var += plik.var().df.get(i).list.get(0).toString() +"		";
			    	//sum += plik.sum().df.get(i).list.get(0).toString() +"		";
			    	//std += plik.std().df.get(i).list.get(0).toString() +"		";
			    }*/
			    long startTime1 = System.nanoTime();
			    DataFrame plik1 = new DataFrame(file.getAbsolutePath(),nazwy,types);
			    ArrayList<DataFrame> gb1=plik1.groupby(group);
			    for(DataFrame df:gb1) {
			    df.max();
			    df.min();
			    }
			    
			    long time1 = (System.nanoTime()-startTime1)/ 1000000;
			    
			    
			    long startTime2 = System.nanoTime();
			    DB databaseframe= new DB(plik2);
			    //ArrayList<DataFrame> gb2=databaseframe.groupby(sgroup);
			    
			    databaseframe.max();
			    databaseframe.min();
			    
		    	//stats1.setText("database:	"+databaseframe.toString()+"	name:	"+databaseframe.dbname);
		    	
		    	long time2 = (System.nanoTime()-startTime2)/ 1000000;
			    
		    	
		    	long startTime3 = System.nanoTime();
		    	ArrayList<DataFrame> gb3=plik1.groupbyThread(group);
		    	
			    for(DataFrame df31:gb3) {
			    	df31.maxthrd().print();
			    	df31.minthrd();
			    }
		   
		    	long time3 = (System.nanoTime()-startTime3) / 1000000;
			    
		        stats.setText("wyniki: \n normalny df:  "+time1+"\n"+"database df:"	 +time2+"\n"+"thread df:	"+time3	);
		        
		        String path2=file.getAbsolutePath().toString().replace(".csv", "wyniki.csv");
		       
		        		
		        BufferedWriter writer = new BufferedWriter(new FileWriter(path2));
		        writer.write("normalny df,database df,thread df"+"\n");
		        writer.write(time1+","+time2+","+time3);
		        writer.close();
		        
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