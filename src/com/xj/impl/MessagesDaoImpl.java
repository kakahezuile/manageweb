package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Messages;
import com.xj.bean.Page;
import com.xj.dao.MessagesDao;

@Repository("messagesDao")
public class MessagesDaoImpl extends MyBaseDaoImpl implements MessagesDao{

	@Override
	public boolean save(Messages messages) throws Exception {
		return this.save(messages, null) > 0;
	}
	
	public List<Messages> getListAsTime(String from , String to , Integer minTime , Integer maxTime){
		String sql = "SELECT * FROM  messages WHERE 1 = 1 and message_from = ? and message_to = ? and timestamp >=? and timestamp <=?";
		List<Object> params = new ArrayList<Object>();
		params.add(from);
		params.add(to);
		params.add(minTime);
		params.add(maxTime);
		try {
			return this.getList(sql,params.toArray() , Messages.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<Messages> getListAsTimeWithPage(String messageFrom,
			String messageTo, Integer minTime, Integer maxTime,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT * FROM  messages WHERE 1 = 1 and timestamp >=? and timestamp <=?  and ((message_from = ? and message_to = ?) or (message_from = ? and message_to = ?)) order by timestamp";
		List<Object> params = new ArrayList<Object>();
		params.add(minTime);
		params.add(maxTime);
		params.add(messageFrom);
		params.add(messageTo);
		params.add(messageTo);
		params.add(messageFrom);
		return this.getData4Page(sql, params.toArray(), pageNum, pageSize, nvm, Messages.class);
	}
}