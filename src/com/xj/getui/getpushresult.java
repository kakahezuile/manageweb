package com.xj.getui;


import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.gexin.rp.sdk.http.utils.HttpUtil;

//import com.gexin.platform.open.utils.encrypt.MD5Util;
//import com.gexin.platform.open.utils.network.HttpUtil;

public class getpushresult {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		String appKey = "dJ2RV2IJIM8AyOSmzJplS7";
		String masterSecret = "mqfl1ye5C99qSZJuejLeW4";
		String taskId = "OSL-0616_RUFSM7IYrx80r25V9c8556";
		String url = "http://sdk.open.api.igexin.com/api.htm";
		Map<String, Object> result = post(url, appKey, masterSecret, taskId);
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(result));
	}
	private static Map<String, Object> post(String url,String appKey,String masterSecret,String taskId) {
		SortedMap<String, Object> param = new TreeMap<String, Object>();
		param.put("action", "getPushMsgResult");
		param.put("appkey", appKey);
		param.put("taskId", taskId);


		// 求sign值方法（1）
		StringBuilder input = new StringBuilder(masterSecret);
		for (Entry<String, Object> entry : param.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof String || value instanceof Integer || value instanceof Long) {
				input.append(entry.getKey());
				input.append(entry.getValue());
			}
		}
		String sign = MD5Util.getMD5Format(input.toString());
		param.put("sign", sign);

		Map<String , Object> ret = HttpUtil.httpPostJSON(url, param);
		return ret;
	}

}
