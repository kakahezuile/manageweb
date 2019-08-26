package com.xj.push;

import java.io.IOException;
import java.io.InputStream;

import com.xj.push.apns.IApnsService;
import com.xj.push.apns.impl.ApnsServiceImpl;
import com.xj.push.apns.model.ApnsConfig;
import com.xj.utils.SystemProperties;

/**
 * 用户端推送
 */
public class Push2ClientBuilder {
	
	private static IApnsService apnsService;
	
	public static synchronized IApnsService getApnsService() {
		if (apnsService == null) {
			SystemProperties properties = SystemProperties.getInstance();
			
			String keypath = properties.get("push.ios.keypath");
			try (InputStream is = Push2ClientBuilder.class.getClassLoader().getResourceAsStream(keypath)) {
				ApnsConfig config = new ApnsConfig();
				config.setKeyStore(is);
				config.setDevEnv(false);
				config.setPassword(properties.get("push.ios.kspassword"));
				config.setPoolSize(5);
				
				apnsService = ApnsServiceImpl.createInstance(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return apnsService;
	}
}