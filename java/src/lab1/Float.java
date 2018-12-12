package lab1;

import java.util.Objects;

public class Float extends Value{
    private float value;
    private static Float aFloat = new Float();

    public static Float getInstance(){
        return aFloat;
    }

    Float(){};

    Float(float newvalue){
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
        if (value instanceof Float){
            this.value += ((Float) value).getValue();
            return this;
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }
    }

    @Override
    public Value sub(Value value) throws NotInstanceOf{
        if (value instanceof Float){
            this.value -= ((Float) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }

    }

    @Override
    public Value mul(Value value)throws NotInstanceOf {
        if (value instanceof Float){
            this.value *= ((Float) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }

    }

    @Override
    public Value div(Value value) throws NotInstanceOf{
    		
        
        	
            this.value /= ((Integer) value).getValue();
            return this;
        

    }

    @Override
    public Value pow(Value value) throws NotInstanceOf{
      
            this.value = (float)Math.pow((double)this.value,(double)((Integer) value).getValue());
            return this;
        

    }

    @Override
    public boolean eq(Value value) throws NotInstanceOf{
        if (value instanceof Float){
            return Objects.equals(this.value, ((Float) value).getValue());
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }
    }

    @Override
    public boolean lte(Value value) throws NotInstanceOf{
        if (value instanceof Float) {
            return this.value <= ((Float) value).getValue();
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }
    }

    @Override
    public boolean gte(Value value) throws NotInstanceOf{
        if (value instanceof Float) {
            return this.value >= ((Float) value).getValue();
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
        }
    }

    @Override
    public boolean neq(Value value) throws NotInstanceOf{
        if (value instanceof Float){
            return !Objects.equals(this.value, ((Float) value).getValue());
            
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of integer)", value);
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
}