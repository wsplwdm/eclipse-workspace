package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Double;
import lab1.Integer;
import lab1.Value;

public class ThreadVar implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadVar(Column k, DataFrame inputframe) {
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
                mean = new Double( mean.GetValue().toString());
                Value Returnv = new Double(0);
                Value colSize = new Double(colToCount.getColumnSize()-1);
                for (int i = 0; i < colToCount.getColumnSize(); i++) {
                    Returnv = Returnv.add((colToCount.list.get(i).sub(mean)).pow(new Double(2)));
                }
                Returnv=Returnv.div(colSize);

                k.addElement(Returnv);
                break;
            }
        }


    }
}