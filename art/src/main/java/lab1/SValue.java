package lab1;

public class SValue extends Value {
    public String value;
    
    public SValue(String newvalue){
        value = newvalue;
    }
    public SValue() {
		// TODO Auto-generated constructor stub
	}
	@Override
    public String toString() {
        return value;
    }

    @Override
    public Value add(Value v) {
        Value ReturnValue = new SValue(this.value+v.toString());
        return ReturnValue;
    }
  

    @Override
    public Value sub(Value v) {
        Value ReturnValue = new SValue(this.value.replace(v.toString(),""));
        return ReturnValue;
    }

    @Override
    public Value mul(Value v) {
        Value ReturnValue = new SValue(value);
        return ReturnValue;
    }

    @Override
    public Value div(Value v) {
        Value ReturnValue = new SValue(this.value);
        return ReturnValue;
    }

    @Override
    public Value pow(Value v) {
        Value ReturnValue = new SValue(value);
        return ReturnValue;
    }

    @Override
    public boolean eq(Value v) {
        boolean returnvalue = (value==v.toString());
        return returnvalue;
    }

    @Override
    public boolean lte(Value v) {
        boolean returnvalue = (value.length()<=v.toString().length());
        return returnvalue;
    }

    @Override
    public boolean gte(Value v) {
        boolean returnvalue = (value.length()>=v.toString().length());
        return returnvalue;
    }

    @Override
    public boolean neq(Value v) {
        boolean returnvalue = (value!=v.toString());
        return returnvalue;
    }

    @Override
    public boolean equals(Object other) {
    	if(other instanceof SValue) {
    		return true;
    	}
        else return false;
    }

    @Override
    public int hashCode() {
        int prime = 701;
        int result=0;
        result = prime*value.hashCode()+1337;
        return result;
    }

    @Override
    public Value create(String s) {
    	value = s;
        return this;
        
    }
    @Override
    protected Value clone(){
        return new SValue(value);
    }
    public void print(){
        System.out.print(this.value);
        System.out.print("\n");
    }
    
    public Object GetValue() {
        return this.value;
    }
    @Override
	public String getType() {
		String type = "SValue";
		return type;
	}
	@Override
	protected Value getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
    
}