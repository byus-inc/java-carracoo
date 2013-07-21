package org.carracoo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtils {
	public static final String EMPTY = "";

	public static String repeat(String str, Integer count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
			.append(Character.toTitleCase(str.charAt(0)))
			.append(str.substring(1))
		.toString();
	}

	public static String capitalizeSentance(String str) {
		String[] words = str.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String w : words) {
			sb.append(" ").append(capitalize(w));
		}
		return sb.toString().substring(1);
	}

	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
			.append(Character.toLowerCase(str.charAt(0)))
			.append(str.substring(1))
		.toString();
	}

	public static String toClassNameNotation(String str) {
		return capitalize(toVariableNotation(str));
	}

	public static String toVariableNotation(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '_') {
				ch = str.charAt(++i);
				result.append(Character.toUpperCase(ch));
			} else {
				result.append(Character.toLowerCase(ch));
			}
		}
		return result.toString();
	}

	public static String toUnderscoredNotation(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder result = new StringBuilder();
		result.append(Character.toLowerCase(str.charAt(0)));
		for (int i = 1; i < str.length(); i++) {
			char ch = str.charAt(i);
			switch (Character.getType(ch)) {
				case Character.DECIMAL_DIGIT_NUMBER:
				case Character.UPPERCASE_LETTER:
					if (str.charAt(i - 1) != '_') {
						result.append('_');
					}
					result.append(Character.toLowerCase(ch));
					break;
				default:
					result.append(Character.toLowerCase(ch));
			}
		}
		return result.toString();
	}


	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}


	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String trim(String str) {
		return str.replaceAll("\\s+", " ").trim();
	}
	
	public static String slug(String str) {
		return Slug.toSlug(str);
	}

	public static String unescapeString( String input ){
		String result = "";
		int backslashIndex = 0;
		int nextSubstringStartPosition = 0;
		int len = input.length();
		do{
			// Find the next backslash in the input
			backslashIndex = input.indexOf( '\\', nextSubstringStartPosition );
			if ( backslashIndex >= 0 ){
				result += input.substring( nextSubstringStartPosition, backslashIndex );
				// Move past the backslash and next character (all escape sequences are
				// two characters, except for \\u, which will advance this further)
				nextSubstringStartPosition = backslashIndex + 2;
				// Check the next character so we know what to escape
				char escapedChar = input.charAt( backslashIndex + 1 );
				switch ( escapedChar ){
					// Try to list the most common expected cases first to improve performance
					case '"':
						result += escapedChar;
						break; // quotation mark
					case '\\':
						result += escapedChar;
						break; // reverse solidus
					case 'n':
						result += '\n';
						break; // newline
					case 'r':
						result += '\r';
						break; // carriage return
					case 't':
						result += '\t';
					break; // horizontal tab
					// Convert a unicode escape sequence to it's character value
					case 'u':
						// Save the characters as a string we'll struct to an int
						String hexValue = "";
						int unicodeEndPosition = nextSubstringStartPosition + 4;
						// Make sure there are enough characters in the string leftover
						if ( unicodeEndPosition > len ){
							new RuntimeException("Unexpected end of input.  Expecting 4 hex digits after \\u." );
						}
						// Try to find 4 hex characters
						for ( int i = nextSubstringStartPosition; i < unicodeEndPosition; i++ )	{
							// get the next character and determine
							// if it's a valid hex digit or not
							char possibleHexChar = input.charAt( i );
							if ( !isHexDigit( possibleHexChar ) ){
								new RuntimeException( "Excepted a hex digit, but found: " + possibleHexChar );
							}
							// Valid hex digit, add it to the value
							hexValue += possibleHexChar;
						}
						// Convert hexValue to an integer, and use that
						// integer value to create a character to add
						// to our string.
						result += String.valueOf((char)Integer.parseInt(hexValue, 16 ));
						// Move past the 4 hex digits that we just read
						nextSubstringStartPosition = unicodeEndPosition;
					break;
					case 'f':
						result += '\f';
					break; // form feed
					case '/':
						result += '/';
					break; // solidus
					case 'b':
						result += '\b';
					break; // bell
					default:
						result += '\\' + escapedChar; // Couldn't unescape the sequence, so just pass it through
				}
			}else{
				result += input.substring( nextSubstringStartPosition );
				break;
			}
		} while ( nextSubstringStartPosition < len );
		return result;
	}

	public static String urlEncode(String val){
		try{
			return URLEncoder.encode(val, "UTF-8").replaceAll("\\+","%20");
		}catch (UnsupportedEncodingException ex){
			return val;
		}
	}
	public static String urlDecode(String val){
		try{
			return URLDecoder.decode(val, "UTF-8");
		}catch (UnsupportedEncodingException ex){
			return val;
		}
	}
	private static Boolean isDigit( char ch){
		return ( ch >= '0' && ch <= '9' );
	}

	private static Boolean isHexDigit( char ch ) {
		return ( isDigit( ch ) || ( ch >= 'A' && ch <= 'F' ) || ( ch >= 'a' && ch <= 'f' ) );
	}

}
