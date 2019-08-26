package com.xj.httpclient.apidemo;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.bean.Admin;
import com.xj.bean.ExtNode;
import com.xj.bean.Messages;
import com.xj.bean.Users;
import com.xj.comm.Constants;
import com.xj.comm.HTTPMethod;
import com.xj.httpclient.utils.HTTPClientUtils;
import com.xj.httpclient.utils.TokenUtils;
import com.xj.httpclient.vo.ClientSecretCredential;
import com.xj.httpclient.vo.Credential;
import com.xj.httpclient.vo.EndPoints;

public class EasemobChatMessage {

	private static Logger LOGGER = LoggerFactory.getLogger(EasemobChatMessage.class);

	private static JsonNodeFactory factory = JsonNodeFactory.instance;

	private static final String APPKEY = Constants.APPKEY;

	// 通过app的client_id和client_secret来获取app管理员token
	private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Constants.USER_ROLE_APPADMIN);

	private static Object getCredential() {
		String token = new TokenUtils().getToken();
		if ("0".equals(token)) {
			LOGGER.info("token需要做首次请求操作");
		} else if ("-1".equals(token)) {
			LOGGER.info("token已超时并且被删除需重新请求");
		} else {
			return token;
		}
		
		return credential;
	}

	/**
	 * 获取聊天消息
	 * @param queryStrNode
	 */
	public static ObjectNode getChatMessages(Long minTime, Long maxTime, String from, String to) {
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {// check appKey format
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		
		try {
			URL chatMessagesUrl = null;
			if (minTime == null && maxTime == null && from == null && to == null) {
				chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatmessages");
			} else {
				if (minTime != null && maxTime != null) {
					if (from != null && to != null) {
						chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
							+ "/chatmessages?ql=select+*+where+from%3D%27"
							+ from + "%27+and+to%3D%27" + to
							+ "%27+and+timestamp%3C" + maxTime
							+ "+and+timestamp%3E" + minTime);
					} else {
						chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
							+ "/chatmessages?ql=select+*+where+timestamp%3C"
							+ maxTime + "+and+timestamp%3E"
							+ minTime);
					}
				} else {
					chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
							+ "/chatmessages?ql=select+*+where+from%3D%27"
							+ from + "%27+and+to%3D%27" + to + "%27");
				}
			}
			
			objectNode = HTTPClientUtils.sendHTTPRequest(chatMessagesUrl, thisCredential, null, HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return objectNode;
	}

	/**
	 * 获取聊天消息
	 * @param queryStrNode
	 */
	public static ObjectNode getChatMessages(Long minTime) {
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {// check appKey format
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		
		try {
			URL chatMessagesUrl = null;
			if (minTime == null) {
				chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatmessages");
			} else {
				chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
					+ "/chatmessages?limit=30000&ql=select+*+where+timestamp%3E"
					+ minTime);
			}
			objectNode = HTTPClientUtils.sendHTTPRequest(chatMessagesUrl, thisCredential, null, HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return objectNode;
	}
	

	/**
	 * 发送聊天信息
	 */
	private static ObjectNode chatMessages(ObjectNode queryStrNode) {
		Object thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode();
		if (queryStrNode != null) {
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.MESSAGES_URL, thisCredential, queryStrNode, HTTPMethod.METHOD_POST);
		}
		return objectNode;
	}

	public static ObjectNode myChatMessages(List<Users> list, String from, String text, ExtNode ext) {
		ObjectNode myNode = factory.objectNode();
		myNode.put("from", from);
		myNode.put("target_type", "users");
		
		ArrayNode userArray = factory.arrayNode();
		Iterator<Users> iterator = list.iterator();
		while (iterator.hasNext()) {
			userArray.add(iterator.next().getEmobId());
		}
		myNode.put("target", userArray);
		
		ObjectNode objectMsg = factory.objectNode();
		objectMsg.put("type", "cmd");
		myNode.put("msg", objectMsg);
		
		if (ext != null) {
			ObjectNode extInform = factory.objectNode();
			extInform.put("title", ext.getTitle());
			extInform.put("content", ext.getContent());
			extInform.put("timestamp", (int) (System.currentTimeMillis() / 1000l));
			if (ext.getCMD_CODE() != null) {
				extInform.put("CMD_CODE", ext.getCMD_CODE());
			}
			myNode.put("ext", extInform);
		}
		
		return chatMessages(myNode);
	}

	public static ObjectNode sendChatMessage(String[] users, String from, String message, Map<String, Object> ext) {
		ObjectNode root = factory.objectNode();
		root.put("from", from);
		root.put("target_type", "users");
		
		ArrayNode userArray = factory.arrayNode();
		for (int i = 0; i < users.length; i++) {
			userArray.add(users[i]);
		}
		root.put("target", userArray);
		
		ObjectNode objectMsg = factory.objectNode();
		objectMsg.put("type", "txt");
		objectMsg.put("msg", message);
		root.put("msg", objectMsg);
		
		if (ext != null && ext.size() > 0) {
			ObjectNode extInform = factory.objectNode();
			
			for (Iterator<Entry<String, Object>> iterator = ext.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> entry = iterator.next();
				
				Object value = entry.getValue();
				if (value instanceof String) {
					extInform.put(entry.getKey(), (String) value);
				} else if (value instanceof Integer) {
					extInform.put(entry.getKey(), (Integer) value);
				} else if (value instanceof Long) {
					extInform.put(entry.getKey(), (Long) value);
				} else if (value instanceof Float) {
					extInform.put(entry.getKey(), (Float) value);
				} else if (value instanceof Double) {
					extInform.put(entry.getKey(), (Double) value);
				} else if (value instanceof BigDecimal) {
					extInform.put(entry.getKey(), (BigDecimal) value);
				} else if (value instanceof Boolean) {
					extInform.put(entry.getKey(), (Boolean) value);
				} else if (value instanceof byte[]) {
					extInform.put(entry.getKey(), (byte[]) value);
				} else if (value instanceof JsonNode) {
					extInform.put(entry.getKey(), (JsonNode) value);
				} else {
					extInform.put(entry.getKey(), null == value ? null : value.toString());
				}
			}
			root.put("ext", extInform);
		}
		
		return chatMessages(root);
	}
	
	/**
	 * 发送消息
	 */
	public static ObjectNode testChatMessages(Set<String> list, String from, String text, ExtNode ext) {
		ObjectNode myNode = factory.objectNode();
		myNode.put("from", from);
		myNode.put("target_type", "users");
		
		ArrayNode userArray = factory.arrayNode();
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String user = iterator.next();
				userArray.add(user);
		}
		myNode.put("target", userArray);
		
		ObjectNode objectMsg = factory.objectNode();
		objectMsg.put("type", "txt");
		objectMsg.put("msg", text);
		myNode.put("msg", objectMsg);
		
		if (ext != null) {
			ObjectNode extInform = factory.objectNode();
			extInform.put("title", ext.getTitle());
			extInform.put("content", ext.getContent());
			extInform.put("timestamp", (int) (System.currentTimeMillis() / 1000l));
			extInform.put("CMD_CODE", ext.getCMD_CODE());
			extInform.put("nickname", ext.getNickname());
			extInform.put("avatar", ext.getAvatar());
			extInform.put("messageId", ext.getMessageId());
			myNode.put("ext", extInform);
		}
		return chatMessages(myNode);
	}
	
	/**
	 * 发送消息
	 */
	public static ObjectNode testChatMessagesAdmin(List<Admin> list, String from, String text, ExtNode ext) {
		ObjectNode myNode = factory.objectNode();
		myNode.put("from", from);
		myNode.put("target_type", "users");
		
		ArrayNode userArray = factory.arrayNode();
		Iterator<Admin> iterator = list.iterator();
		while (iterator.hasNext()) {
			userArray.add(iterator.next().getEmobId());
		}
		myNode.put("target", userArray);
		
		ObjectNode objectMsg = factory.objectNode();
		objectMsg.put("type", "txt");
		objectMsg.put("msg", text);
		myNode.put("msg", objectMsg);
		
		if (ext != null) {
			ObjectNode extInform = factory.objectNode();
			extInform.put("title", ext.getTitle());
			extInform.put("content", ext.getContent());
			extInform.put("timestamp", (int) (System.currentTimeMillis() / 1000l));
			extInform.put("CMD_CODE", ext.getCMD_CODE());
			extInform.put("nickname", ext.getNickname());
			extInform.put("avatar", ext.getAvatar());
			myNode.put("ext", extInform);
		}
		return chatMessages(myNode);
	}

	private static Object getNodeString(JsonNode objectNode, String key, Object object) {
		if (object instanceof String) {
			return objectNode.get(key) != null ? objectNode.get(key).asText() : "";
		} else if (object instanceof Long) {
			return objectNode.get(key) != null ? objectNode.get(key).asLong() : 0l;
		} else if (object instanceof Integer) {
			return objectNode.get(key) != null ? objectNode.get(key).asInt() : 0;
		}
		return null;
	}

	// 从聊天记录对象中获取数据
	public static List<Messages> getUserMessageBean(ObjectNode resultObjectNode) {
		Messages bean = new Messages();
		String statusCode = "";
		List<Messages> list = new ArrayList<Messages>();
		if (null != resultObjectNode) {
			if (resultObjectNode.get("statusCode") != null) {
				statusCode = resultObjectNode.get("statusCode").asText();
			}
			
			if ("200".equals(statusCode)) {
				JsonNode array = resultObjectNode.get("entities");
				Iterator<JsonNode> it = array.iterator();
				JsonNode jsonNode = null;
				while (it.hasNext()) {
					jsonNode = it.next();
					bean.setCMD_CODE(statusCode);
					bean.setUuid((String) getNodeString(jsonNode, "uuid", ""));
					bean.setChatType((String) getNodeString(jsonNode, "chat_type", ""));
					bean.setType((String) getNodeString(jsonNode, "type", ""));
					bean.setMessageFrom((String) getNodeString(jsonNode, "from", ""));
					bean.setMessageTo((String) getNodeString(jsonNode, "to", ""));
					bean.setMsgId((String) getNodeString(jsonNode, "msg_id", ""));
					bean.setTimestamp((Long) getNodeString(jsonNode, "timestamp", 0l));
					bean.setCreated((Long) getNodeString(jsonNode, "created", 0l));
					bean.setModified((Long) getNodeString(jsonNode, "modified", 0l));
					bean.setDirection((String) getNodeString(jsonNode, "direction", ""));
					if (jsonNode.get("payload") != null) {
						JsonNode jsonNode2 = jsonNode.get("payload");
						JsonNode arrayNode = jsonNode2.get("bodies");
						Iterator<JsonNode> it2 = arrayNode.iterator();
						if (it2.hasNext()) {
							JsonNode payLoadBean = it2.next();

							bean.setAddr((String) getNodeString(payLoadBean, "addr", ""));
							bean.setFilename((String) getNodeString(payLoadBean, "filename", ""));
							bean.setLat((String) getNodeString(payLoadBean, "lat", ""));
							bean.setVideoLength((Integer) getNodeString(payLoadBean, "length", 0));
							bean.setLng((String) getNodeString(payLoadBean, "lng", ""));
							bean.setMsg((String) getNodeString(payLoadBean, "msg", ""));
							bean.setSecret((String) getNodeString(payLoadBean, "secret", ""));
							bean.setMsgType((String) getNodeString(payLoadBean, "type", ""));
							bean.setUrl((String) getNodeString(payLoadBean, "url", ""));
						}
					}
					list.add(bean);
				}
			} else {
				LOGGER.error("没有获取到聊天记录");
			}
		} else {
			LOGGER.error("没有获取到聊天记录");
		}
		return list;
	}
	
	
}