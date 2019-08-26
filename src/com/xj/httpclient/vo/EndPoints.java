package com.xj.httpclient.vo;



import java.net.URL;

import com.xj.comm.Constants;
import com.xj.httpclient.utils.HTTPClientUtils;

/**
 * HTTPClient EndPoints
 * 
 * @author Lynch 2014-09-15
 *
 */
public abstract class EndPoints {

	public static final URL ROOT_URL = HTTPClientUtils.getURL("");

	public static final URL MANAGEMENT_URL = HTTPClientUtils.getURL("/management");

	public static final URL TOKEN_ORG_URL = HTTPClientUtils.getURL("/management/token");

	public static final URL APPLICATION_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/"));

	public static final URL TOKEN_APP_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/token");

	public static final URL USERS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users");

	public static final URL MESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/messages");

	public static final URL CHATMESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
			+ "/chatmessages");

	public static final URL CHATGROUPS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups");

	public static final URL CHATFILES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles");
	
	public synchronized static URL getUserUpdate(){
		return USERS_UPDATE;
	}
	
	private static URL USERS_UPDATE = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+getUserName()+"/password");
	
	private static String USER_NAME = "";
	
	public synchronized static void setUserName(String userName){
		USER_NAME = userName;
	}
	
	public synchronized static String getUserName(){
		return USER_NAME;
	}

}
