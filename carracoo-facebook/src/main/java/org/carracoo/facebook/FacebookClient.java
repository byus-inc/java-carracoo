package org.carracoo.facebook;

import org.carracoo.json.JSON;
import org.carracoo.rest.RestException;
import org.carracoo.rest.RestExecutor;
import org.carracoo.rest.RestRequest;
import org.carracoo.rest.http.RestHttpRequest;
import org.carracoo.rest.http.RestHttpResponse;
import org.carracoo.rest.http.RestHttpService;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookClient extends RestHttpService {
	public static final String GRAPH_ENDPOINT   = "https://graph.facebook.com";
	public static final String OAUTH_ENDPOINT   = "https://www.facebook.com/dialog/oauth";

	final protected String graphUrl;
	final protected String oauthUrl;
	final protected String appId;
	final protected String appSecret;
	final protected String appPermissions;
	final protected String appRedirectUrl;
	final protected String appAccessToken;

	public FacebookClient (
		String appId,
		String appSecret,
		String appPermissions,
		String appRedirectUrl
	)throws FacebookException {
		this(GRAPH_ENDPOINT,OAUTH_ENDPOINT,appId,appSecret,appPermissions,appRedirectUrl);
	}

	public FacebookClient (
		String graphUrl,
		String oauthUrl,
		String appId,
		String appSecret,
		String appPermissions,
		String appRedirectUrl
	)throws FacebookException {
		this.graphUrl       = graphUrl;
		this.oauthUrl       = oauthUrl;
		this.appId          = appId;
		this.appSecret      = appSecret;
		this.appPermissions = appPermissions;
		this.appRedirectUrl = appRedirectUrl;
		this.appAccessToken = getAppAccessToken();
	}

	private final String getAppAccessToken() throws FacebookException {
		try {
			return (String) get("/oauth/access_token")
				.param("client_id", appId)
				.param("client_secret", appSecret)
				.param("grant_type", "client_credentials")
			.toMap().get("access_token");
		}catch(RestException ex){
			throw new FacebookException("app access token call failed",ex);
		}
	}


	@Override
	public synchronized FacebookRequest get(String path) {
		return new FacebookRequest(this)
			.method("GET")
			.action(graphUrl + path);
	}

	@Override
	public synchronized RestExecutor executor(RestRequest request) {
		return new FacebookExecutor(this,request);
	}


	public List<Object> getTestAccounts() throws FacebookException {
		try {
			Map result = get("/%app_id/accounts/test-users")
				.param("%app_id", appId)
				.param("access_token", appAccessToken)
			.toMap();
			if(result.containsKey("data") && result.get("data") instanceof List){
				return (List<Object>) result.get("data");
			}else {
				throw new FacebookException("invalid result for test accounts call\n"+ JSON.encode(result));
			}
		}catch (RestException ex){
			throw new FacebookException("get test accounts call failed",ex);
		}
	}

	public String getLoginUrl() {
		return String.format(
			"%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=token%%20code",
			oauthUrl,appId,appRedirectUrl,appPermissions
		);
	}
}
