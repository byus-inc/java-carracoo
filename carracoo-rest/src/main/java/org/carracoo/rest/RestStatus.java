package org.carracoo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public enum RestStatus {

	HTTP_OK                     (200,true,"Ok"),
	HTTP_CREATED                (201,true,"Created"),
	HTTP_ACCEPTED               (202,true,"Accepted"),
	HTTP_NOT_AUTHORITATIVE      (203,true,"Non-Authoritative Information"),
	HTTP_NO_CONTENT             (204,true,"No Content"),
	HTTP_RESET                  (205,true,"Reset Content"),
	HTTP_PARTIAL                (206,true,"Partial Content"),
	HTTP_MULT_CHOICE            (300,false,"Multiple Choices"),
	HTTP_MOVED_PERM             (301,false,"Moved Permanently"),
	HTTP_MOVED_TEMP             (302,false,"Temporary Redirect"),
	HTTP_SEE_OTHER              (303,false,"See Other"),
	HTTP_NOT_MODIFIED           (304,false,"Not Modified"),
	HTTP_USE_PROXY              (305,false,"Use Proxy"),
	HTTP_BAD_REQUEST            (400,false,"Bad Request"),
	HTTP_UNAUTHORIZED           (401,false,"Unauthorized"),
	HTTP_PAYMENT_REQUIRED       (402,false,"Payment Required"),
	HTTP_FORBIDDEN              (403,false,"Forbidden"),
	HTTP_NOT_FOUND              (404,false,"Not Found"),
	HTTP_BAD_METHOD             (405,false,"Method Not Allowed"),
	HTTP_NOT_ACCEPTABLE         (406,false,"Not Acceptable"),
	HTTP_PROXY_AUTH             (407,false,"Proxy Authentication Required"),
	HTTP_CLIENT_TIMEOUT         (408,false,"Request Time-Out"),
	HTTP_CONFLICT               (409,false,"Conflict"),
	HTTP_GONE                   (410,false,"Gone"),
	HTTP_LENGTH_REQUIRED        (411,false,"Length Required"),
	HTTP_PRECON_FAILED          (412,false,"Precondition Failed"),
	HTTP_ENTITY_TOO_LARGE       (413,false,"Request Entity Too Large"),
	HTTP_REQ_TOO_LONG           (414,false,"Request-URI Too Large"),
	HTTP_UNSUPPORTED_TYPE       (415,false,"Unsupported Media Type"),
	HTTP_SERVER_ERROR           (500,false,"Internal Server Error"),
	HTTP_NOT_IMPLEMENTED        (501,false,"Not Implemented"),
	HTTP_BAD_GATEWAY            (502,false,"Bad Gateway"),
	HTTP_UNAVAILABLE            (503,false,"Service Unavailable"),
	HTTP_GATEWAY_TIMEOUT        (504,false,"Gateway Timeout"),
	HTTP_VERSION                (505,false,"HTTP Version Not Supported"),
	CONNECTION_FAILED           (600,false,"Connection failed")
	;

	public final Integer code;
	public final Boolean success;
	public final String  message;

	RestStatus(Integer code,Boolean success,String message){
		this.code    = code;
		this.message = message;
		this.success = success;
	}

	public static RestStatus valueOf(int code){
		for(RestStatus status : RestStatus.values()){
			if(status.code.equals(code)){
				return status;
			}
		}
		throw new RuntimeException("Unsupported HTTP Status Code");
	}

}
