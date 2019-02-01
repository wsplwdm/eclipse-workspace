import java.io.IOException;
import java.util.ArrayList;

import lab1.Column;
import lab1.DataFrame;

public class MainGroupbyIdMax {

	public static void main(String[] args) {
		DataFrame df;
		try {
			df = new DataFrame("Z:\\groupby.csv");
		int[] space = new int[df.getNumberOfCOlumns()];
		String colnames="";
		int id=0;
		for(int i=0;i<df.getNumberOfCOlumns();i++) {
			if(df.get(i).getName().equals("id")){
				id =i; 
			}
			space[i]=df.get(i).get(1).toString().length()-df.get(i).getName().toString().length();
			for(int k=0;k<=space[i];k++) {
				colnames+=" ";				
			}
			colnames+=df.get(i).getName();
			
		}
		//df.print();
		ArrayList<DataFrame> groupbyList = df.groupby(0);
		
		System.out.println(colnames);
		for(int j =0;j<groupbyList.size();j++) {
			//frame.print();
			System.out.print(df.getListOfColumns().get(id).get(j)+"	");
			
			groupbyList.get(j).max().print();
		}
		} catch (IOException e) {
					
				e.printStackTrace();
			}
	}

}
