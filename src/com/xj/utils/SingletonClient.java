package com.xj.utils;

import cn.emay.sdk.client.api.Client;

/**
 * 亿美短信客户端
 */
public class SingletonClient {

	private static Client client = null;

	private SingletonClient() {
	}

	public synchronized static Client getClient(String softwareSerialNo, String key) {
		if (client == null) {
			try {
				client = new Client(softwareSerialNo, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	public synchronized static Client getClient() {
		if (client == null) {
			try {
				SystemProperties properties = SystemProperties.getInstance();
				client = new Client(properties.get("serialNumber"), properties.get("serialPassword"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
}