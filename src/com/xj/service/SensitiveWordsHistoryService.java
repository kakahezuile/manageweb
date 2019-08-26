package com.xj.service;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xj.bean.Activities;
import com.xj.bean.Messages;
import com.xj.bean.SensitiveRunTime;
import com.xj.bean.SensitiveWords;
import com.xj.bean.SensitiveWordsHistory;
import com.xj.bean.UsersApp;
import com.xj.dao.ActivitiesDao;
import com.xj.dao.SensitiveRunTimeDao;
import com.xj.dao.SensitiveWordsDao;
import com.xj.dao.SensitiveWordsHistoryDao;
import com.xj.mongo.MessageMongo;

@Repository("sensitiveWordsHistoryService")
public class SensitiveWordsHistoryService {

	@Autowired
	private SensitiveWordsHistoryDao sensitiveWordsHistoryDao;

	@Autowired
	private SensitiveWordsDao sensitiveWordsDao;

	@Autowired
	private MessageMongo messageMongo;

	@Autowired
	private ActivitiesDao activitiesDao;

	@Autowired
	private UserService userService;

	@Autowired
	private SensitiveRunTimeDao sensitiveRunTimeDao;

	public void addSensitiveWordsHistory() throws Exception {
		Long startTime = 0l;
		long endTime = System.currentTimeMillis();
		SensitiveRunTime sensitiveRuntime = sensitiveRunTimeDao.getMaxSensitiveRuntime();
		if (sensitiveRuntime != null && sensitiveRuntime.getTimestamp() != null) {
			startTime = sensitiveRuntime.getTimestamp();
		}
		
		Iterator<SensitiveWords> it = sensitiveWordsDao.getSensitiveWordsList().iterator();
		while (it.hasNext()) {
			Iterator<Messages> itMessage = messageMongo.getListByText(startTime, endTime, it.next().getSensitiveWords()).iterator();
			while (itMessage.hasNext()) {
				Messages message = itMessage.next();
				String to = message.getMessageTo();
				if (isNumeric(to)) { // 是群组聊天
					List<Activities> listActivities = activitiesDao.getListByGroupId(to);
					if (listActivities != null && listActivities.size() > 0) {
						UsersApp user = userService.getUserByEmobId(message.getMessageFrom());
						Integer time = (int) (endTime / 1000l);
						
						SensitiveWordsHistory sensitiveWordsHistory = new SensitiveWordsHistory();
						sensitiveWordsHistory.setGroupName(listActivities.get(0).getActivityTitle());
						sensitiveWordsHistory.setGroupId(to);
						sensitiveWordsHistory.setCommunityId(user.getCommunityId());
						sensitiveWordsHistory.setCommunityName(user.getCommunityName());
						sensitiveWordsHistory.setHistoryTime((int) (message.getTimestamp() / 1000l));
						sensitiveWordsHistory.setStatus("untreated");
						sensitiveWordsHistory.setSensitiveWords(message.getMsg());
						sensitiveWordsHistory.setEmobId(message.getMessageFrom());
						sensitiveWordsHistory.setNickname(message.getNickname());
						sensitiveWordsHistory.setCreateTime(time);
						sensitiveWordsHistory.setUpdateTime(time);
						sensitiveWordsHistory.setUuid(message.getUuid());
						
						try {
							sensitiveWordsHistoryDao.addSensitiveWordsHistory(sensitiveWordsHistory);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		SensitiveRunTime sensitiveRunTime = new SensitiveRunTime();
		sensitiveRunTime.setTimestamp(endTime);
		sensitiveRunTimeDao.addSensitiveRunTime(sensitiveRunTime);
	}

	public void addSensitiveWordsHistory(Messages message) throws Exception {
		String msg = message.getMsg();
		if (StringUtils.isBlank(msg)) {
			return;
		}
		
		Iterator<SensitiveWords> it = sensitiveWordsDao.getSensitiveWordsList().iterator();
		while (it.hasNext()) {
			if (msg.indexOf(it.next().getSensitiveWords()) == -1) {
				continue;
			}
			
			String to = message.getMessageTo();
			if (isNumeric(to)) { // 是群组聊天
				List<Activities> listActivities = activitiesDao.getListByGroupId(to);
				if (listActivities != null && listActivities.size() > 0) {
					UsersApp user = userService.getUserByEmobId(message.getMessageFrom());
					Integer time = (int) (System.currentTimeMillis() / 1000l);
					
					SensitiveWordsHistory sensitiveWordsHistory = new SensitiveWordsHistory();
					sensitiveWordsHistory.setGroupName(listActivities.get(0).getActivityTitle());
					sensitiveWordsHistory.setGroupId(to);
					sensitiveWordsHistory.setCommunityId(user.getCommunityId());
					sensitiveWordsHistory.setCommunityName(user.getCommunityName());
					sensitiveWordsHistory.setHistoryTime((int) (message.getTimestamp() / 1000l));
					sensitiveWordsHistory.setStatus("untreated");
					sensitiveWordsHistory.setSensitiveWords(msg);
					sensitiveWordsHistory.setEmobId(message.getMessageFrom());
					sensitiveWordsHistory.setNickname(message.getNickname());
					sensitiveWordsHistory.setCreateTime(time);
					sensitiveWordsHistory.setUpdateTime(time);
					sensitiveWordsHistory.setUuid(message.getUuid());
					try {
						sensitiveWordsHistoryDao.addSensitiveWordsHistory(sensitiveWordsHistory);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			SensitiveRunTime sensitiveRunTime = new SensitiveRunTime();
			sensitiveRunTime.setTimestamp(message.getTimestamp());
			sensitiveRunTimeDao.addSensitiveRunTime(sensitiveRunTime);
		}
	}

	public boolean isNumeric(String str) {
		return Pattern.compile("[0-9]*").matcher(str).matches();
	}
}