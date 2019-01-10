package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Double;
import lab1.Integer;
import lab1.Value;

public class ThreadStd implements Runnable {
    private DataFrame frame;
    private Column colToCount;
    public ThreadStd(Column k, DataFrame inputframe) {
        frame=inputframe;
        colToCount=k;
    }

    @Override
    public void run() {
        Value var;
        for(Column k: frame.toList()){
            if(k.getName().equals(colToCount.getName())){
                var = colToCount.list.get(0);
                for(int i=1;i<colToCount.getColumnSize();i++){
                    var =var.add(colToCount.list.get(i));

                }

                var= var.div(new Integer(colToCount.getColumnSize()));
                var =  new Double( var.GetValue().toString());
                Value Returnv = new Double(0);
                Value colSize = new Double(colToCount.getColumnSize()-1);
                for (int i = 0; i < colToCount.getColumnSize(); i++) {
                    Returnv = Returnv.add((colToCount.list.get(i).sub(var)).pow(new Double(2)));
                }
                Returnv=Returnv.div(colSize);
                Returnv = Returnv.pow(new Double(0.5));
                k.addElement(Returnv);
                break;
            }
        }


    }
}
