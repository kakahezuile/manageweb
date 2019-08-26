package com.xj.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.PushMessage;
import com.xj.bean.Users;
import com.xj.getui.pushtoList;
import com.xj.mongo.PushMessageMongo;
import com.xj.mongo.impl.PushMessageMongoImpl;
import com.xj.push.utils.PushUtils;
import com.xj.utils.DateUtil;

/**
 * 消息推送线程
 */
public class PushNotification2Andriod implements Runnable{
	
	private Logger logger = LoggerFactory.getLogger(PushNotification2Ios.class);
	
	private ExtNode ext;
	
	private Set<Users> users;
	
	public PushNotification2Andriod(ExtNode extNode , Set<Users> users){
		this.users = users;
		this.ext = extNode; 
	}
	
	@Override
	public void run() {
		PushMessageMongo pushMessageMongo = new PushMessageMongoImpl();
		List<String> andorid_old = new ArrayList<String>();
		List<String> andorid_new = new ArrayList<String>();
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
			try {
				pushMessageMongo.addPushMessage(pushMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if( StringUtils.isNotBlank(u.getEquipmentVersion()) && u.getEquipmentVersion().compareTo("1.5.0")>=0){
				andorid_new.add(u.getEmobId());
			}else{
				andorid_old.add(u.getEmobId());
			}
		}
		
		if(CollectionUtils.isNotEmpty(andorid_old)){
			pushtoList.pushtoTransmissionList1000(new ArrayList<String>(andorid_old),new Gson().toJson(ext), ext.getContent());
		}
		
		if(CollectionUtils.isNotEmpty(andorid_new)){
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("CMD_CODE", ext.getCMD_CODE());
			node.put("title", ext.getTitle());
			node.put("content", ext.getContent());
			node.put("messageId", ext.getMessageId());
			node.put("timestamp", ext.getTimestamp());
			
			int i =0;
			while(i  < andorid_new.size()){
				int j = i+999;
				if(j>andorid_new.size()-1){
					j=andorid_new.size();
				}
				List<String> subList = andorid_new.subList(i, j);
				try {
					PushUtils.sendXingePushMessageList(node, subList.toArray(new String[subList.size()]));
				} catch (JSONException e) {
					logger.error("["+DateUtil.formateTimeMillSeconds(new Date())+"]xingge push message to andriod  error , messageId:"+node.get("messageId")+", subList("+i+", "+j+")");
					i+=999;
					continue;
				}
				i+=999;
			}
		}
		users.clear();
	}
}