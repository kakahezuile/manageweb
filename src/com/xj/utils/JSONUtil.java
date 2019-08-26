package com.xj.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {

	public static JSONObject getJSONObject(JSONObject jsonObject,
			String attrName) {
		JSONObject returnObject = null;
		if (jsonObject.has(attrName)) {
			try {
				returnObject = jsonObject.getJSONObject(attrName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnObject;
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String attrName) {
		JSONArray returnArray = null;
		if (jsonObject.has(attrName)) {
			try {
				returnArray = jsonObject.getJSONArray(attrName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnArray;
	}

	public static String getString(JSONObject jsonObject, String attrName) {
		String returnString = null;
		if (jsonObject.has(attrName)) {
			try {
				returnString = jsonObject.getString(attrName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnString;
	}

	public static int getInt(JSONObject jsonObject, String attrName) {
		int returnInt = -1;
		if (jsonObject.has(attrName)) {
			try {
				returnInt = jsonObject.getInt(attrName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnInt;
	}
}
