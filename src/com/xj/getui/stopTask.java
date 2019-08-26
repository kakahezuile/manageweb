package com.xj.getui;

import java.io.IOException;

import com.gexin.rp.sdk.http.IGtPush;

public class stopTask {
	static String appId = "IWesbtJ9TB7xKLW5QZCAZ6";
	static String appkey = "dJ2RV2IJIM8AyOSmzJplS7";
	static String master = "";
	static String TaskId="";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(new IGtPush(host, appkey, master).stop(TaskId));
	}
}