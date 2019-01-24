package lab1;

import java.util.ArrayList;

public class Column {
	private Value vtype;
    private String type,name;
    int dataFrameSize=0;
    public ArrayList<Value> listOfValues;
	
    
    public Column(String name, String type){
    	this.type = type;
        this.name = name;
        this.listOfValues = new ArrayList<Value>();
    }
    public Column(String name, Value type){
    	this.vtype = type;
    	this.type = type.getType();
        this.name = name;
        this.listOfValues = new ArrayList<Value>();
    }
    
    public Column() {
    	
    }
    public ArrayList<Value> getListOfValues(){
    	return listOfValues;
    }
    public Column(Column column) {
    	this.type = new String(column.getType());
        this.name = new String(column.getName());
        this.listOfValues = new ArrayList<Value>(column.listOfValues);
    }
    public Column(String name, Class<? extends Value> type) {
    	this.type = type.getTypeName();
        this.name = name;
        this.listOfValues = new ArrayList<Value>();
	}
	public void addElement(Value v)throws RuntimeException {
    	
    		this.listOfValues.add(v);
    	
    }
    
    
    public int getColumnSize() {
        return listOfValues.size();
    }

    public String getType(){
        return type;
    }
    public Value getVType(){
        return vtype;
    }
    

    public String getName(){
        return name;
    }
     public Value get(int i){
    	return listOfValues.get(i);
    }
     
    
     
     ////sparsedataframe
     public int getDfsize() {
    	 return dataFrameSize;
     }

	public void anotherOneHidelement() {
		dataFrameSize++;
		
		
	}
       
}