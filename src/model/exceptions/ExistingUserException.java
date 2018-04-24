package model.exceptions;

public class ExistingUserException extends Exception {
	
	private String usedString;
	
	public ExistingUserException(String str) {
		this.usedString = str;
	}
	@Override
	public String getMessage() {
		return usedString + " is not available";
	}
}
