package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Double;
import lab1.Integer;
import lab1.Value;

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
        for(Column k: df.toList()){
        	if(columnToProcess.list.get(0) instanceof SValue!=true) {
	            if(k.getName().equals(columnToProcess.getName())){
	                var = columnToProcess.list.get(0);
	                for(int i=1;i<columnToProcess.getColumnSize();i++){
	                    var =var.add(columnToProcess.list.get(i));
	
	                }
	
	                var= var.div(new Integer(columnToProcess.getColumnSize()));
	                var =  new Double( var.GetValue().toString());
	                Value Returnv = new Double(0);
	                Value colSize = new Double(columnToProcess.getColumnSize()-1);
	                for (int i = 0; i < columnToProcess.getColumnSize(); i++) {
	                    Returnv = Returnv.add((columnToProcess.list.get(i).sub(var)).pow(new Double(2)));
	                }
	                Returnv=Returnv.div(colSize);
	                Returnv = Returnv.pow(new Double(0.5));
	                k.addElement(Returnv);
	                break;
	            }
        	}
        }


    }
}
