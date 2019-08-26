package com.xj.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.PushMessage;
import com.xj.bean.PushTask;
import com.xj.bean.Users;
import com.xj.getui.pushtoList;
import com.xj.jpush.api.server.PushList;
import com.xj.mongo.PushMessageMongo;

/**
 * app主动拉取消息推送线程
 * @author Administrator
 *
 */
public class PushThreadObj implements Runnable {
	
	private PushMessageMongo pushMessageMongo;
	
	private List<Users> users;
	
	private PushTask pushTask;
	
	private Integer time;
	
	public PushThreadObj(List<Users> users , PushMessageMongo pushMessageMongo ,  Integer time){
		this.users = users;
		this.pushMessageMongo = pushMessageMongo;
		this.time = time;
	}
	
	@Override
	public void run() {
		if(users != null && users.size() > 0){
			try {
				List<PushMessage> list = pushMessageMongo.getPushMessages(users.get(0).getEmobId(),time);
				if(list != null && list.size() > 0){
					System.out.println("get list message "+list.size()+" pushTheadObj --------------------------------------------------");
				}
				
				if(list != null && list.size() > 0){
					Iterator<PushMessage> iterator = list.iterator();
					ExtNode extNode = null;
					while(iterator.hasNext()){
						PushMessage pushMessage = iterator.next();
						extNode = new ExtNode();
						extNode.setCMD_CODE(pushMessage.getCMD_CODE());
						extNode.setContent(pushMessage.getContent());
						extNode.setMessageId(pushMessage.getMessageId());
						extNode.setNickname(pushMessage.getNickname());
						extNode.setTitle(pushMessage.getTitle());
						extNode.setTimestamp(pushMessage.getTimestamp());
						users.get(0).setEquipment(pushMessage.getEquipment());
						push(extNode);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void push(ExtNode ext){
		try {
			if(ext.getCMD_CODE() > 110){
				ext.setContent(ext.getNickname()+":"+ext.getContent()); 
				pushTask.setPushId(users.get(0).getEmobId());
			}
			List<String> list = new ArrayList<>();
			list.add(users.get(0).getEmobId());
			pushtoList.pushtoTransmissionList1000(list,new Gson().toJson(ext), ext.getContent());
			PushList.push(ext, list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
