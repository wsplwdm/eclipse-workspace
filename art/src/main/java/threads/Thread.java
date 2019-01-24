package threads;

import lab1.DataFrame;
import valueTypes.Value;
import lab1.Column;

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
            if(groupcol.listOfValues.get(j).eq(id)) {
                values = new Value[dfToProcess.getListOfColumns().size() - 1];
                int i = 0;
                for (int k = 0; k < dfToProcess.getListOfColumns().size(); k++) {
                    if (!dfToProcess.getListOfColumns().get(k).getName().equals(groupcol.getName())) {
                        values[i] = dfToProcess.getListOfColumns().get(k).listOfValues.get(j);
                        i++;
                    }
                }
                dfOutput.add(values);
            }
        }

    }
}
