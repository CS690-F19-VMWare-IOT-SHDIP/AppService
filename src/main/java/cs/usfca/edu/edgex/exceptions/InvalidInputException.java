package cs.usfca.edu.edgex.exceptions;

public class InvalidInputException extends Exception{
	
	public InvalidInputException() {
		super("Input is Invalid. Please send correct input and retry");
	}
	
	public InvalidInputException(String msg) {
		super(msg);
	}
}
