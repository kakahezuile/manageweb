package com.xj.mongo;

import java.util.List;

import com.xj.bean.Messages;

public interface MessageMongo {

	public void insertMessage(Messages messages) throws Exception;

	public List<Messages> getListMessage(String from, String to, Long start, Long end) throws Exception;

	public List<Messages> getListByText(Long start, Long end, String text) throws Exception;

	public Messages getMessageByUuid(String uuid) throws Exception;

	public List<Messages> getMessageByMsgId(String... msgId) throws Exception;

	public List<Messages> getListMessage(String to, Long time, boolean isMoreThan) throws Exception;

	public List<Messages> getListMessage(String from, String to, Long time) throws Exception;

	public List<Messages> getListMessage(String from, String to, Long time, boolean isMoreThan) throws Exception;

	public List<Messages> getListMessage(String from, String to) throws Exception;

	public List<Messages> getListMessageNext(String from, String to, Long time) throws Exception;

	public Messages getMessageInMaxTime() throws Exception;

	public List<Messages> getListmessage(String to) throws Exception;

	public List<Messages> getListMessages(String type) throws Exception;
}