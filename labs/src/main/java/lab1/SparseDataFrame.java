package lab1;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
	SValue hidelement;

private ArrayList<Column> sdf = new ArrayList<Column>();

public SparseDataFrame(String[] names, String[] types, SValue hide){
	super(names, types);
	hidelement = hide;
	}


public SparseDataFrame(Column[] cols){
	super(cols);
	}
//size = df.size
//Kol get = df. Kol get
public void add(Value[] vals,int col){
	//dodawanie obiektow z list do kolumny o wskazanym indeksie
	for(int i=0;i<vals.length;i++) {
		if(vals[i].toString()!=hidelement.toString()) {
			COOValue tmp = new COOValue(i,vals[i]);
			sdf.get(col).list.add(tmp);
		}
		sdf.get(col).anotherOneHidelement();
	}
    
	}
public DataFrame toDense(SparseDataFrame sparse) {
    Column[] cols = new Column[sdf.size()];
    int l =0;
	for(Column column:sdf) {
		int tmp=column.getColumnSize();
		Column ktmp= new Column(column.getName(),column.getType());
		for(int i=0;i<column.getDfsize()-tmp;i++) {
			ktmp.list.add(hidelement);
		for(int j=0;j<tmp;j++){
			ktmp.list.add(((COOValue) column.list.get(j)).getIndex(),((COOValue) column.get(j)).getValue());
			}
		
		}
		cols[l]=ktmp;
		l++;
	
	}
	DataFrame df = new DataFrame(cols);
	return df;
	
}

public SparseDataFrame(DataFrame dataf, SValue hide) {
	super(dataf);
	hidelement = hide;
	for(int i=0;i<dataf.df.size();i++){
		Column ktmp= new Column(dataf.get(i).getName(),dataf.get(i).getType());
		for(int j=0;j<dataf.df.get(0).getColumnSize();j++) {
			if(dataf.df.get(i).get(j)!=hide) {
				COOValue tmpc = new COOValue(j,dataf.df.get(i).get(j));
				ktmp.list.add(tmpc);
			}
		}
		
		sdf.add(ktmp);
	}
	
}




}




