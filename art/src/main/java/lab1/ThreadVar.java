package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Double;
import lab1.Integer;
import lab1.Value;

public class ThreadVar implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadVar(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {
        Value mean;
        for(Column k: df.toList()){
        	if(columnToProcess.list.get(0) instanceof SValue!=true) {
	            if(k.getName().equals(columnToProcess.getName())){
	                mean = columnToProcess.list.get(0);
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    mean =mean.add(columnToProcess.list.get(i));
	
	                }
	
	                mean= mean.div(new Integer(columnToProcess.getColumnSize()));
	                mean = new Double( mean.GetValue().toString());
	                Value Returnv = new Double(0);
	                Value colSize = new Double(columnToProcess.getColumnSize()-1);
	                for (int i = 0; i < columnToProcess.getColumnSize(); i++) {
	                    Returnv = Returnv.add((columnToProcess.list.get(i).sub(mean)).pow(new Double(2)));
	                }
	                Returnv=Returnv.div(colSize);
	
	                k.addElement(Returnv);
	                break;
	            }
        	}
        }


    }
}