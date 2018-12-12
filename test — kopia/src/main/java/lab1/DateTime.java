package lab1;

import java.time.LocalDateTime;


public class DateTime extends Value{
    private LocalDateTime val;

    public DateTime() {}

    private DateTime(String value) {
        val = LocalDateTime.parse(value);
    }
    
    public DateTime(LocalDateTime value) {
        val = value;
    }


    public LocalDateTime getValue() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public DateTime create(String value) {
        return new DateTime(value);
    }



    @Override
    public DateTime add(Value v) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    @Override
    public DateTime sub(Value v) throws RuntimeException {
        throw new RuntimeException();
    }


    @Override
    public DateTime mul(Value v) throws RuntimeException {
        throw new RuntimeException();
    }


    @Override
    public DateTime div(Value v) throws RuntimeException {
        throw new RuntimeException();
    }


    @Override
    public DateTime pow(Value v) throws RuntimeException {
        throw new RuntimeException();
    }


    @Override
    public boolean eq(Value v) {
        if (v instanceof DateTime)
            return getValue().equals(((DateTime) v).getValue());
        else
            return false;
    }
    public boolean equals(Object other){
    	if(other instanceof DateTime) {
    		return true;
    	}
        else return false;
    }


    @Override
    public boolean lte(Value v)throws RuntimeException {
        if (v instanceof DateTime)
            return getValue().isBefore(((DateTime) v).getValue());
        else
            throw new RuntimeException();
    }


    @Override
    public boolean gte(Value v)throws RuntimeException {
        if (v instanceof DateTime)
            return getValue().isAfter(((DateTime) v).getValue());
        else
            throw new RuntimeException();
    }



    @Override
    public boolean neq(Value v) {
        return !eq(v);
    }
    @Override
    public int hashCode(){
        return this.val.hashCode();
    }
    public Object GetValue() {
        return this.val;
    }
    @Override
    protected Value clone(){
        return new DateTime(val);
    }
    @Override
	public String getType() {
		String type = "DateTime";
		return type;
	}
}
