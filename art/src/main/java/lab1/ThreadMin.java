package lab1;


import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class ThreadMin implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadMin(Column k, DataFrame inputframe) {
        frame=inputframe;
        colToCount=k;
    }

    @Override
    public void run() {

    	 for(Column k: frame.toList()){
             if(k.getName().equals(colToCount.getName())){
                 Value min = colToCount.list.get(0);
                 for(int i=0;i<colToCount.getColumnSize();i++){
                     if(min.gte(colToCount.list.get(i))){
                         min=colToCount.list.get(i);
                     }
                }
                k.addElement(min);
                break;
            }
        }


    }
}

