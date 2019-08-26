package com.xj.getui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.xj.bean.ExtNode;

public class pushtoAPP {

	static String appId = "IWesbtJ9TB7xKLW5QZCAZ6";
	static String appkey = "dJ2RV2IJIM8AyOSmzJplS7";
	static String master = "mqfl1ye5C99qSZJuejLeW4";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	

	public static void main(String[] args) throws Exception {

		ExtNode extNode = new ExtNode();
		extNode.setCMD_CODE(110);
//		String result = pushTransmission(new Gson().toJson(extNode));
//		System.out.println(result);
	}
	
	public static String pushTransmission(String extJson,String ms) throws Exception{
		String result = "";
		IGtPush push = new IGtPush(host, appkey, master);
		TransmissionTemplate template = TransmissionTemplateDemo(extJson,ms);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		List<String> phoneTypeList = new ArrayList<String>();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		phoneTypeList.add("ANDROID");
		phoneTypeList.add("IOS");
		message.setAppIdList(appIdList);
		message.setPhoneTypeList(phoneTypeList);
		message.setPushNetWorkType(0);
		IPushResult ret = push.pushMessageToApp(message);
		result = ret.getResponse().toString();
		return result;
	}

	public static TransmissionTemplate TransmissionTemplateDemo(String extNode , String message)
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(2);
		template.setTransmissionContent(extNode);
		template.setPushInfo("", 1, message, "", "",
				"", "", "");
		return template;
	}

	public static LinkTemplate linkTemplateDemo(String extNode) throws Exception {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle("标题");
		template.setText("内容");
		template.setLogo("icon.png");
		template.setLogoUrl("");
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		template.setUrl("http://www.baidu.com");
		template.setPushInfo("actionLocKey", 1, "message", "sound", extNode,
				"locKey", "locArgs", "launchImage",1);
		return template;
	}

}