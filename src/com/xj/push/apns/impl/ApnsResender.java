/*
 * Copyright 2013 DiscoveryBay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xj.push.apns.impl;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xj.push.apns.IApnsService;
import com.xj.push.apns.model.PushNotification;

/**
 * EN: resend the notifications which sent after an error one using same connection
 * CN: 重发，没啥好说的
 * @author RamosLi
 *
 */
public class ApnsResender {
	
	private ApnsResender() {}

	private static Logger logger = LoggerFactory.getLogger(ApnsResender.class);
	
	private static ApnsResender instance = new ApnsResender();
	
	public static ApnsResender getInstance() {
		return instance;
	}
	
	public void resend(String name, Queue<PushNotification> queue) {
		IApnsService service = ApnsServiceImpl.getCachedService(name);
		if (service != null) {
			while (!queue.isEmpty()) {
				service.sendNotification(queue.poll());
			}
		} else {
			logger.error("Cached service is null. name: " + name);
		}
	}
}