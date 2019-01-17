package lab1;

import lab1.DataFrame;
import lab1.Column;
import lab1.Value;

public class Thread implements Runnable {
    private Value id;
    private DataFrame dfToProcess;
    private DataFrame dfOutput;
    private Column groupcol;
    
    public Thread(DataFrame dftoprocess, Value value, DataFrame dfout, Column col) {
        id=value;
        dfToProcess =dftoprocess;
        dfOutput = dfout;
        groupcol =col;
    }

    @Override
    public void run() {
        Value[] values ;
       
        for(int j = 0; j< dfToProcess.size(); j++) {
            if(groupcol.list.get(j).eq(id)) {
                values = new Value[dfToProcess.toList().size() - 1];
                int i = 0;
                for (int k = 0; k < dfToProcess.toList().size(); k++) {
                    if (!dfToProcess.toList().get(k).getName().equals(groupcol.getName())) {
                        values[i] = dfToProcess.toList().get(k).list.get(j);
                        i++;
                    }
                }
                dfOutput.add(values);
            }
        }

    }
}
