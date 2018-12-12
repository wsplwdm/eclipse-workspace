# java
package lab2;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
	
	private ArrayList<Kol> df = new ArrayList<Kol>();
	
	public SparseDataFrame(String[] nazwy, String[] typy, String ifhide){
		super(nazwy, typy);
		
	}
	public SparseDataFrame(DataFrame old){
		
	}
	public void add(COOValue coovalue, int colindex){
		df.get(colindex).list.add(coovalue);
	}
	
	public DataFrame toDense(SparseDataFrame frame){
		String[] nazwy;
		String[] typy;
		for(Kol kol:df){
			nazwy.add(kol.getNazwa());
			typy.add(kol.getTyp());
		}
		DataFrame returndataframe = new DataFrame(nazwy, typy);
		for(Kol kol:df){
			for(int i=0;i<kol.getKolSize();i++){
				returndataframe.get(//kol).lista.add(i, 0);
			}
			int index = df.kol.list.getIndex;
			double element = df.kol.getValue;
			
			returndataframe.get(kol).lista.add(index, element);
			
				
			}
				
				
		
		
		}
		returndataframe.add(wartosci)
		
		return returndataframe;
	}
}
package lab2;

public class COOValue {
	private int index;
	private double value;
	public COOValue(int index,double value){
		this.index = index;
		this.value=value;
	}
	public int getIndex(){
		return index;
	}
	public double getValue(){
		return value;
		
	}

}
