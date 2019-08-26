package com.xj.thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.xj.bean.ExtNode;
import com.xj.bean.PushMessage;
import com.xj.bean.Users;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.mongo.PushMessageMongo;
import com.xj.mongo.impl.PushMessageMongoImpl;
import com.xj.push.utils.PushUtils;

/**
 * 消息推送线程
 */
public class PushNotification2Ios implements Runnable{
	
	private ExtNode ext;
	
	private Set<Users> users;
	
	public PushNotification2Ios(ExtNode extNode , Set<Users> users){
		this.users = users;
		this.ext = extNode; 
	}
	
	@Override
	public void run() {
		PushMessageMongo pushMessageMongo = new PushMessageMongoImpl();
		Set<String> ios_new = new HashSet<String>();
		Set<String> ios_old = new HashSet<String>();
		try {
			Iterator<Users> iterator = users.iterator();
			while(iterator.hasNext()){
				Users u = iterator.next();
				
				PushMessage pushMessage = new PushMessage();
				pushMessage.setMessageId(ext.getMessageId());
				pushMessage.setEmobId(u.getEmobId());
				pushMessage.setEquipment(u.getEquipment());
				pushMessage.setCMD_CODE(ext.getCMD_CODE());
				pushMessage.setTitle(ext.getTitle());
				pushMessage.setContent(ext.getContent());
				pushMessage.setTimestamp(ext.getTimestamp());
				pushMessage.setNickname(ext.getNickname());
				pushMessage.setType("unread");
				pushMessage.setCommunityId(ext.getCommunityId());
				pushMessage.setNumber(ext.getCount());
				pushMessageMongo.addPushMessage(pushMessage);
				
				if(StringUtils.isNotBlank(u.getEquipment()) && "ios".equals(u.getEquipment())){
					continue;
				}
				if(StringUtils.isNotBlank(u.getDeviceToken())){
					ios_new.add(u.getDeviceToken());
				}else{
					ios_old.add(u.getEmobId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ext.getCMD_CODE()==100 && CollectionUtils.isNotEmpty(ios_new)){
			Map<String,Object> content = new HashMap<String,Object>();
			content.put("CMD_CODE", ext.getCMD_CODE());
			content.put("title", ext.getTitle());
			content.put("content", ext.getContent());
			content.put("messageId", ext.getMessageId());
			content.put("timestamp", ext.getTimestamp());
			
			PushUtils.sendIosPush(content, ext.getMessageId(), ios_new.toArray(new String[]{}));
		}
		
		if (CollectionUtils.isNotEmpty(ios_old)) {
			EasemobIMUsers.updateIMUser(EasemobIMUsers.getAminEmobId(), ext.getNickname());
			EasemobChatMessage.testChatMessages(ios_old, EasemobIMUsers.getAminEmobId(), ext.getContent(), ext);
		}
		
		users.clear();
	}
}