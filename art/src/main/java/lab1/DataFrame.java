package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import threads.Thread;
import threads.ThreadMax;
import threads.ThreadMean;
import threads.ThreadMin;
import threads.ThreadStd;
import threads.ThreadSum;
import threads.ThreadVar;
import valueTypes.Double;
import valueTypes.Float;
import valueTypes.Integer;
import valueTypes.SValue;
import valueTypes.Value;



public class DataFrame implements groupby, Serializable{
    public ArrayList<Column> listOfColumns = new ArrayList<Column>();
    public String groupelement;
    private Value groupbyid;
    
   

    public DataFrame() {}
    public DataFrame(String[] names, String[] types){
    	///Tworzenie datafram'a z pustymi kolumnami
        for (int i=0; i<names.length; i++)
        {
            listOfColumns.add(new Column(names[i],types[i]));
        }
    }
    
   
    public DataFrame(String[] namesofcolumns, Value[] typesofcolumns) {
    	 ///Tworzenie datafram'a z pustymi kolumnami
        try {
        	for (int i = 0; i < namesofcolumns.length; ++i) {
        		listOfColumns.add(new Column(namesofcolumns[i], typesofcolumns[i]));
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }

    }

    
    public DataFrame(String file,String[] types,boolean header) throws Exception{
    	///tworzenie datafram'a z pliku
    	FileInputStream fstream = new FileInputStream(file);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	String strLine;
    	
    	if(header==true) {
    		String pierwszaLinia = br.readLine();
    		String[] nazwy = pierwszaLinia.split(",");
    		for (int i=0; i<nazwy.length; i++)
            {
                listOfColumns.add(new Column(nazwy[i],types[i]));
            }    		
    	}
    	
    	while ((strLine = br.readLine()) != null){
    		String[] tmp = strLine.split(",");
    		Float[] tmp2 = new Float[tmp.length];
    		for(int i=0;i<tmp.length;i++) {
    			Float tmp3 = new Float();
    			tmp3.create(tmp[i]);
    			
    			
    			tmp2[i]=tmp3;
    		}
    		this.add((Float[]) tmp2);
    	}
    	
    	br.close();
	  
    }
    
    public DataFrame(String file) throws IOException {
    	///tworzenie datafram'a ze ścieżki do pliku, pierwszy wiersz jest traktowany jako nazwy kolumn
    	FileInputStream fstream1 = new FileInputStream(file);
    	BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
    	
    	String firstLine = br1.readLine();
    	String[] nazwy = firstLine.split(",");
    	int colnumber = nazwy.length;
    	String secondLine = br1.readLine();
    	String[] typ = secondLine.split(",");
    	Value[] typy = new Value[colnumber];
    	
    	for(int i =0;i<colnumber;i++) {
    		try {
    			int result = java.lang.Integer.parseInt(typ[i]);
    			Integer resulttype = new valueTypes.Integer(result);	
    			typy[i]=resulttype;
    			}
    		catch(NumberFormatException ei){
    			try {
    				double result = java.lang.Double.parseDouble(typ[i]);
    				valueTypes.Double resulttype = new valueTypes.Double(result);
    				typy[i]=resulttype;
    				}
    			catch(NumberFormatException ed) {
    				try {
        				float result = java.lang.Float.parseFloat(typ[i]);
        				valueTypes.Float resulttype = new valueTypes.Float(result);
        				typy[i]=resulttype;            				}
        			catch(NumberFormatException ef) {
        				String result = typ[i];
        				valueTypes.SValue resulttype = new valueTypes.SValue(result);
        				typy[i]=resulttype;
        				}
    			}
    		}
    		
    	}
    	for (int i=0; i<nazwy.length; i++)
        {
            listOfColumns.add(new Column(nazwy[i],typy[i]));
        }
    	br1.close();
    	FileInputStream fstream = new FileInputStream(file);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	String strLine;
    	firstLine = br.readLine();
    	while ((strLine = br.readLine()) != null){
    		String[] values = strLine.split(",");
    		if(values.length!=nazwy.length) {
    			throw new RuntimeException("invalid dataframe file");
    		}
    		else {
    		for(int j=0;j<nazwy.length;j++) {
    			try {
    				boolean sv=true;
    			if(typy[j].getInstance() instanceof Integer) {
    				sv=false;
    				Integer addelement = new Integer();
    				addelement.create(values[j]);
    				listOfColumns.get(j).addElement(addelement);
    				//System.out.println("adding int   "+addelement.toString()+"  to  "+nazwy[j]+sv);
    				
    			}
    			if(typy[j].getInstance() instanceof Double && sv==true) {
    				sv=false;
    				Double addelement = new Double();
    				addelement.create(values[j]);
    				listOfColumns.get(j).addElement(addelement);
    				//System.out.println("adding double   "+addelement.toString()+"  to  "+nazwy[j]+sv);
    				
    			}
    			if(typy[j].getInstance() instanceof Float && sv==true) {
    				sv=false;
    				Float addelement = new Float();
    				addelement.create(values[j]);
    				listOfColumns.get(j).addElement(addelement);
    				//System.out.println("adding float   "+addelement.toString()+"  to  "+nazwy[j]+sv);
    				
    			}
    			//System.out.println("sprawdzam czy sv="+sv);
    			if(sv==true) {
    				SValue addelement = new SValue();
    				addelement.create(values[j]);
    				listOfColumns.get(j).addElement(addelement);
    				//System.out.println("adding string "+addelement.toString()+"  to  "+nazwy[j]+sv);
    			}
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}	
    		}
    		
    	}
    	
    	br.close();
    }
    
    
   
    
    
    public DataFrame(Column[] Kolumny){
    	//tworzenie datafram'a z kolumn
        for (int i=0; i<Kolumny.length; i++) {
            listOfColumns.add(Kolumny[i]);
        }
    }
   
    
    public DataFrame(DataFrame dftosave) {
		// TODO Auto-generated constructor stub
	}
    
    public void saveToCSV(String path){
    	FileWriter fileWriter;
    	String path2=path.replace(".csv", "_zapis.csv");
		try {
			fileWriter = new FileWriter(path2);
		
        PrintWriter writer = new PrintWriter(fileWriter);
    	
		for(Column col:listOfColumns) {
			writer.print(col.getName()+",");
		}
		writer.print("\n");
        
       
    	 for (int i=0; i<size(); i++){
    		 System.out.println("for i");
             for (Column col:listOfColumns){
            	 System.out.println("for col");
            	 if(col.getListOfValues().get(i).getType()=="SValue") {
            		 System.out.println("if");
            		 writer.print("\""+col.getListOfValues().get(i).GetValue()+"\",");
            		 System.out.println("\""+col.getListOfValues().get(i).GetValue()+"\",");
            	 }
            	 else {
                 System.out.println("else");
            	 writer.print(col.getListOfValues().get(i).GetValue()+",");
            	 System.out.println(col.getListOfValues().get(i).GetValue()+",");
            	 }
             }
             writer.write("\n");
         }
    	  writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
   
	public int size(){
		 /// zwraca ilość wierszy datafram'a
        return this.listOfColumns.get(0).getColumnSize();
    } 
	public int getNumberOfCOlumns(){
		 /// zwraca ilość wierszy datafram'a
       return this.listOfColumns.size();
   } 
   
    
  
	
     public  Column get(String colname){
    	// zwraca kolumnę o podanej nazwie
        for (Column col:listOfColumns){
            if (col.getName()==colname){
            	return col;
            }
        }
        throw new RuntimeException("Kol not found!");
        
    }
     
    public Column get(int i) {
    	//zwraca kolumnę o podanym indeksie
    	return listOfColumns.get(i);
    }

    public DataFrame get(String [] cols, boolean copy){ 
    	//zwraca datafram'a stworzonego z podanych kolumn
        Column[] tmp = new Column[cols.length];
        for (int i=0; i<tmp.length; i++){
           
            if (copy==true){ 
            	tmp[i] = new Column(get(cols[i]));
            }
            else{ 
            	tmp[i] = this.get(cols[i]);
            }
        }
        DataFrame dataFrame = new DataFrame(tmp);
        return dataFrame;
    }

    
    public void add(Value[] tmp){
    	///dodaje wiersz
        int i = 0;
        for (Column col:listOfColumns){
            col.getListOfValues().add(tmp[i++]);
        }
    }
    
    public void add(Value[] vals,int col){
    	//dodawanie obiektow z list do columny o wskazanym indeksie
    	for(int i=0;i<vals.length;i++) {
    			listOfColumns.get(col).getListOfValues().add(vals[i]);
    		}
    		
    	}
        
    public DataFrame iloc(int a, int b){
    	//zwraca wiersze z podanego zakresu (jako dataframe)
        
        int size = listOfColumns.size();
        String[] types = new String[size];
        String[] names = new String[size];
        for (int i=0; i<size; i++){
            types[i] = listOfColumns.get(i).getType();
            names[i] = listOfColumns.get(i).getName();
        }
        DataFrame Nowadf = new DataFrame(names,types);
        Value[] tmp = new Value[listOfColumns.size()];
        for (int i=a; i<=b; i++){
            for (int j=0; j<tmp.length; j++){
        
                tmp[j] = listOfColumns.get(j).getListOfValues().get(i);
            }
            Nowadf.add(tmp);

        }
        return Nowadf;
    }
    
    public DataFrame iloc(int i){
    	//zwraca wiersz o podanym indeksie (jako dataframe)
        return iloc(i,i);
    }
    public ArrayList<Column> getListOfColumns(){
    	//zwraca listę kolumn
        return listOfColumns;
    }
    void setGroupElement(String v){
        groupelement=v;
    }

    
    public void print(){
    	//drukuje datafram'a
        for (int i=0; i<size(); i++){
            for (Column col:listOfColumns){
                System.out.print(col.getListOfValues().get(i)+"   ");
            }
            System.out.println();
        }
    }
    
  
    
    
    public String[] statistics(String path) throws IOException {
    	//otwiera datafram'a i zwraca statystyki 
    	String[] ret= {"","","","","",""};
    	//max min mean vaar sum std\
    	DataFrame tmp = new DataFrame(path);
    	for(int i =0; i<tmp.listOfColumns.size();i++) {
	    	
	    	DataFrame plik;
			try {
				plik = new DataFrame(path);
			
	    	
	    	
	    	ret[0] += plik.maxthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +"		";
	    	ret[1] += plik.minthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +"		";
	    	ret[2] += plik.meanthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +" 		 ";
	    	ret[3] += plik.varthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +"		";
	    	ret[4] += plik.sumthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +"		";
	    	ret[5] += plik.stdthrd().listOfColumns.get(i).getListOfValues().get(0).toString() +"		";
	    	
	    
    	
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	return ret;
    }
    
    public Value getId(){
        return groupbyid;
    }
    public void setGroupbyId(Value v){
        groupbyid=v;
    }
    
    public ArrayList<DataFrame> groupby(int id) {
    	// groupby według indeksu kolumny
    	Column groupByThisColumn = listOfColumns.get(id);
        
        String[] names = new String[getListOfColumns().size()-1];
        Value[] types = new Value[getListOfColumns().size()-1] ;
        
        ArrayList<DataFrame> returnDataFrame = new ArrayList<DataFrame>();
        ArrayList<Value> tmpValues = new ArrayList<Value>();
        
        int i=0;
        for(Column col: getListOfColumns()){
            if(groupByThisColumn!=col) {
            	names[i] = col.getName();
                types[i] = col.getVType();
                
                i++;
            }

        }
        for(i =0;i<size();i++){
            if(tmpValues.contains(groupByThisColumn.getListOfValues().get(i))!=true){
            	
                tmpValues.add(groupByThisColumn.getListOfValues().get(i));
            }

        }
        for(i =0;i<tmpValues.size();i++){
            returnDataFrame.add(new DataFrame(names,types));
            returnDataFrame.get(i).setGroupbyId(tmpValues.get(i));
        }
        Value[] values ;
        int j ;
        for(i =0;i<size();i++){

            values = new Value[getListOfColumns().size()-1];
            j=0;
            for(Column col: getListOfColumns()){
                if(groupByThisColumn!=col){
                    values[j]=col.getListOfValues().get(i);
                    j++;
                }
            }


            for(int k=0;k<returnDataFrame.size();k++){
                if(groupByThisColumn.getListOfValues().get(i).eq(returnDataFrame.get(k).getId())){

                    returnDataFrame.get(k).add(values);
                    break;
                }
            }
        }
        return returnDataFrame;
    }
    
    public ArrayList<DataFrame> groupbyThread(int id){
    	//groupby wielowątkowy
    	Column groupByThisColumn = listOfColumns.get(id);
    	
    	String[] names = new String[getListOfColumns().size()-1];
    	Value[] types = new Value[getListOfColumns().size()-1] ;
    	
    	ArrayList<DataFrame> returnDataFrame = new ArrayList<DataFrame>();
        ArrayList<Value> tmpValues = new ArrayList<Value>();
        
        
        int processors = Runtime.getRuntime().availableProcessors();
        
        int i=0;
        for(Column col: getListOfColumns()){
            if(col!=groupByThisColumn) {
            	
            	names[i] = col.getName();
                types[i] = col.getVType();
                
                i++;
            }

        }
        for(i =0;i<size();i++){
            if(tmpValues.contains(groupByThisColumn.getListOfValues().get(i))!=true){
                tmpValues.add(groupByThisColumn.getListOfValues().get(i));
            }

        }
        for(i =0;i<tmpValues.size();i++){
            returnDataFrame.add(new DataFrame(names,types));
            returnDataFrame.get(i).setGroupbyId(tmpValues.get(i));
        }


        ExecutorService executor = Executors.newFixedThreadPool(processors);
        
        
        for(i =0;i<tmpValues.size();i++){
            Runnable workerthread = new Thread(this,tmpValues.get(i),returnDataFrame.get(i),groupByThisColumn);
            executor.execute(workerthread);
        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                
            }
        } catch (InterruptedException e) {
        	executor.shutdownNow();
            e.printStackTrace();
            
        }
        return returnDataFrame;
    }
    
    
    
    
    //max i min działający tylko na danej kolumnie zwracający pojedynczą Value z wynikiem
  public Value max(Column col) {
	  Value max=col.get(0);
	  for(Value v:col.getListOfValues()) {
		  if(v.gte(max))
			  max=v;
	  }
	  return max;
  }
  public Value min(Column col) {
	  Value min=col.get(0);
	  for(Value v:col.getListOfValues()) {
		  if(v.lte(min))
			  min=v;
	  }
	  return min;
  }
    
    
    public DataFrame max() {
        Value max = getListOfColumns().get(0).getListOfValues().get(0);
        Column[] NewKols = new Column[getListOfColumns().size()];
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		max = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(max);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                max = getListOfColumns().get(it).getListOfValues().get(0);
                for (int i = 0; i < k.getColumnSize(); i++) {
                    if (k.getListOfValues().get(i).gte(max)) {
                        max = k.getListOfValues().get(i);
                    }
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].getListOfValues().add(max);
                it++;
            }
            else{
                NewKols[it]= new Column(k);
                it++;
            }
        	}
        }
        
        
        return new DataFrame(NewKols);
    }
    
    
    
    
    public DataFrame min() {
        Value min = getListOfColumns().get(0).getListOfValues().get(0);
        Column[] NewKols = new Column[getListOfColumns().size()];
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		min = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(min);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                min = getListOfColumns().get(it).getListOfValues().get(0);
                for (int i = 0; i < k.getColumnSize(); i++) {
                    if (k.getListOfValues().get(i).lte(min)) {
                        min = k.getListOfValues().get(i);
                    }
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].getListOfValues().add(min);
                it++;
            }
            else{
                NewKols[it]= new Column(k);
                it++;
            }
            }
        }
        return new DataFrame(NewKols);
    }

    public DataFrame mean() {
        Value mean;
        Column[] NewKols = new Column[getListOfColumns().size()];
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		mean = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(mean);
                it++;
        	} 
        	else {
	        	if(groupelement==null || groupelement!=k.getName()) {
	            	
	                mean = getListOfColumns().get(it).getListOfValues().get(0);
	                for (int i = 1; i < k.getColumnSize(); i++) {
	                    mean = mean.add(k.getListOfValues().get(i));
	                }
	                mean = mean.div(new Double(k.getColumnSize()));
	                NewKols[it] = new Column(k.getName(), k.getType());
	                NewKols[it].getListOfValues().add(mean);
	                it++;
	            }
	            else{
	                NewKols[it]= new Column(k);
	                it++;
	            }
        	}
        }
        return new DataFrame(NewKols);
    }

    public DataFrame std() {
        DataFrame newmean= this.mean();
        Value Returnv = new Integer(0);
        Value v = new Integer(this.size());
        Column[] NewKols = new Column[getListOfColumns().size()];
        
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		Returnv = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(Returnv);
                it++;
        	}
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
            	
            	
                for(int i=0;i<k.getColumnSize();i++){
                    Returnv =Returnv.add( (k.getListOfValues().get(i).sub(newmean.getListOfColumns().get(it).getListOfValues().get(0))).pow(new Integer(2)));
                }
                Returnv=Returnv.div(v);
                Returnv=Returnv.pow(new Double(0.5));
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].getListOfValues().add(Returnv);
                Returnv = new Integer(0);
                it++;
            }
            else{
                NewKols[it]= new Column(k);
                it++;
            }
            }
        }
        return new DataFrame(NewKols);
    }

    public DataFrame sum() {
        Value sum;
        Column[] NewKols = new Column[getListOfColumns().size()];
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		sum = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(sum);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                sum = getListOfColumns().get(it).getListOfValues().get(0);
                for (int i = 1; i < k.getColumnSize(); i++) {
                    sum = sum.add(k.getListOfValues().get(i));
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].getListOfValues().add(sum);
                it++;
            }
            else{
                NewKols[it]= new Column(k);
                it++;
            }
        	}
        }
        return new DataFrame(NewKols);
    }

    public DataFrame var() {
        DataFrame newmean= this.mean();
        Value Returnv = new Integer(0);
        Value v = new Integer(this.size());
        Column[] NewKols = new Column[getListOfColumns().size()];
        
        int it=0;
        for(Column k: getListOfColumns()){
        	if(k.getVType() instanceof SValue) {
        		Returnv = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].getListOfValues().add(Returnv);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                for(int i=0;i<k.getColumnSize();i++){
                    Returnv =Returnv.add( k.getListOfValues().get(i).sub(newmean.getListOfColumns().get(it).getListOfValues().get(0)));
                }
                Returnv=Returnv.div(v);
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].getListOfValues().add(Returnv);
                Returnv = new Integer(0);
                it++;
            }
            else{
                NewKols[it]= new Column(k);
                it++;
            }
            }
        }
        return new DataFrame(NewKols);
    }

    public DataFrame apply(Applyable operation) {
        Column[] Cols = new Column[size()];
        int it=0;
        for(Column k: getListOfColumns()){
            Cols[it]=new Column(k);
            it++;
        }
        DataFrame returndf = new DataFrame(Cols);
        operation.apply(returndf);
        for(int i=0;i<returndf.getListOfColumns().size();i++){
            if(returndf.getListOfColumns().get(i).getName()==groupelement){
                returndf.getListOfColumns().set(i, new Column(this.getListOfColumns().get(i)));

        }

    }
    return returndf;
    }

    public DataFrame maxthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadMax(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
               
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }


    public DataFrame minthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadMin(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                //do nothing, just wait
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }

 
    public DataFrame meanthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadMean(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                //do nothing, just wait
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }


    public DataFrame stdthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadStd(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                //do nothing, just wait
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }


    public DataFrame sumthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadSum(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                //do nothing, just wait
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }

   
    public DataFrame varthrd(){
        int cores = Runtime.getRuntime().availableProcessors();
        String[] names = new String[getListOfColumns().size()];
        Value[] types = new Value[getListOfColumns().size()];
        for(int i=0;i<getListOfColumns().size();i++){
            names[i]=getListOfColumns().get(i).getName();
            types[i]=getListOfColumns().get(i).getVType();
        }
        DataFrame outputFrame = new DataFrame(names,types);

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (Column k : getListOfColumns()) {
            Runnable worker = new ThreadVar(k,outputFrame);
            executor.execute(worker);

        }
        executor.shutdown();
        try {
            while(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                //do nothing, just wait
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        return outputFrame ;
    }

    
    
    
    
    
    
    
    //dodawanie, odejmowanie, mnozenie, dzielenie, potegowanie element�w columny przez wartosc
    public Column colvalmul(Column k,Value mulelement) {
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.getListOfValues().get(i).mul(mulelement));
    	}
    	return tmp;
    }
    public Column colvaldiv(Column k,Value divelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.getListOfValues().get(i).div(divelement));
    	}
    	return tmp;
    }
    public Column colvaladd(Column k,Value addelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.getListOfValues().get(i).add(addelement));
    	}
    	return tmp;
    }
    public Column colvalsub(Column k,Value subelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.getListOfValues().get(i).sub(subelement));
    	}
    	return tmp;
    }
    public Column colvalpow(Column k,Value powelement)throws RuntimeException {
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.getListOfValues().get(i).pow(powelement));
    	}
    	return tmp;
    }
    public Column colcoladd(Column k,Column addelement)throws RuntimeException {
    	if(k.getColumnSize()==addelement.getColumnSize()) {
    		Column tmp = new Column(k.getName(),k.getType());
        	for(int i=0;i< k.getColumnSize();i++) {
        		tmp.addElement(k.getListOfValues().get(i).add(addelement.getListOfValues().get(i)));
        	}
        	return tmp;
    		
    	}
    	else {
    		throw new RuntimeException("different sizes of cols");
    	}
    	
    }
    public Column colcolsub(Column k,Column subelement) throws RuntimeException{
    	if(k.getColumnSize()==subelement.getColumnSize()) {
    		Column tmp = new Column(k.getName(),k.getType());
        	for(int i=0;i< k.getColumnSize();i++) {
        		tmp.addElement(k.getListOfValues().get(i).sub(subelement.getListOfValues().get(i)));
        	}
        	return tmp;
    		
    	}
    	else {
    		throw new RuntimeException("different sizes of cols");
    	}
    	
    }
    public Column colcolmul(Column k,Column element) throws RuntimeException{
    	if(k.getColumnSize()==element.getColumnSize()) {
    		Column tmp = new Column(k.getName(),k.getType());
        	for(int i=0;i< k.getColumnSize();i++) {
        		tmp.addElement(k.getListOfValues().get(i).mul(element.getListOfValues().get(i)));
        	}
        	return tmp;
    		
    	}
    	else {
    		throw new RuntimeException("different sizes of cols");
    	}
    }
    	public Column colcoldiv(Column k,Column element)throws RuntimeException {
        	if(k.getColumnSize()==element.getColumnSize()) {
        		Column tmp = new Column(k.getName(),k.getType());
            	for(int i=0;i< k.getColumnSize();i++) {
            		tmp.addElement(k.getListOfValues().get(i).div(element.getListOfValues().get(i)));
            	}
            	return tmp;
        		
        	}
        	else {
        		throw new RuntimeException("different sizes of cols");
        	}
    	
    }
    	public Column colcolpow(Column k,Column element)throws RuntimeException {
        	if(k.getColumnSize()==element.getColumnSize()) {
        		Column tmp = new Column(k.getName(),k.getType());
            	for(int i=0;i< k.getColumnSize();i++) {
            		tmp.addElement(k.getListOfValues().get(i).pow(element.getListOfValues().get(i)));
            	}
            	return tmp;
        		
        	}
        	else {
        		throw new RuntimeException("different sizes of cols");
        	}
    	
    }
}