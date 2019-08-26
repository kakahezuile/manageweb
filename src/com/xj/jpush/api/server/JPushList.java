package com.xj.jpush.api.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.Users;
import com.xj.jpush.api.JPushClient;
import com.xj.jpush.api.common.resp.APIConnectionException;
import com.xj.jpush.api.common.resp.APIRequestException;
import com.xj.jpush.api.push.model.Message;
import com.xj.jpush.api.push.model.Platform;
import com.xj.jpush.api.push.model.PushPayload;
import com.xj.jpush.api.push.model.audience.Audience;
import com.xj.jpush.api.push.model.notification.IosNotification;
import com.xj.jpush.api.push.model.notification.Notification;

public class JPushList {

	private static String masterSecret = "25bb033584af4844314d879f";
	private static String appKey = "9d01084c03e5e2b3def02b3a";

	public static String push(ExtNode ext, List<Users> list) {
		JPushClient jPushClient = new JPushClient(masterSecret, appKey, false, 30*60);
		Gson gson = new Gson();
		String resultStr = "";
		Iterator<Users> iterator = list.iterator();
		Users u = null;
		List<String> androidUsers = new ArrayList<String>();
		List<String> iosUsers = new ArrayList<String>();
		while (iterator.hasNext()) {
			u = iterator.next();
			if (u != null && "android".equals(u.getEquipment())) {
				androidUsers.add(u.getEmobId());
			}
			if (u != null && "ios".equals(u.getEquipment())&& StringUtils.isNotBlank(u.getEquipmentVersion()) && "1.3.9".compareTo(u.getEquipmentVersion())<=0) {
				iosUsers.add(u.getEmobId());
			}
		}
		//处理android推送
		if (androidUsers != null && androidUsers.size()!=0) {
			int androidLen = androidUsers.size();
			String androids[] = new String[androidLen];
			Iterator<String> androidIt = androidUsers.iterator();
			int i = 0;
			while(androidIt.hasNext()){
				androids[i] = androidIt.next();
				i++;
			}
			try {
				jPushClient.sendAndroidMessageWithAlias(ext.getTitle(), gson.toJson(ext), androids);
			} catch (APIConnectionException e) {
//				e.printStackTrace();
			} catch (APIRequestException e) {
//				e.printStackTrace();
			}
		}
		//处理ios推送
		if (iosUsers != null && iosUsers.size()!=0) {
			int iosLen = iosUsers.size();
			String ioses[] = new String[iosLen];
			Iterator<String> iosIt = iosUsers.iterator();
			int j = 0;
			while(iosIt.hasNext()){
				ioses[j] = iosIt.next();
				j++;
			}
			try {
				PushPayload ppl = buildPushMessage(ext.getTitle(),gson.toJson(ext),ioses);
				jPushClient.sendPush(ppl);
			} catch (APIConnectionException e) {
//				e.printStackTrace();
			} catch (APIRequestException e) {
//				e.printStackTrace();
			}
		}
		return resultStr;
	}
	
	private static PushPayload buildPushMessage(String alert,String msgContent,String... alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
//                                .setBadge(1)
                                .incrBadge(1)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                                
                        .build())
                 .setMessage(Message.content(msgContent))
//                 .setOptions(Options.newBuilder()
//                         .setApnsProduction(false)  //true : 生产环境  ; false : 开发环境
//                         .build())
                 .build();
    }
}