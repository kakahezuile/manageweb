package com.xj.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.Messages;
import com.xj.dao.MessagesDao;

//@Service("messagesService")
public class MessagesService {
	@Autowired
	private MessagesDao messagesDao;
	
	public boolean save(Messages m) throws Exception{
		return messagesDao.save(m);
	}
	
	public List<Messages> getListWithTime(String from , String to , Integer minTime , Integer maxTime) throws Exception{
		return messagesDao.getListAsTime(from , to , minTime , maxTime);
	}
}
