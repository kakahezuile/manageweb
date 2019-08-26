package com.xj.timer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xj.dao.MyUserDao;
import com.xj.push.Push2ClientBuilder;
import com.xj.push.apns.IApnsService;
import com.xj.push.apns.model.Feedback;

/**
 * 强卸载了app的用户token置空
 */
public class ApnsFeedBackPull {
	
	@Autowired
	private MyUserDao myUserDao;
	
	public void execute() {
		IApnsService service = Push2ClientBuilder.getApnsService();
		List<Feedback> feedbacks = service.getFeedbacks();
		if(CollectionUtils.isEmpty(feedbacks)){
			return ;
		}
		List<String> tokens = new ArrayList<String>();
		
		for (Feedback feedback : feedbacks) {
			tokens.add(feedback.getToken());
		}
		
		myUserDao.updateUserDeviceTokenToNull(tokens);
	}
}