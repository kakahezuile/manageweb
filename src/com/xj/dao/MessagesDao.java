package com.xj.dao;

import java.util.List;

import com.xj.bean.Messages;
import com.xj.bean.Page;

public interface MessagesDao extends MyBaseDao {
	
	/**
	 * 保存一条聊天记录
	 * @param messages
	 * @return 成功与否
	 * @throws Exception
	 */
	boolean save(Messages messages) throws Exception;
	
	/**
	 * 获取聊天记录
	 * @param messageFrom 消息发起人
	 * @param messageTo 消息接收人
	 * @param minTime 最早发送时间
	 * @param maxTime 最终发送时间
	 * @return 消息列表
	 * @throws Exception
	 */
	List<Messages> getListAsTime(String messageFrom , String messageTo , Integer minTime , Integer maxTime) throws Exception;
	
	Page<Messages> getListAsTimeWithPage(String messageFrom , String messageTo , Integer minTime , Integer maxTime , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
}