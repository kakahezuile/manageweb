package com.xj.httpclient.apidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.comm.Constants;
import com.xj.comm.HTTPMethod;
import com.xj.httpclient.utils.HTTPClientUtils;
import com.xj.httpclient.utils.TokenUtils;
import com.xj.httpclient.vo.ClientSecretCredential;
import com.xj.httpclient.vo.Credential;
import com.xj.httpclient.vo.EndPoints;


public class EasemobChatGroups {
	private static Logger LOGGER = LoggerFactory.getLogger(EasemobChatGroups.class);

	// 通过app的client_id和client_secret来获取app管理员token
	private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Constants.USER_ROLE_APPADMIN);
	
	private static JsonNodeFactory factory = JsonNodeFactory.instance;
	private static final String APPKEY = Constants.APPKEY;
	
	public static void main(String[] args) {
		System.out.println(getGroupList("1434353385596613").get("data").get(0).get("maxusers"));
	}
	
	private static Object getCredential(){
		TokenUtils tokenUtils = new TokenUtils();
    	String token = tokenUtils.getToken();
    	if("0".equals(token)){
    		LOGGER.info("token需要做首次请求操作");
    	}else if("-1".equals(token)){
    		LOGGER.info("token已超时并且被删除需重新请求");
    	}else{
    		return token;
    	}
    	return credential;
    }
    
    /**
	 * 创建群组
	 * 
	 */
	public static ObjectNode creatChatGroups(ObjectNode dataObjectNode) {
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		// check properties that must be provided
		if (!dataObjectNode.has("groupname")) {
			LOGGER.error("Property that named groupname must be provided .");
			objectNode.put("message", "Property that named groupname must be provided .");
			return objectNode;
		}
		if (!dataObjectNode.has("desc")) {
			LOGGER.error("Property that named desc must be provided .");
			objectNode.put("message", "Property that named desc must be provided .");
			return objectNode;
		}
		if (!dataObjectNode.has("public")) {
			LOGGER.error("Property that named public must be provided .");
			objectNode.put("message", "Property that named public must be provided .");
			return objectNode;
		}
		if (!dataObjectNode.has("approval")) {
			LOGGER.error("Property that named approval must be provided .");
			objectNode.put("message", "Property that named approval must be provided .");
			return objectNode;
		}
		if (!dataObjectNode.has("owner")) {
			LOGGER.error("Property that named owner must be provided .");
			objectNode.put("message", "Property that named owner must be provided .");
			return objectNode;
		}
		if (!dataObjectNode.has("members") || !dataObjectNode.path("members").isArray()) {
			LOGGER.error("Property that named members must be provided .");
			objectNode.put("message", "Property that named members must be provided .");
			return objectNode;
		}

		try {
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, thisCredential, dataObjectNode,
					HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 删除群组
	 */
	
	
	public static ObjectNode deleteGroup(String groupId){
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		if(groupId != null){
			objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"+groupId), thisCredential, factory.objectNode(),
					HTTPMethod.METHOD_DELETE);
		}
		return objectNode;
	}
	/**
	 * 获取群组信息
	 * @param groupId
	 * @return
	 */
	public static ObjectNode getGroupList(String groupId){
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		if(groupId != null){
			objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"+groupId), thisCredential, factory.objectNode(),
					HTTPMethod.METHOD_GET);
		}
		return objectNode;
	}
	
	/**
	 * 加入环信群组
	 */
	public static ObjectNode addGroup(String emobId , String groupId){
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		if(emobId != null){
			objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"+groupId+"/users/"+emobId), thisCredential, factory.objectNode(),
					HTTPMethod.METHOD_POST);
		}
		return objectNode;
	}
	
	/**
	 * 删除群成员
	 */
	public static ObjectNode deleteGroup(String emobId , String groupId){
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		if(emobId != null){
			objectNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"+groupId+"/users/"+emobId), thisCredential, factory.objectNode(),
					HTTPMethod.METHOD_DELETE);
		}
		return objectNode;
	}
	/**
	 * 修改群名称
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public static boolean updateGroup(String groupId , String groupName){
		boolean isTrue = false;
		Object thisCredential = getCredential();
		ObjectNode result = factory.objectNode();
		ObjectNode dataBody = factory.objectNode();
		dataBody.put("groupname", groupName);
		if(groupId != null){
			result = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"+groupId), thisCredential, dataBody, HTTPMethod.METHOD_PUT);
		}
		if(result != null && result.get("data") != null){
			JsonNode data = result.get("data").get("groupname");
			if(data != null && data.asBoolean()){
				isTrue = true;
			}
		}
		return isTrue;
	}

	
}
