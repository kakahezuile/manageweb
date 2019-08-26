package com.xj.getui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class pushtoList {

	private static final String appId = "IWesbtJ9TB7xKLW5QZCAZ6";
	private static final String appkey = "dJ2RV2IJIM8AyOSmzJplS7";
	private static final String master = "mqfl1ye5C99qSZJuejLeW4";
	private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void pushtoTransmissionList1000(List<String> list , String extJson , String ms ){
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		int i =0;
		while(i  < list.size()){
			int j = i+999;
			if(j>list.size()-1){
				j=list.size();
			}
			List<String> subList = list.subList(i, j);
			pushtoTransmission(subList, extJson, ms);
			i+=999;
		}
		list.clear();
	}
	
	public static String[] pushtoTransmission(List<String> list , String extJson , String ms){
		IGtPush push = null;
		try {
			push = new IGtPush(host, appkey, master);
			
			List<Target> targets = new ArrayList<Target>();
			if (list != null && list.size() > 0) {
				for (int i = 0 ; i < list.size(); i++) {// 添加 发送用户群组
					Target target = new Target();
					target.setAlias(list.get(i));
					target.setAppId(appId);
					targets.add(target);
				}
			}
			
			ListMessage message = new ListMessage();
			message.setData(getTransmissionTemplate(extJson, ms));
			message.setOffline(true);
			message.setOfflineExpireTime(30*1000);
			message.setPushNetWorkType(0);
			
			String taskId = push.getContentId(message);
			IPushResult ret = push.pushMessageToList(taskId, targets);
			
			String result[] = new String[2];
			result[0] = ret.getResponse().toString();
			result[1] = taskId;
			
			return result;
		} finally {
			if (null != push) {
				try {
					push.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static TransmissionTemplate getTransmissionTemplate(String extJson , String message){
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(2);
		template.setTransmissionContent(extJson);
		
		return template;
	}
}