package lab1;

import java.util.ArrayList;

public class Column {
	private Value vtype;
    private String type,name;
    int dfsize=0;
    public ArrayList<Value> list;
    
    public Column(String name, String type){
    	this.type = type;
        this.name = name;
        this.list = new ArrayList<Value>();
    }
    public Column(String name, Value type){
    	this.vtype = type;
    	this.type = type.getType();
        this.name = name;
        this.list = new ArrayList<Value>();
    }
    
    public Column() {
    	
    }
    public Column(Column column) {
    	this.type = new String(column.getType());
        this.name = new String(column.getName());
        this.list = new ArrayList<Value>(column.list);
    }
    public Column(String name, Class<? extends Value> type) {
    	this.type = type.getTypeName();
        this.name = name;
        this.list = new ArrayList<Value>();
	}
	public void addElement(Value v)throws RuntimeException {
    	
    		this.list.add(v);
    	
    }
    
    
    public int getColumnSize() {
        return list.size();
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
    	return list.get(i);
    }
     
    
     public int getDfsize() {
    	 return dfsize;
     }

	public void anotherOneHidelement() {
		dfsize++;
		
		
	}
       
}