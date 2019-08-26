package com.xj.thread;

import com.xj.bean.Messages;
import com.xj.mongo.MessageMongo;
import com.xj.service.SensitiveWordsHistoryService;
import com.xj.timer.MessageTransfer;

public class MessageSelectThread implements Runnable {

	private MessageMongo messageMongo;
	
	private SensitiveWordsHistoryService sensitiveWordsHistoryService;

	public MessageSelectThread(MessageMongo messageMongo, SensitiveWordsHistoryService sensitiveWordsHistoryService) {
		this.messageMongo = messageMongo;
		this.sensitiveWordsHistoryService = sensitiveWordsHistoryService;
	}

	@Override
	public void run() {
		try {
			Long timeStamp = 0l;
			Messages messages = messageMongo.getMessageInMaxTime();
			if (messages != null) {
				timeStamp = messages.getTimestamp();
			}
			
			new MessageTransfer().save(timeStamp, System.currentTimeMillis(), messageMongo, sensitiveWordsHistoryService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}