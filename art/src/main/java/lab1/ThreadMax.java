package lab1;


import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class ThreadMax implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadMax(Column k, DataFrame inputframe) {
        frame=inputframe;
        colToCount=k;
    }

    @Override
    public void run() {

        for(Column k: frame.toList()){
            if(k.getName().equals(colToCount.getName())){
                Value max = colToCount.list.get(0);
                for(int i=0;i<colToCount.getColumnSize();i++){
                    if(max.lte(colToCount.list.get(i))){
                        max=colToCount.list.get(i);
                    }
                }
                k.addElement(max);
                break;
            }
        }


    }
}
