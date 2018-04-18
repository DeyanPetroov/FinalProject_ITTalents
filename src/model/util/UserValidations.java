package model.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UserValidations {

	private static final int MIN_PASSWORD_LENGTH = 5;
	private static final String PHONE_PATTERN = "^08[7-9][0-9]{7}$";
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	private static final String NAME_PATTERN = "^[a-zA-Z '.-]{3,31}$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static final boolean isValidStr(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static boolean isValidPhone(String phone) {
		return phone.matches(PHONE_PATTERN);
	}
	
	public static boolean isValidUsername(String username) {
		return username.matches(USERNAME_PATTERN);
	}
	
	public static boolean isValidName(String name) {
		return name.matches(NAME_PATTERN);
	}
	
	public static boolean isValidEmail(String email) {
		return isValidStr(email) && email.matches(EMAIL_PATTERN);
	}
	
	public static boolean isValidPassword(String password) {
		return isValidStr(password) && password.length() >= MIN_PASSWORD_LENGTH;
	}
	
	public static boolean isValidURL(String url) throws IOException {
		if(!isValidStr(url)) {
			return false;
		}		
		File file = new File(url);
		if(file.exists() && file.isFile()) {
		    if (ImageIO.read(file) != null) {
			     return true;
			}
		}
		return false;
	}
}