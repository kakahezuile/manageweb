package com.xj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.HomeTips;
import com.xj.dao.ShopDao;


@Service("homeTipsService")
public class HomeTipsService {
	
	@Autowired
	private LifeCircleService lifeCircleService;
	
	@Autowired
	private ShopDao shopDao;
	
	/**
	 * 应用首页 数字提示
	 * @param communityId
	 * @param emobId
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public HomeTips getTips(Integer communityId , String emobId , Integer time) throws Exception{
		HomeTips homeTips = new HomeTips();
		Integer num = lifeCircleService.getLifeCircleNum(communityId, emobId, time);
		homeTips.setLifeCircleCount(num);
		int newTime = shopDao.getNewItem(communityId, time, "2");
		homeTips.setFastCount(newTime);
		return homeTips;
	}
}
