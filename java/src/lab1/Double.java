package lab1;

import java.util.Objects;

public class Double extends Value {
    private double value;
    private static Double aDouble = new Double();

    public static Double getInstance(){
        return aDouble;
    }

    Double(){};

    public Double(final double d){
        value = d;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return java.lang.Double.toString(value);
    }


    @Override
    public Value add(Value value) throws NotInstanceOf {
        if (value instanceof Integer){
            this.value += ((Integer) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }
    }

    @Override
    public Value sub(Value value)throws NotInstanceOf {
        if (value instanceof Integer){
            this.value -= ((Integer) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }

    }

    @Override
    public Value mul(Value value) throws NotInstanceOf{
        if (value instanceof Integer){
            this.value *= ((Integer) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }

    }

    @Override
    public Value div(Value value) throws NotInstanceOf{
        if (value instanceof Integer){
            this.value /= ((Integer) value).getValue();
            return this;
        }
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }

    }

    @Override
    public Value pow(Value value) throws NotInstanceOf{
        if (value instanceof Integer){
            this.value = (int)Math.pow((double)this.value,(double)((Integer) value).getValue());
            return this;
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }

    }

    
	@Override
    public boolean eq(Value value) throws NotInstanceOf{
        if (value instanceof Integer){
            return Objects.equals(this.value, ((Integer) value).getValue());
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }
    }

    @Override
    public boolean lte(Value value)throws NotInstanceOf {
        if (value instanceof Integer) {
            return this.value <= ((Integer) value).getValue();
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }
    }

    @Override
    public boolean gte(Value value) throws NotInstanceOf{
        if (value instanceof Integer) {
            return this.value >= ((Integer) value).getValue();
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }
    }

    @Override
    public boolean neq(Value value)throws NotInstanceOf {
        if (value instanceof Integer){
            return !Objects.equals(this.value, ((Integer) value).getValue());
        }
        
        else {
        	throw new NotInstanceOf("Invalid Value type(not instance of Integer)", value);
        }
    }

    @Override
    public boolean equals(Object other) {
    	if(other instanceof Double) {
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
        value = java.lang.Integer.parseInt(s);
        return this;
    }
    @Override
    public Object GetValue() {
        return this.value;
    }
    @Override
    protected Value clone(){
        return new Double(value);
    }
}
