package security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputControl {

	//public static final String specialChars = "[`~!@#$%^&*()_+[\\]\\\\;\',./{}|:\"<>?]";
	
	/**
	 * Checks if the string contains any of the illegal chars
	 * @param s String from input field in application
	 * @return True/False
	 */
	public static boolean ValidateInput(String s){
		if(s != null){
			Pattern p = Pattern.compile("[$%*`;'<>]");
			Matcher m = p.matcher(s);
			return m.find();
		}else{
			return false;
		}
		
	}
}
