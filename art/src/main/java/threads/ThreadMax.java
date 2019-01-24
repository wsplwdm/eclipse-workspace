package threads;


import lab1.DataFrame;
import valueTypes.Integer;
import valueTypes.SValue;
import valueTypes.Value;
import lab1.Column;

public class ThreadMax implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadMax(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {
    	try {
	        for(Column k: df.getListOfColumns()){
	            if(k.getName().equals(columnToProcess.getName())){
	            	
	            	Value max=new valueTypes.Integer(java.lang.Integer.MIN_VALUE);
	            	if(columnToProcess.listOfValues.get(0) instanceof SValue ==true) {
	            		max = new SValue(" ---- ");}
	            	else {
	            		max=columnToProcess.listOfValues.get(0);
	            	
	            
	                for(int i=0;i<columnToProcess.getColumnSize();i++){
	                	if(columnToProcess.listOfValues.get(i) instanceof SValue !=true) {
		                    if(max.lte(columnToProcess.listOfValues.get(i))){
		                        max=columnToProcess.listOfValues.get(i);
		                    }
	                	}
	                }
	            	}
	                k.addElement(max);
	                break;
	            }
	            
	        }

		}catch(Exception e) {}
    	
    }
}
