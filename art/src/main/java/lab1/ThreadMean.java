package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Integer;
import lab1.Value;

public class ThreadMean implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadMean(Column k, DataFrame inputframe) {
        frame=inputframe;
        colToCount=k;
    }

    @Override
    public void run() {
        Value mean;
        for(Column k: frame.toList()){
            if(k.getName().equals(colToCount.getName())){
                mean = colToCount.list.get(0);
                for(int i=1;i<colToCount.getColumnSize();i++){
                    mean =mean.add(colToCount.list.get(i));

                }

                mean= mean.div(new Integer(colToCount.getColumnSize()));
                k.addElement(mean);
                break;
            }
        }


    }
}