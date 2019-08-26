package com.xj.httpclient.vo;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;

import com.xj.comm.Constants;
import com.xj.httpclient.utils.TokenUtils;

/**
 * Token 
 * 
 * @author Lynch 2014-09-15
 *
 */
public class Token {
	private String accessToken;
	private Long expiredAt;
	
	private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Constants.USER_ROLE_APPADMIN);

	public Token() {
	}

	public Token(String accessToken, Long expiredAt) {
		this.accessToken = accessToken;
		this.expiredAt = expiredAt;
	}

	public static void applyAuthentication(HttpEntityEnclosingRequestBase httpMethodEntity, Object credentail) {
		if(credentail instanceof Credential || "-1".equals(credentail)){
			Token token = credential.getToken();
			TokenUtils tokenUtils = new TokenUtils();
			
			tokenUtils.saveFileToken(token.accessToken, token.expiredAt);
			applyAuthentication(httpMethodEntity, token);
		}else{
			httpMethodEntity.addHeader("Authorization", "Bearer " + (String)credentail);
		}
		
		
	}
	public static void main(String[] args) {
		applyAuthentication(null, "-1");
	}
	public static void applyAuthentication(HttpEntityEnclosingRequestBase httpMethodEntity, Token token) {
		httpMethodEntity.addHeader("Authorization", "Bearer " + token.toString());
	}

	public static void applyAuthentication(HttpRequestBase httpMethodEntity, Object credentail) {
		if(credentail instanceof Credential || "-1".equals(credentail)){
			Token token = credential.getToken();
			TokenUtils tokenUtils = new TokenUtils();
			tokenUtils.saveFileToken(token.accessToken, token.expiredAt);
			applyAuthentication(httpMethodEntity, token);
		}else{
			httpMethodEntity.addHeader("Authorization", "Bearer " + (String)credentail);
		}
	}

	public static void applyAuthentication(HttpRequestBase httpMethodEntity, Token token) {
		httpMethodEntity.addHeader("Authorization", "Bearer " + token.toString());
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expiredAt;
	}
	
	@Override
	public String toString() {
		return accessToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((expiredAt == null) ? 0 : expiredAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (accessToken == null) {
			if (other.accessToken != null)
				return false;
		} else if (!accessToken.equals(other.accessToken))
			return false;
		if (expiredAt == null) {
			if (other.expiredAt != null)
				return false;
		} else if (!expiredAt.equals(other.expiredAt))
			return false;
		return true;
	}
}
