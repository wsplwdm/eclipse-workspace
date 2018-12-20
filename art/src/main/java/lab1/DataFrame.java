package lab1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class DataFrame implements groupby{
    public ArrayList<Column> df = new ArrayList<Column>();
    public String groupelement;


    public DataFrame(String[] names, String[] types){
        for (int i=0; i<names.length; i++)
        {
            df.add(new Column(names[i],types[i]));
        }
    }
    
    public DataFrame(String file,String[] types,boolean header) throws Exception{
    	FileInputStream fstream = new FileInputStream(file);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	String strLine;
    	
    	if(header==true) {
    		String pierwszaLinia = br.readLine();
    		String[] nazwy = pierwszaLinia.split(",");
    		for (int i=0; i<nazwy.length; i++)
            {
                df.add(new Column(nazwy[i],types[i]));
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
    public DataFrame(String file, String[] nazwy,Value[] typy) throws IOException {
    	FileInputStream fstream = new FileInputStream(file);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	String strLine;
    	String pierwszaLinia = br.readLine();
    	for (int i=0; i<nazwy.length; i++)
        {
            df.add(new Column(nazwy[i],typy[i]));
        }
    	while ((strLine = br.readLine()) != null){
    		String[] values = strLine.split(",");
    		for(int j=0;j<nazwy.length;j++) {
    			if(typy[j].getInstance() instanceof Integer) {
    				Integer addelement = new Integer();
    				addelement.create(values[j]);
    				df.get(j).addElement(addelement);
    			}
    			if(typy[j].getInstance() instanceof Double) {
    				Double addelement = new Double();
    				addelement.create(values[j]);
    				df.get(j).addElement(addelement);
    			}
    			if(typy[j].getInstance() instanceof Float) {
    				Float addelement = new Float();
    				addelement.create(values[j]);
    				df.get(j).addElement(addelement);
    			}
    			if(typy[j].getInstance() instanceof SValue) {
    				SValue addelement = new SValue();
    				addelement.create(values[j]);
    				df.get(j).addElement(addelement);
    			}
    			
    		}
    		
    	}
    	br.close();
    }
    
    
    
    public DataFrame(String file,Value[] types,boolean header) throws Exception{
    	FileInputStream fstream = new FileInputStream(file);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	String strLine;
    	
    	if(header==true) {
    		String pierwszaLinia = br.readLine();
    		String[] nazwy = pierwszaLinia.split(",");
    		for (int i=0; i<nazwy.length; i++)
            {
                df.add(new Column(nazwy[i],types[i]));
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
    
    
    public DataFrame(Column[] Kolumny){
        for (int i=0; i<Kolumny.length; i++) {
            df.add(Kolumny[i]);
        }
    }
    public DataFrame(DataFrame dataf) {
    }
    
    public int size(){
        return this.df.get(0).getColumnSize();
    } 
    
  

     public  Column get(String colname){
        for (Column col:df){
            if (col.getName()==colname){
            	return col;
            }
        }
        throw new RuntimeException("Kol not found!");
        
    }
    public Column get(int i) {
    	return df.get(i);
    }

    public DataFrame get(String [] cols, boolean copy){
        Column[] tmp = new Column[cols.length];
        for (int i=0; i<tmp.length; i++){
           
            if (copy){ 
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
        int i = 0;
        for (Column col:df){
            col.list.add(tmp[i++]);
        }
    }
    
    public void add(Value[] vals,int col){
    	//dodawanie obiektow z list do columny o wskazanym indeksie
    	for(int i=0;i<vals.length;i++) {
    			df.get(col).list.add(vals[i]);
    		}
    		
    	}
        
    public DataFrame iloc(int a, int b){
        
        int size = df.size();
        String[] types = new String[size];
        String[] names = new String[size];
        for (int i=0; i<size; i++){
            types[i] = df.get(i).getType();
            names[i] = df.get(i).getName();
        }
        DataFrame Nowadf = new DataFrame(names,types);
        Value[] tmp = new Value[df.size()];
        for (int i=a; i<=b; i++){
            for (int j=0; j<tmp.length; j++){
        
                tmp[j] = df.get(j).list.get(i);
            }
            Nowadf.add(tmp);

        }
        return Nowadf;
    }
    
    public DataFrame iloc(int i){
        return iloc(i,i);
    }
    public ArrayList<Column> toList(){
        return df;
    }
    void setGroupElement(String v){
        groupelement=v;
    }

    
    public void print(){
        for (int i=0; i<size(); i++){
            for (Column col:df){
                System.out.print(col.list.get(i)+"   ");
            }
            System.out.println();
        }
    }
    
    
    public DataFrame groupby(String id){
        Column[] Cols = new Column[size()];
        int it=0;
        for(Column k: toList()){
            Cols[it]=new Column(k);
        }
        DataFrame returndf = new DataFrame(Cols);
        returndf.setGroupElement(id);
        return returndf;
    }
    
    public DataFrame max() {
        Value max = toList().get(0).list.get(0);
        Column[] NewKols = new Column[toList().size()];
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		max = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(max);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                max = toList().get(it).list.get(0);
                for (int i = 0; i < k.getColumnSize(); i++) {
                    if (k.list.get(i).gte(max)) {
                        max = k.list.get(i);
                    }
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].list.add(max);
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
        Value min = toList().get(0).list.get(0);
        Column[] NewKols = new Column[toList().size()];
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		min = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(min);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                min = toList().get(it).list.get(0);
                for (int i = 0; i < k.getColumnSize(); i++) {
                    if (k.list.get(i).lte(min)) {
                        min = k.list.get(i);
                    }
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].list.add(min);
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
        Column[] NewKols = new Column[toList().size()];
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		mean = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(mean);
                it++;
        	} 
        	else {
	        	if(groupelement==null || groupelement!=k.getName()) {
	            	
	                mean = toList().get(it).list.get(0);
	                for (int i = 1; i < k.getColumnSize(); i++) {
	                    mean = mean.add(k.list.get(i));
	                }
	                mean = mean.div(new Double(k.getColumnSize()));
	                NewKols[it] = new Column(k.getName(), k.getType());
	                NewKols[it].list.add(mean);
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
        Column[] NewKols = new Column[toList().size()];
        
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		Returnv = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(Returnv);
                it++;
        	}
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
            	
            	
                for(int i=0;i<k.getColumnSize();i++){
                    Returnv =Returnv.add( (k.list.get(i).sub(newmean.toList().get(it).list.get(0))).pow(new Integer(2)));
                }
                Returnv=Returnv.div(v);
                Returnv=Returnv.pow(new Double(0.5));
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].list.add(Returnv);
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
        Column[] NewKols = new Column[toList().size()];
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		sum = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(sum);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                sum = toList().get(it).list.get(0);
                for (int i = 1; i < k.getColumnSize(); i++) {
                    sum = sum.add(k.list.get(i));
                }
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].list.add(sum);
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
        Column[] NewKols = new Column[toList().size()];
        
        int it=0;
        for(Column k: toList()){
        	if(k.getVType() instanceof SValue) {
        		Returnv = new SValue("___");
        		NewKols[it] = new Column(k.getName(), k.getType());
        		NewKols[it].list.add(Returnv);
                it++;
        	} 
        	else {
            if(groupelement==null || groupelement!=k.getName()) {
                for(int i=0;i<k.getColumnSize();i++){
                    Returnv =Returnv.add( k.list.get(i).sub(newmean.toList().get(it).list.get(0)));
                }
                Returnv=Returnv.div(v);
                NewKols[it] = new Column(k.getName(), k.getType());
                NewKols[it].list.add(Returnv);
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
        for(Column k: toList()){
            Cols[it]=new Column(k);
            it++;
        }
        DataFrame returndf = new DataFrame(Cols);
        operation.apply(returndf);
        for(int i=0;i<returndf.toList().size();i++){
            if(returndf.toList().get(i).getName()==groupelement){
                returndf.toList().set(i, new Column(this.toList().get(i)));

        }

    }
    return returndf;
    }

    
    
    //dodawanie, odejmowanie, mnozenie, dzielenie, potegowanie element�w columny przez wartosc
    public Column colvalmul(Column k,Value mulelement) {
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.list.get(i).mul(mulelement));
    	}
    	return tmp;
    }
    public Column colvaldiv(Column k,Value divelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.list.get(i).div(divelement));
    	}
    	return tmp;
    }
    public Column colvaladd(Column k,Value addelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.list.get(i).add(addelement));
    	}
    	return tmp;
    }
    public Column colvalsub(Column k,Value subelement) throws RuntimeException{
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.list.get(i).sub(subelement));
    	}
    	return tmp;
    }
    public Column colvalpow(Column k,Value powelement)throws RuntimeException {
    	Column tmp = new Column(k.getName(),k.getType());
    	for(int i=0;i< k.getColumnSize();i++) {
    		tmp.addElement(k.list.get(i).pow(powelement));
    	}
    	return tmp;
    }
    public Column colcoladd(Column k,Column addelement)throws RuntimeException {
    	if(k.getColumnSize()==addelement.getColumnSize()) {
    		Column tmp = new Column(k.getName(),k.getType());
        	for(int i=0;i< k.getColumnSize();i++) {
        		tmp.addElement(k.list.get(i).add(addelement.list.get(i)));
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
        		tmp.addElement(k.list.get(i).sub(subelement.list.get(i)));
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
        		tmp.addElement(k.list.get(i).mul(element.list.get(i)));
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
            		tmp.addElement(k.list.get(i).div(element.list.get(i)));
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
            		tmp.addElement(k.list.get(i).pow(element.list.get(i)));
            	}
            	return tmp;
        		
        	}
        	else {
        		throw new RuntimeException("different sizes of cols");
        	}
    	
    }
}