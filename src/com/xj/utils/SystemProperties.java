package com.xj.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystemProperties {

	private static SystemProperties instance;
	private Map<String, String> properties = new HashMap<String, String>();

	private SystemProperties() {
	}

	public static SystemProperties getInstance() {
		if (instance == null) {
			synchronized (SystemProperties.class) {
				if (instance == null) {
					instance = new SystemProperties();
					
					Properties propertites = PropertyTool.getPropertites("/configure.properties");
					for (Iterator<Entry<Object, Object>> iterator = propertites.entrySet().iterator(); iterator.hasNext();) {
						Entry<Object, Object> next = iterator.next();
						
						instance.put(getStringValue(next.getKey()), getStringValue(next.getValue()));
					}
				}
			}
		}
		
		return instance;
	}

	public String get(String key) {
		return this.properties.get(key);
	}

	public void put(String key, String value) {
		this.properties.put(key, value);
	}

	private static String getStringValue(Object obj) {
		if (null == obj) {
			return null;
		}
		
		return obj.toString();
	}
}