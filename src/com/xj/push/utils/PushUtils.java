package com.xj.push.utils;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.xinge.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;
import com.xj.push.Push2ClientBuilder;
import com.xj.push.apns.IApnsService;
import com.xj.push.apns.model.Payload;
import com.xj.utils.SystemProperties;

public class PushUtils {
	
	private static SystemProperties properties = SystemProperties.getInstance();
	
	/**
	 * 发送ios推送
	 * @param node
	 * @param messageId
	 * @param users
	 */
	public static void sendIosPush(Map<String, Object> node, Long messageId, String... users) {
		if (null == users || users.length == 0) {
			return;
		}
		
		IApnsService service = Push2ClientBuilder.getApnsService();
		for (int i = 0; i < users.length; i++) {
			try {
				Payload payload = new Payload();
				payload.setAlert(node.get("title").toString());
				payload.setBadge(1);
				payload.setSound("default");
				payload.addParam("content", node);
				service.sendNotification(users[i], payload);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/**
	 * 信鸽大量推送到账户   10000+
	 * @param node
	 * @param users   目标用户账户  不得大于1000
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject sendXingePushMessageMulti(Map<String,Object> node, String... users) throws JSONException {
		XingeApp xinge = new XingeApp(Integer.valueOf(properties.get("push.xinge.client.accessid")), properties.get("push.xinge.client.secretkey"));
		Message message = new Message();
		message.setExpireTime(86400);
		message.setCustom(node);
		message.setTitle(node.get("title").toString());
		message.setContent(node.get("content").toString());
		message.setStyle(new Style(0,1,1,0,0));
		message.setType(Message.TYPE_MESSAGE);//透传消息
		JSONObject ret = xinge.createMultipush(message);
		if (ret.getInt("ret_code") != 0){
			return (ret);
		} else {
            JSONObject result = new JSONObject();
            result.append("all", xinge.pushAccountListMultiple(ret.getJSONObject("result").getInt("push_id"), Arrays.asList(users)));
            return (result);
        }
	}
	
	/**
	 * 信鸽推送到单个账户     
	 * @param node
	 * @param users   目标用户账户  不得大于1000
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject sendXingePushMessageSingle(Map<String,Object> node, String account) throws JSONException {
		XingeApp xinge = new XingeApp(Integer.valueOf(properties.get("push.xinge.client.accessid")), properties.get("push.xinge.client.secretkey"));
		Message message = new Message();
		message.setExpireTime(86400);
		message.setCustom(node);
		message.setTitle(node.get("title").toString());
		message.setContent(node.get("content").toString());
		message.setType(Message.TYPE_MESSAGE); //透传消息
		message.setStyle(new Style(0,1,1,0,0));
		JSONObject ret = xinge.pushSingleAccount(0, account, message);
		return (ret);
	}
	
	
	/**
	 * 信鸽推送到多个账户      1-10000
	 * @param node
	 * @param users   目标用户账户  不得大于1000
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject sendXingePushMessageList(Map<String,Object> node, String... users) throws JSONException {
		XingeApp xinge = new XingeApp(Integer.valueOf(properties.get("push.xinge.client.accessid")), properties.get("push.xinge.client.secretkey"));
		Message message = new Message();
		message.setExpireTime(86400);
		message.setCustom(node);
		message.setTitle(node.get("title").toString());
		message.setContent(node.get("content").toString());
		message.setStyle(new Style(0, 1, 1, 0, 0));
		message.setType(Message.TYPE_MESSAGE);
		
		JSONObject ret = xinge.pushAccountList(0,  Arrays.asList(users), message);
		return (ret);
	}
	
}