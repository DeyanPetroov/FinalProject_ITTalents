package model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
	
	public String getCurrentDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		String formattedString = date.format(formatter);
		return formattedString;
	}
}
