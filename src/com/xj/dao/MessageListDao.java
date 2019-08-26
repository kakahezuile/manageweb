package com.xj.dao;

import java.util.List;

import com.xj.bean.MessageList;

public interface MessageListDao extends MyBaseDao{
	
	public Integer addMessageList(MessageList messageList) throws Exception;
	
	public List<MessageList> getMessageList(String emobIdTo) throws Exception;
}
