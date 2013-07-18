package org.carracoo.congo.parsing;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.carracoo.json.JSON;
import org.jongo.bson.Bson;
import org.jongo.marshall.Marshaller;
import org.jongo.marshall.MarshallingException;


import java.util.List;
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
public class SeedQuery {

	private static final String  TOKEN   = "#";
	private static final Pattern PATTERN = Pattern.compile(TOKEN);

	public static DBObject create(String json, Object... args){
		return new BasicDBObject(createQuery(json, args));
	}

    private static Map createQuery(String query, Object... parameters) {
        if (parameters == null) {
            parameters = new Object[]{null};
        }
        if (parameters.length == 0) {
            return JSON.decode(query.getBytes());
        }
        return createQueryWithParameters(query, parameters);
    }

    private static Map createQueryWithParameters(String template, Object[] parameters) {
        String query = template;
        assertThatParamsCanBeBound(query, parameters);
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
                replacement = marshallParameter(parameter, true).toString();
            } catch (RuntimeException e) {
                String message = String.format("Unable to bind parameter: %s into query: %s", parameter, query);
                throw new IllegalArgumentException(message, e);
            }

            query = query.substring(0, tokenIndex) + replacement + query.substring(tokenIndex + TOKEN.length());
            tokenIndex += replacement.length();
        }
		System.out.println("Q "+query);
        return JSON.decode(query.getBytes());
    }

    private static String marshallParameter(Object parameter, boolean serializeBsonPrimitives) {
        try {
            return new String(JSON.encode(parameter));
        } catch (Exception e) {
            String message = String.format("Unable to marshall parameter: %s", parameter);
            throw new MarshallingException(message, e);
        }
    }

    private static void assertThatParamsCanBeBound(String template, Object[] parameters) {
        int nbTokens = countTokens(template);
        if (nbTokens != parameters.length) {
            String message = String.format("Unable to bind parameters into query: %s. Tokens and parameters numbers mismatch " +
                    "[tokens: %s / parameters:%s]", template, nbTokens, parameters.length);
            throw new IllegalArgumentException(message);
        }
    }

    private static int countTokens(String template) {
        int count = 0;
        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

}
