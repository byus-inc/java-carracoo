package org.carracoo.beans.utils;

import org.carracoo.json.JSON;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/5/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Query {

	private static final String  TOKEN   = "#";
	private static final Pattern PATTERN = Pattern.compile(TOKEN);

	public static final Query NONE = new Query(null);

	public static Query get(Object query, Object... params) {
		return new Query(query, params);
	}

	private final Object value;
	private final Object[] params;

	private Query(Object value, Object... params) {
		this.value = value;
		this.params = params;
	}

	public Object getValue() {
		return value;
	}

	public Object[] getParams() {
		return params;
	}

	public String asString() {
		if (value == null) {
			return null;
		}
		return createStringQueryWithParameters(getStringValue(), params);
	}

	public String asString(Object... params) {
		return createStringQueryWithParameters(getStringValue(), params);
	}

	public Map asMap() {
		if (value == null) {
			return null;
		}
		if (value instanceof Map && (params == null || params.length == 0)) {
			return (Map)value;
		}
		return createMapQueryWithParameters(getStringValue(), params);
	}

	public Map asMap(Object... params) {
		return createMapQueryWithParameters(getStringValue(), params);
	}

	public Query clone() {
		return new Query(value, params);
	}

	public Query clone(Object... params) {
		return new Query(value, params);
	}

	private String getStringValue() {
		String str = null;
		if (value instanceof String) {
			str = (String) value;
		} else {
			str = JSON.encode(value);
		}
		return str;
	}

	private String createStringQueryWithParameters(String template, Object[] parameters) {
		String query = template;
		assertThatParamsCanBeBound(query, parameters);
		if (parameters.length == 0) {
			return query;
		}
		int paramIndex = 0;
		int tokenIndex = 0;
		while (true) {
			tokenIndex = query.indexOf(TOKEN, tokenIndex);
			if (tokenIndex < 0) {
				break;
			}

			Object parameter = parameters[paramIndex++];

			String replacement;
			try {
				replacement = marshallParameter(parameter);
			} catch (RuntimeException e) {
				String message = String.format("Unable to bind parameter: %s into query: %s", parameter, query);
				throw new IllegalArgumentException(message, e);
			}

			query = query.substring(0, tokenIndex) + replacement + query.substring(tokenIndex + TOKEN.length());
			tokenIndex += replacement.length();
		}
		System.out.println("Q "+query);
		return query;
	}

	private Map createMapQueryWithParameters(String template, Object[] parameters) {
		String query = createStringQueryWithParameters(template, parameters);
		return JSON.decode(query.getBytes());
	}

	private String marshallParameter(Object parameter) {
		try {
			return JSON.encode(parameter);
		} catch (Exception e) {
			String message = String.format("Unable to marshall parameter: %s", parameter);
			throw new RuntimeException(message, e);
		}
	}

	private void assertThatParamsCanBeBound(String template, Object[] parameters) {
		int nbTokens = countTokens(template);
		if (nbTokens != parameters.length) {
			String message = String.format("Unable to bind parameters into query: %s. Tokens and parameters numbers mismatch " +
				"[tokens: %s / parameters:%s]", template, nbTokens, parameters.length);
			throw new IllegalArgumentException(message);
		}
	}

	private int countTokens(String template) {
		int count = 0;
		Matcher matcher = PATTERN.matcher(template);
		while (matcher.find()) {
			count++;
		}
		return count;
	}

}
