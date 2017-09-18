package com.tiancom.pas.weixin.response;

/**
 * access token
 * backjson :{"access_token":"ACCESS_TOKEN","expires_in":7200}
 * @author luoxt
 *
 */
public class AccessToken {
	public String access_token;
	public String expires_in;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
