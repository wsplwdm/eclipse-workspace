package lab1;

import valueTypes.Value;

public class NotInstanceOf extends RuntimeException{
	public NotInstanceOf(String message, Value v)
    {
       super(message);
    }
	public NotInstanceOf() {}

    // Constructor that accepts a message
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}