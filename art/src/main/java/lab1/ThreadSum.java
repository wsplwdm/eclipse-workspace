package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

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
        for(Column k: df.toList()){
        	
	            if(k.getName().equals(columnToProcess.getName())){
	                sum = columnToProcess.list.get(0);
	                if(columnToProcess.list.get(0) instanceof SValue!=true) {
	                	sum=new SValue(" ---- ");}
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    sum =sum.add(columnToProcess.list.get(i));
	
	                }
	                k.addElement(sum);
	                break;
	            }
        	}
        }


    
}