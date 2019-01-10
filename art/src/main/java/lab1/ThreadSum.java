package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class ThreadSum implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadSum(Column k, DataFrame inputframe) {
        frame=inputframe;
        colToCount=k;
    }

    @Override
    public void run() {
        Value sum;
        for(Column k: frame.toList()){
            if(k.getName().equals(colToCount.getName())){
                sum = colToCount.list.get(0);
                for(int i=1;i<colToCount.getColumnSize();i++){
                    sum =sum.add(colToCount.list.get(i));

                }
                k.addElement(sum);
                break;
            }
        }


    }
}