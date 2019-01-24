package threads;

import lab1.DataFrame;
import valueTypes.SValue;
import valueTypes.Value;
import lab1.Column;

public class ThreadSum implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadSum(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {
        Value sum;
        for(Column k: df.getListOfColumns()){
        	
	            if(k.getName().equals(columnToProcess.getName())){
	                sum = columnToProcess.listOfValues.get(0);
	                if(columnToProcess.listOfValues.get(0) instanceof SValue==true) {
	                	sum=new SValue(" ---- ");}
	                else {
	                	sum=columnToProcess.listOfValues.get(0);
	                
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    sum =sum.add(columnToProcess.listOfValues.get(i));
	
	                }
	                }
	                k.addElement(sum);
	                break;
	            }
        	}
        }


    
}