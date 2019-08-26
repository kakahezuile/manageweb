package com.xj.xiaomi;

import java.util.List;

import com.google.gson.Gson;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import com.xj.bean.ExtNode;

public class MIpush {
	
	private static final String MY_PACKAGE_NAME = "xj.property";
	
	private static final String APP_SECRET_KEY = "Pzfd1VUcTax7L5NyeA3sVA==";

	public static void sendMessageToAliases(ExtNode extNode, List<String> list) throws Exception {
		Constants.useOfficial();
		Sender sender = new Sender(APP_SECRET_KEY);
		// 使用默认提示音提示
		Message message = new Message.Builder().title(extNode.getTitle()).description(extNode.getContent()).payload(new Gson().toJson(extNode)).restrictedPackageName(MY_PACKAGE_NAME).notifyType(1).build();
		sender.sendToAlias(message, list, 0); // 根据aliasList，发送消息到指定设备上，不重试。		
	}
}