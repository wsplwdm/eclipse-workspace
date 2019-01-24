package valueTypes;

public class COOValue extends Value{
	private int index;
	private Value value;
	
	public COOValue(int i, Value o){
		index = i;
		value = o;
	}
	
	public int getIndex(){
		return index;
	}
	public Value getValue() {
		return value;
	}
	public String toString() {
		return ""+index+"::"+value;
	}
	@Override
	public Value add(Value v) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Value sub(Value v) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Value mul(Value v) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Value div(Value v) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Value pow(Value v) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean eq(Value v) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean lte(Value v) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean gte(Value v) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean neq(Value v) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object GetValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Value create(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Value clone() {
		// TODO Auto-generated method stub
		return null;
		
	}
	@Override
	public String getType() {
		String type = "COOValue";
		return type;
	}

	@Override
	public Value getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}