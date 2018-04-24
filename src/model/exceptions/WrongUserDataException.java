package model.exceptions;

public class WrongUserDataException extends Exception {

	@Override
	public String getMessage() {
		return "Wrong username or password";
	}
}
