package przycisk_plik.controller;


import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;




public class StackPaneController {
	
	@FXML
    private Button button;

    @FXML
    private Button button2;

    @FXML
    private Label stats;

    @FXML
    private Button button3;

    @FXML
    private LineChart lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label stats1;

    @FXML
    private ChoiceBox choiceX;

    @FXML
    private ChoiceBox choiceY;

    	
    DataFrame plik;
    String[] nazwy;
    
    

	public StackPaneController() {
		
		
	}
	
    @FXML
    void onActionDraw(ActionEvent event) {
    	int x =plik.df.indexOf( choiceX.getValue());
    	int y =plik.df.indexOf( choiceY.getValue());    	

        lineChart.getData().clear();
        LineChart.Series<?,?> series = new XYChart.Series();
        Value[] xl = new Value[plik.size()];
        Value[] yl = new Value[plik.size()];
        for(int i =0;i<plik.size();i++) {
      
        	xl[i]=(plik.df.get(x).list.get(i));
        		        	
        }
        for(int i =0;i<plik.size();i++) {
		      
        	yl[i]=(plik.df.get(y).list.get(i));
        		        	
        }
        for(int i=0;i<xl.length;i++) {
        series.getData().add(new XYChart.Data(xl[i].GetValue(), yl[i].GetValue()));
        
        }
        lineChart.getData().addAll(series);
    	
    }

   
	
	@FXML
	public void onActonButton() {
		
	}
	
	@FXML
	public void onActonButton2() {
		String max = " ",min= " ",mean= " ",var= " ",sum= " ",std =" ";
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("csv Files", "*.csv"));
		List<File> f = fc.showOpenMultipleDialog(null);
		for(File file: f) {
    	boolean header = true;
        try {
        	BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath())); 
        	
        	String firstLine = br.readLine();
        	String[] nazwy = firstLine.split(",");
        	
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
        	
        	
			DataFrame plik = new DataFrame(file.getAbsolutePath(),types,header);
			
			
			
			 
			    
			    for(int i =0; i<colnumber;i++) {
			    	max += plik.max().df.get(i).list.get(0).toString() +"		";
			    	min += plik.min().df.get(i).list.get(0).toString() +"		";
			    	mean += plik.mean().df.get(i).list.get(0).toString() +" 	 ";
			    	var += plik.var().df.get(i).list.get(0).toString() +"		";
			    	sum += plik.sum().df.get(i).list.get(0).toString() +"		";
			    	std += plik.std().df.get(i).list.get(0).toString() +"		";
			    }
			    	
			 
		        stats.setText("data frame stats:\n \n"+"max:  "+max+"\n"+"min:  "+min+"\n"+"mean: "+mean+"\n"+"var:  "+var+"\n"+"sum:  "+sum+"\n"+"std:  "+std+"\n");
		        
		    
		        
		        br.close();
		} catch (Exception e) {
			stats1.setText(e.toString());
			e.printStackTrace();
		}
        
		}
		
	}

	
	
	
	
}
