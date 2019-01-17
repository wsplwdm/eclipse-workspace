package lab1;

import java.util.Objects;

public class Float extends Value{
    private float value;
    private static Float aFloat = new Float();

    public Float getInstance(){
        return aFloat;
    }

    public Float(){};

    public Float(float newvalue){
        value = newvalue;
    }
    
    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return java.lang.Float.toString(value);
    }


    @Override
    public Value add(Value value) throws NotInstanceOf{
        if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float){
            this.value += java.lang.Float.parseFloat(value.GetValue().toString());
            return this;
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }
    }

    @Override
    public Value sub(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            this.value -= java.lang.Float.parseFloat(value.GetValue().toString());
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }

    }

    @Override
    public Value mul(Value value)throws NotInstanceOf {
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            this.value *= java.lang.Float.parseFloat(value.GetValue().toString());
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }

    }

    @Override
    public Value div(Value value) throws NotInstanceOf{
    		
        
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {

            this.value /= java.lang.Float.parseFloat(value.GetValue().toString());
            return this;
    	}
    	 else {
         	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
         }
    	

    }

    @Override
    public Value pow(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
    		
            this.value = (float) Math.pow((double)this.value,java.lang.Double.parseDouble(value.GetValue().toString()));
            return this;
    	}
    	else {
         	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
         }
    	

    }

    @Override
    public boolean eq(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            return Objects.equals(this.value, java.lang.Float.parseFloat(value.GetValue().toString()));
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }
    }

    @Override
    public boolean lte(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            return this.value <= java.lang.Float.parseFloat(value.GetValue().toString());
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        	
        }
    }

    @Override
    public boolean gte(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            return this.value >= java.lang.Float.parseFloat(value.GetValue().toString());
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }
    }

    @Override
    public boolean neq(Value value) throws NotInstanceOf{
    	if (value instanceof Integer||value instanceof lab1.Double||value instanceof lab1.Float) {
            return !Objects.equals(this.value, java.lang.Float.parseFloat(value.GetValue().toString()));
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
        }
    }

    @Override
    public boolean equals(Object other) {
    	if(other instanceof Float) {
    		return true;
    	}
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Value create(String s) {
        value = java.lang.Float.parseFloat(s);
        return this;
    }
    @Override
    public Object GetValue() {
        return this.value;
    }
    @Override
    protected Value clone(){
        return new Float(value);
        
    }
    @Override
	public String getType() {
		String type = "Float";
		return type;
	}
}