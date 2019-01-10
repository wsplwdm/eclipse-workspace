package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class Thread implements Runnable {
    private Value id;
    private DataFrame inputDataFrame;
    private DataFrame outputDataFrame;
    private Column groupingCol;
    
    public Thread(DataFrame array, Value value, DataFrame dataFrame, Column s) {
        id=value;
        inputDataFrame =array;
        outputDataFrame = dataFrame;
        groupingCol =s;
    }

    @Override
    public void run() {
        Value[] valuesToAdd ;
        int h =0;
        for(int j = 0; j< inputDataFrame.sizeOfCol(); j++) {
            if(groupingCol.list.get(j).eq(id)) {
                valuesToAdd = new Value[inputDataFrame.toList().size() - 1];
                h = 0;
                for (int i = 0; i < inputDataFrame.toList().size(); i++) {
                    if (!inputDataFrame.toList().get(i).getName().equals(groupingCol.getName())) {
                        valuesToAdd[h] = inputDataFrame.toList().get(i).list.get(j);
                        h++;
                    }
                }
                outputDataFrame.add(valuesToAdd);
            }
        }

    }
}
