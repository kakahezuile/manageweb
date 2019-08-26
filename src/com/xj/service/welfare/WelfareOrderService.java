package com.xj.service.welfare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.xj.bean.welfare.Welfare;
import com.xj.bean.welfare.WelfareOrder;
import com.xj.dao.welfare.WelfareDao;
import com.xj.dao.welfare.WelfareOrderDao;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.mongo.MongoUtils;

/**
 * 福利订单业务服务
 * @author 王利东
 * 2015-09-16
 */
@Service("welfareOrderService")
public class WelfareOrderService {

	public static final String WELFARE_ADMIN_EMOBID = "welfare_admin_emobid";
	public static final String WELFARE_ADMIN_AVATAR = "http://7d9lcl.com2.z0.glb.qiniucdn.com/common_pic_welfare_avatar.png";
	public static final String WELFARE_ADMIN_NICKNAME = "福利";
	public static final int WELFARE_MESSAGE_TYPE_SUCCESS = 601;
	public static final int WELFARE_MESSAGE_TYPE_FAILURE = 602;
	public static final int WELFARE_MESSAGE_TYPE_NOTIFY = 603;

	@Autowired
	private WelfareOrderDao welfareOrderDao;

	@Autowired
	private WelfareDao welfareDao;

	/**
	 * 获取福利信息
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public List<WelfareOrder> getWelfareOrders(Integer welfareId) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("请提供参数:welfareId");
		}
		
		return welfareOrderDao.getWelfareOrders(welfareId);
	}
	
	/**
	 * 退款
	 * @param welfareOrderId
	 * @return
	 */
	public boolean refund(Integer welfareOrderId) {
		return welfareOrderDao.refund(welfareOrderId);
	}
	
	/**
	 * 发送通知到福利某一具体用户
	 * @param welfareOrderId
	 * @param type
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public boolean notify(Integer welfareOrderId, int type, String message) throws Exception {
		if (null == welfareOrderId) {
			throw new NullPointerException("请提供参数:welfareOrderId");
		}
		
		WelfareOrder welfareOrder = welfareOrderDao.getWelfareOrder(welfareOrderId);
		if (null == welfareOrder) {
			throw new IllegalArgumentException("无法获取到订单信息:" + welfareOrderId);
		}
		
		Welfare welfare = welfareDao.getWelfare(welfareOrder.getWelfareId());
		if (null == welfare) {
			throw new IllegalArgumentException("无法获取到福利信息!");
		}
		
		return notify(new String[] {welfareOrder.getEmobId()}, type, welfare, message);
	}
	
	/**
	 * 发送通知到福利指定范围的用户
	 * @param welfareId
	 * @param type
	 * @param status 订单状态
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public boolean notifyAll(Integer welfareId, int type, String status, String message) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("请提供参数:welfareId");
		}
		
		String[] users = welfareOrderDao.getWelfareOrderUsers(welfareId, status);
		Welfare welfare = welfareDao.getWelfare(welfareId);
		if (null == welfare) {
			throw new IllegalArgumentException("无法获取到福利信息!");
		}
		
		return notify(users, type, welfare, message);
	}
	
	private boolean notify(String[] users, int type, Welfare welfare, String message) {
		if (null == users || users.length == 0) {
			return false;
		}
		if (StringUtils.isBlank(message)) {
			return false;
		}
		if (WELFARE_MESSAGE_TYPE_SUCCESS != type && WELFARE_MESSAGE_TYPE_FAILURE != type) {
			type = WELFARE_MESSAGE_TYPE_NOTIFY;
		}
		
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("timestamp", (int) (System.currentTimeMillis() / 1000l));
		ext.put("avatar", WELFARE_ADMIN_AVATAR);
		ext.put("nickname", WELFARE_ADMIN_NICKNAME);
		ext.put("CMD_CODE", type);
		ext.put("welfareId", String.valueOf(welfare.getWelfareId()));
		ext.put("title", welfare.getTitle());
		ext.put("code", "");
		ext.put("image", welfare.getPoster());
		ext.put("clickable", 1);
		ext.put("messageId", UUID.randomUUID().toString().replaceAll("-", ""));
		ext.put("shopType", "19");
		
		ObjectNode response = EasemobChatMessage.sendChatMessage(users, WELFARE_ADMIN_EMOBID, message, ext);
		boolean success = response.get("statusCode").asInt() == 200;
		if (success) {
			sync2Mongodb(users, ext, message);
		}
		
		return success;
	}
	
	private void sync2Mongodb(final String[] users, final Map<String, Object> ext, final String content) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DBCollection collection = MongoUtils.getCollection("welfare_message");
				
				for (int i = 0; i < users.length; i++) {
					DBObject message = new BasicDBObject();
					message.put("timestamp", ext.get("timestamp"));
					message.put("avatar", ext.get("avatar"));
					message.put("nickname", ext.get("nickname"));
					message.put("CMD_CODE", ext.get("CMD_CODE"));
					message.put("welfareId", ext.get("welfareId"));
					message.put("title", ext.get("title"));
					message.put("code", ext.get("code"));
					message.put("image", ext.get("image"));
					message.put("clickable", ext.get("clickable"));
					message.put("messageId", ext.get("messageId"));
					message.put("shopType", ext.get("shopType"));
					message.put("emobId", users[i]);
					message.put("content", content);
					message.put("status", "unread");
					
					collection.insert(message);
				}
			}
		}, "福利消息同步到Mongodb线程").start();
	}
}