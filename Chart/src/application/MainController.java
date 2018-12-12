package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class MainController implements Initializable {
	
		@FXML
	    private LineChart<?, ?> LineChart;

	    @FXML
	    private CategoryAxis x;

	    @FXML
	    private NumberAxis y;

	    
	    public void initialize(URL url, ResourceBundle rb) {
	    	XYChart.Series series = new XYChart.Series();
	    	series.getData().add(new XYChart.Data("1",23));
	    	LineChart.getData().addAll(series);
	    	}
	}