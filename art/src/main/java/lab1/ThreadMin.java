package lab1;


import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class ThreadMin implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadMin(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {

    	 for(Column k: df.getListOfColumns()){
             if(k.getName().equals(columnToProcess.getName())){
            	 Value min=new lab1.Integer(java.lang.Integer.MAX_VALUE);
            	 if(columnToProcess.listOfValues.get(0) instanceof SValue ==true) {
 	                min = new SValue(" ---- ");}
            	 else {
            		 min=columnToProcess.listOfValues.get(0);
            	
                 for(int i=0;i<columnToProcess.getColumnSize();i++){
                	 if(columnToProcess.listOfValues.get(i) instanceof SValue !=true) {
	                     if(min.gte(columnToProcess.listOfValues.get(i))){
	                         min=columnToProcess.listOfValues.get(i);
	                     }
                	 }
                }
            	 }
                k.addElement(min);
                break;
            }
        }


    }
}

