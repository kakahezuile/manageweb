package com.xj.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.xj.bean.ExtNode;
import com.xj.bean.PushMessage;
import com.xj.bean.Users;
import com.xj.mongo.PushMessageMongo;
import com.xj.mongo.impl.PushMessageMongoImpl;
import com.xj.xiaomi.MIpush;

/**
 * 消息推送线程
 */
public class PushNotification2Mi implements Runnable{
	
	private ExtNode ext;
	
	private Set<Users> users;
	
	public PushNotification2Mi(ExtNode extNode , Set<Users> users){
		this.users = users;
		this.ext = extNode; 
	}
	
	@Override
	public void run() {
		PushMessageMongo pushMessageMongo = new PushMessageMongoImpl();
		Iterator<Users> iterator = users.iterator();
		List<String>  target = new ArrayList<String>();
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
			try {
				pushMessageMongo.addPushMessage(pushMessage);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			target.add(u.getEmobId());
		}
			
		if(CollectionUtils.isNotEmpty(target)){
			try {
				MIpush.sendMessageToAliases(ext, new ArrayList<String>(target));
				users.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}