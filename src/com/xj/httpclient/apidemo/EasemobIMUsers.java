package com.xj.httpclient.apidemo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.comm.Constants;
import com.xj.comm.HTTPMethod;
import com.xj.httpclient.utils.HTTPClientUtils;
import com.xj.httpclient.utils.TokenUtils;
import com.xj.httpclient.vo.ClientSecretCredential;
import com.xj.httpclient.vo.Credential;
import com.xj.httpclient.vo.EndPoints;

/**
 * REST API Demo :用户体系集成 REST API HttpClient4.3实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/userapi
 * 
 * @author Lynch 2014-09-15
 * 
 */
public class EasemobIMUsers {

	private static Logger LOGGER = Logger.getLogger(EasemobIMUsers.class);
	private static JsonNodeFactory factory = JsonNodeFactory.instance;

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Constants.USER_ROLE_APPADMIN);

    public static ObjectNode addUser(String userName , String passWord){
   
		ObjectNode objectNode = factory.objectNode();

		objectNode.put("username",userName);
		objectNode.put("password", passWord);
		objectNode = createNewIMUserSingle(objectNode);
		
        /**
         * 注册IM用户[单个]
         */
//        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
//        if (null != createNewIMUserSingleNode) {
//            LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
//        }


//        /**
//         * IM用户登录
//         */
//        ObjectNode imUserLoginNode = imUserLogin(datanode.get("username").asText(), datanode.get("password").asText());
//        LOGGER.info("imUserLoginNode is :"+imUserLoginNode);
//        if (null != imUserLoginNode) {
//            LOGGER.info("IM用户登录: " + imUserLoginNode.toString());
//        }
		return objectNode;
    }
    private static Object getCredential(){
    	TokenUtils tokenUtils = new TokenUtils();
    	String token = tokenUtils.getToken();
    	if("0".equals(token)){
    		LOGGER.info("token需要做首次请求操作");
    	}else if("-1".equals(token)){
    		LOGGER.info("token已超时并且被删除需重新请求");
    		return "-1";
    	}else{
    		return token;
    	}
    	return credential;
    }

    /**
	 * 注册IM用户[单个]
	 * 
	 * 给指定Constants.APPKEY创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {
		Object thisCredential = getCredential();

        LOGGER.info("createNewIMUserSingle");
		ObjectNode objectNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Constants.APPKEY");

			return objectNode;
		}

		objectNode.removeAll();

		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			LOGGER.error("Property that named username must be provided .");

			objectNode.put("message", "Property that named username must be provided .");

			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			LOGGER.error("Property that named password must be provided .");

			objectNode.put("message", "Property that named password must be provided .");

			return objectNode;
		}

		try {

		    objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, thisCredential, dataNode,
                    HTTPMethod.METHOD_POST);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(objectNode + "---------------------注册");
		return objectNode;
	}

    /**
     * IM用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public static ObjectNode imUserLogin(String username, String password) {
    	Object thisCredential = getCredential();
        LOGGER.info("imUserLogin");
        ObjectNode objectNode = factory.objectNode();

        // check appKey format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Appkey: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Appkey");

            return objectNode;
        }
        if (StringUtils.isEmpty(username)) {
            LOGGER.error("Your username must be provided，the value is username or uuid of imuser.");

            objectNode.put("message", "Your username must be provided，the value is username or uuid of imuser.");

            return objectNode;
        }
        if (StringUtils.isEmpty(password)) {
            LOGGER.error("Your password must be provided，the value is username or uuid of imuser.");

            objectNode.put("message", "Your password must be provided，the value is username or uuid of imuser.");

            return objectNode;
        }

        try {
            ObjectNode dataNode = factory.objectNode();
            dataNode.put("grant_type", "password");
            dataNode.put("username", username);
            dataNode.put("password", password);

            objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.TOKEN_APP_URL, thisCredential, dataNode,
                    HTTPMethod.METHOD_POST);

        } catch (Exception e) {
            throw new RuntimeException("Some errors ocuured while fetching a token by usename and passowrd .");
        }

        return objectNode;
    }
    
    public static ObjectNode addBlack(String emobIdFrom , String emobIdTo){
    	Object thisCredential = getCredential();
    	ObjectNode objectNode = factory.objectNode();
    	ObjectNode dataNode = factory.objectNode();
    	ArrayNode arrayNode = new ArrayNode(factory);
    	arrayNode.add(emobIdTo);
//    	List<String> list = new ArrayList<String>();
//    	list.add(emobIdTo);
    	dataNode.put("usernames", arrayNode);
    	objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+emobIdFrom+"/blocks/users"), thisCredential, dataNode, HTTPMethod.METHOD_POST);
    	return objectNode;
    }
    
    public static ObjectNode deleteBlack(String emobIdFrom , String emobIdTo){
    	Object thisCredential = getCredential();
    	ObjectNode objectNode = factory.objectNode();
//    	ObjectNode dataNode = factory.objectNode();
    	List<String> list = new ArrayList<String>();
    	list.add(emobIdTo);
//    	dataNode.put("usernames", list.toArray().toString());
    	objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+emobIdFrom+"/blocks/users/"+emobIdTo), thisCredential, objectNode, HTTPMethod.METHOD_DELETE);
    	return objectNode;
    }
    
    // 修改用户密码
    public static ObjectNode updateUserPass(String userName , String passWord){
    	Object thisCredential = getCredential();
        LOGGER.info("update");
        ObjectNode objectNode = factory.objectNode();
        
        // check appKey format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Appkey: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Appkey");

            return objectNode;
        }
        
        if (StringUtils.isEmpty(userName)) {
            LOGGER.error("Your username must be provided，the value is username or uuid of imuser.");

            objectNode.put("message", "Your username must be provided，the value is username or uuid of imuser.");

            return objectNode;
        }
        if (StringUtils.isEmpty(passWord)) {
            LOGGER.error("Your password must be provided，the value is username or uuid of imuser.");

            objectNode.put("message", "Your password must be provided，the value is username or uuid of imuser.");

            return objectNode;
        }
        
        try {
            ObjectNode dataNode = factory.objectNode();
      
            dataNode.put("newpassword", passWord);
            
//            EndPoints.setUserName(userName);
            objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+userName+"/password"), thisCredential, dataNode,
                    HTTPMethod.METHOD_PUT);

        } catch (Exception e) {
            throw new RuntimeException("Some errors ocuured while fetching a token by usename and passowrd .");
        }
        
        return objectNode;
    }
    
    public static ObjectNode getIMUser(String username){
    	Object thisCredential = getCredential();
    	 ObjectNode objectNode = factory.objectNode();
    	 objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+username), thisCredential, objectNode, HTTPMethod.METHOD_GET);
    	 return objectNode;
    }
    
    public synchronized static ObjectNode updateIMUser(String username , String nickname){
    	Object thisCredential = getCredential(); 
   	 	ObjectNode objectNode = factory.objectNode();
   	 	objectNode.put("nickname", nickname);
   	 	objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+username), thisCredential, objectNode, HTTPMethod.METHOD_PUT);
   	 	return objectNode;
    }
    
    public static ObjectNode getIMuserMessageCount(String username){
    	Object thisCredential = getCredential();
   	 	ObjectNode objectNode = factory.objectNode();
   	 	objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+username+"/offline_msg_count"), thisCredential, objectNode, HTTPMethod.METHOD_GET);
   	 	return objectNode;
    }
  
    /**
     * 根据用户名判断 当前用户是否在线
     * @param username
     * @return
     */
    public static ObjectNode getIMuserIsOnline(String username){
    	Object thisCredential = getCredential();
   	 	ObjectNode objectNode = factory.objectNode();
   	 	objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+username+"/status"), thisCredential, objectNode, HTTPMethod.METHOD_GET);
   	 	return objectNode;
    }
    
    public static void main(String[] args) {
    	ObjectNode objectNode = getIMuserIsOnline("26bfcb05734e46bc1b8999534a0c4a27");
    	System.out.println(objectNode);
    	
    }
    
    public static String getAminEmobId(){
    	return "85317d8dec832c56171a0d039b813861";
    }
}
