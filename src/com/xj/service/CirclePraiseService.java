package com.xj.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.LifePraise;
import com.xj.bean.LifePraiseContent;
import com.xj.bean.Page;
import com.xj.bean.SinglePraise;
import com.xj.bean.UserPercent;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.LifeCircleDao;
import com.xj.dao.LifeCircleDetailDao;
import com.xj.dao.LifePraiseDao;
import com.xj.dao.MyUserDao;

@Service("circlePraiseService")
public class CirclePraiseService {
	
	@Autowired
	private LifePraiseDao lifePraiseDao;
	
	@Autowired
	private LifeCircleDao lifeCircleDao;
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private LifeCircleDetailDao lifeCircleDetailDao;
	
	/**
	 * 添加点赞
	 * @param lifePraise
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean addCirclePraise(LifePraise lifePraise , Integer status) throws Exception{
		boolean result = false;
		int resultId = 0;
		Calendar c1 = new GregorianCalendar();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0); 
		Calendar c2 = new GregorianCalendar(); 
		c2.set(Calendar.HOUR_OF_DAY, 23); 
		c2.set(Calendar.MINUTE, 59); 
		c2.set(Calendar.SECOND, 59);
		int start = (int)(c1.getTime().getTime() / 1000l);
		int end = (int)(c2.getTime().getTime() / 1000l);
		LifePraise lifePraise2 = lifePraiseDao.getLifePraise(lifePraise.getEmobIdFrom(), lifePraise.getEmobIdTo(), start, end);
		if(lifePraise2 == null){
			if(status == 1){
				
			}else if(status == 2){
				
			}
			resultId = lifePraiseDao.addLifePraise(lifePraise);
			if(resultId > 0){
				Users users = new Users();
				users.setEmobId(lifePraise.getEmobIdTo());
				boolean udpate = myUserDao.updateUserByEmobId(users);
				if(udpate){
					if(status == 1){
						result = lifeCircleDao.updateLifeCircle(lifePraise.getLifeCircleId());
					}else if(status == 2){
						result = lifeCircleDetailDao.updateLifeCircleDetail(lifePraise.getLifeCircleDetailId());
					}
				}
			}
		}
		
		if(resultId > 0){
			result = true;
		}
		return result;
	}
	/**
	 * 获取点赞列表
	 * @param communityId
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public SinglePraise getSinglePraise(Integer communityId , String emobId  , Integer pageNum , Integer pageSize , Integer nvm) throws Exception{
		SinglePraise singlePraise = new SinglePraise();
		UsersApp usersApp = myUserDao.getUserByEmobId(emobId);
		if(usersApp != null){
			singlePraise.setNickname(usersApp.getNickname());
			singlePraise.setCharacterValues(usersApp.getCharacterValues());
			singlePraise.setAvatar(usersApp.getAvatar());
		}
		UserPercent userPercent = myUserDao.getUserPercent(communityId , emobId);
		if(userPercent != null){
			Double percent = ((double)userPercent.getCharacterCount() / (double)userPercent.getUserCount()) * (double)100;
			if(userPercent.getUserCount() == 0){
				singlePraise.setCharacterPercent(new Double(0));
			}else{
				singlePraise.setCharacterPercent(percent);
			}
			
		}
		Page<LifePraiseContent> page = lifePraiseDao.getLifepraises(emobId, pageNum, pageSize, nvm);
		singlePraise.setList(page.getPageData());
		return singlePraise;
	}
}
