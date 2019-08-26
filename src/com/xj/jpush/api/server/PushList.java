package com.xj.jpush.api.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.jpush.api.JPushClient;
import com.xj.jpush.api.push.model.Message;
import com.xj.jpush.api.push.model.PushPayload;
import com.xj.jpush.api.push.model.audience.Audience;
import com.xj.jpush.api.push.model.audience.AudienceTarget;

public class PushList {

	private static String appKey = "9d01084c03e5e2b3def02b3a";

	private static String masterSecret = "25bb033584af4844314d879f";

	//private static String packege = "xj.property";

	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String content) {
		return PushPayload.newBuilder().setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias("e9c3eefafe5c977a3e04e99d109d378b", "alias2")).build()).setMessage(Message.newBuilder().setMsgContent(content).addExtra("from", "JPush").build()).build();
	}

	public static String push(ExtNode ext, List<String> list){
		JPushClient jPushClient = new JPushClient(masterSecret, appKey, false, 30*60);
		Gson gson = new Gson();
		String resultStr = "";
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> listTo = new ArrayList<String>();
			int len = list.size();
			for(int i = 0 ; i < len ;i++){
				listTo.add(list.get(i));
				if(i % 999 == 0){
					try {
						jPushClient.sendAndroidMessageWithAlias(ext.getTitle(), gson.toJson(ext), listTo.toArray(new String[]{}));
					} catch (Exception e) {
						e.printStackTrace();
					}
					listTo = new ArrayList<String>();
				}
				if(i == len-1){
					try {
						jPushClient.sendAndroidMessageWithAlias(ext.getTitle(), gson.toJson(ext), listTo.toArray(new String[]{}));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
		}
		return resultStr;
	}
}
