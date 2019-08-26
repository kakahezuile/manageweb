package com.xj.mongo;

import java.util.List;

import com.xj.bean.Bulletin;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;

public interface PushMessageMongo {
	
	public void addPushMessage(PushMessage pushMessage) throws Exception;
	
	public boolean updatePushMessage(PushMessage pushMessage) throws Exception;
	
	public List<PushMessage> getPushMessages(String emobId , Integer time) throws Exception;
	
	public List<PushMessage> getPushMessages() throws Exception;
	
	public List<PushStatistics> getPushMessageByCode(Integer cmdCode , int page , int pageSize) throws Exception;
	
	public List<PushMessage> getPushMessages(Long messageId , int page , int pageSize) throws Exception;
	
	List<PushStatistics> getPushMessageByCodeWithComminuty(Integer cmdCode, int page, int pageSize) throws Exception;

	/**
	 * 获取需要重推的消息
	 */
	public List<Bulletin> getResendPushMessage();
}
