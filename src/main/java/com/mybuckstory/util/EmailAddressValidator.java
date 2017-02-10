package com.mybuckstory.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressValidator {

	private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
		
	private EmailAddressValidator(){}
	
	public static boolean isValid(String emailAddress){
		Matcher matcher = emailPattern.matcher(emailAddress);
		return matcher.matches();
	}
	
}
