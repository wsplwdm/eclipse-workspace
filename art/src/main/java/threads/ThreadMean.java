package threads;

import lab1.DataFrame;
import valueTypes.Integer;
import valueTypes.SValue;
import valueTypes.Value;
import lab1.Column;

public class ThreadMean implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadMean(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {
        Value mean;
        for(Column k: df.getListOfColumns()){
        	
	            if(k.getName().equals(columnToProcess.getName())){
	                mean = columnToProcess.listOfValues.get(0);
	                if(columnToProcess.listOfValues.get(0) instanceof SValue==true) {
	                	mean=new SValue(" ---- ");}
	                else {
	                	mean=columnToProcess.listOfValues.get(0);
	               
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    mean =mean.add(columnToProcess.listOfValues.get(i));
	
	                }
	                }
	                mean= mean.div(new Integer(columnToProcess.getColumnSize()));
	                k.addElement(mean);
	                break;
	            }
        	}
        }


    
}