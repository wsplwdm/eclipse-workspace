package valueTypes;

import java.util.Objects;

import lab1.NotInstanceOf;

public class Integer extends Value{
        private int value;
        private static Integer integer = new Integer();

        public Integer getInstance(){
            return integer;
        }

        public Integer(){};

        public Integer(final int newvalue){
            value = newvalue;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return java.lang.Float.toString(value);
        }


        @Override
        public Value add(Value value) {
        	if (value instanceof Integer||value instanceof valueTypes.Double||value instanceof valueTypes.Float) {
                this.value += java.lang.Double.parseDouble(value.GetValue().toString());
             
                 return this;
             }
             else {
             	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
             }
        }

        @Override
        public Value sub(Value value) throws NotInstanceOf{
            if (value instanceof valueTypes.Integer||value instanceof valueTypes.Double||value instanceof valueTypes.Float){
                this.value -= ((Integer) value).getValue();
                return this;
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }

        }

        @Override
        public Value mul(Value value) throws NotInstanceOf{
            if (value instanceof valueTypes.Integer||value instanceof valueTypes.Double||value instanceof valueTypes.Float){
                this.value *= ((Double) value).getValue();
                
                return this;
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }

        }

        @Override
        public Value div(Value value) throws NotInstanceOf{
        	
            if (value instanceof valueTypes.Integer||value instanceof valueTypes.Double||value instanceof valueTypes.Float){
                this.value /= java.lang.Double.parseDouble(value.GetValue().toString());
                
                return this;
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }
        }

        @Override
        public Value pow(Value value) throws NotInstanceOf{
            if (value instanceof SValue){
            	throw new NotInstanceOf("Invalid Value type(try numeric insteadof SValue)", value);
                
            }
            else {
            	try {
	            	this.value = (int)Math.pow((double)this.value,(double)((Integer) value).getValue());
	                return this;
            	}
            	catch(ClassCastException e) {
            		print("Double cannot be cast to Integer");
            		
            	}
            }
            return this;
            

        }

        private void print(String string) {
			// TODO Auto-generated method stub
			
		}

		@Override
        public boolean eq(Value value) throws NotInstanceOf{
            if (value instanceof Integer){
                return Objects.equals(this.value, ((Integer) value).getValue());
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }
        }

        @Override
        public boolean lte(Value value) throws NotInstanceOf{
            if (value instanceof Integer) {
                return this.value <= ((Integer) value).getValue();
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }
            
        }

        @Override
        public boolean gte(Value value) throws NotInstanceOf{
            if (value instanceof Integer) {
                return this.value >= ((Integer) value).getValue();
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }
        }

        @Override
        public boolean neq(Value value) throws NotInstanceOf{
            if (value instanceof Integer){
                return !Objects.equals(this.value, ((Integer) value).getValue());
            }
            else {
            	throw new NotInstanceOf("Invalid Value type(not instance of numeric)", value);
            }
        }

        @Override
        public boolean equals(Object other) {
        	if(other instanceof Integer) {
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
    		return new Integer(value);
    	}

		@Override
		public String getType() {
			String type = "Integer";
			return type;
		}

}