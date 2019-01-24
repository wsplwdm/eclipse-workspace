package threads;

import lab1.DataFrame;
import valueTypes.Double;
import valueTypes.Integer;
import valueTypes.SValue;
import valueTypes.Value;
import lab1.Column;

public class ThreadStd implements Runnable {
    private DataFrame df;
    private Column columnToProcess;
    public ThreadStd(Column col, DataFrame inputframe) {
        df=inputframe;
        columnToProcess=col;
    }

    @Override
    public void run() {
        Value var;
        for(Column k: df.getListOfColumns()){
        
	            if(k.getName().equals(columnToProcess.getName())){
	                var = columnToProcess.listOfValues.get(0);
	            	if(columnToProcess.listOfValues.get(0) instanceof SValue==true) {
	            		Value Returnv =new SValue(" ---- ");
	            		k.addElement(Returnv);
	            	}
	            	else {
	            		var=columnToProcess.listOfValues.get(0);
	            	
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    var =var.add(columnToProcess.listOfValues.get(i));
	
	                }
	
	                var= var.div(new Integer(columnToProcess.getColumnSize()));
	                var =  new Double( var.GetValue().toString());
	                Value Returnv = new Double(0);
	                Value colSize = new Double(columnToProcess.getColumnSize()-1);
	                for (int i = 0; i < columnToProcess.getColumnSize(); i++) {
	                    Returnv = Returnv.add((columnToProcess.listOfValues.get(i).sub(var)).pow(new Double(2)));
	                }
	                
	                Returnv=Returnv.div(colSize);
	                Returnv = Returnv.pow(new Double(0.5));
	            	
	                k.addElement(Returnv);
	            	}
	                break;
	            }
        	}
        }


    
}
