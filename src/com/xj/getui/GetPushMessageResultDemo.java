package com.xj.getui;



import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.gexin.rp.sdk.http.utils.HttpUtil;

public class GetPushMessageResultDemo {
	

	//填写mastersecret
	private static final String MASTERSECRET = "mqfl1ye5C99qSZJuejLeW4";
	//填写appkey
	private static final String APPKEY = "dJ2RV2IJIM8AyOSmzJplS7";
	//填写taskId
	private static final String TASKID = "OSL-0616_RUFSM7IYrx80r25V9c8556";
	public static void main(String[] args) {
		SortedMap<String, Object> param = new TreeMap<String, Object>();
		param.put("action", "getPushMsgResult");
		param.put("appkey", APPKEY);
		param.put("taskId", TASKID);


		// 计算Sign值
		StringBuilder input = new StringBuilder(MASTERSECRET);
		for (Entry<String, Object> entry : param.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof String || value instanceof Integer || value instanceof Long) {
				input.append(entry.getKey());
				input.append(entry.getValue());
			}
		}
		String sign = MD5Util.getMD5Format(input.toString());
		param.put("sign", sign);

		Map<String, Object> ret = post(param);
		System.out.println(ret);

	}

	private static Map<String, Object> post(Map<String, Object> param) {
		return HttpUtil.httpPostJSON("http://sdk.open.api.igexin.com/apiex.htm", param);
	}
}

