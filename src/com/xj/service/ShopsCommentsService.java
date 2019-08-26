package com.xj.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Page;
import com.xj.bean.ShopsCommentsHistory;
import com.xj.bean.UserComments;
import com.xj.dao.ShopsCommentsDao;

@Service("shopsCommentsService")
public class ShopsCommentsService {
	
	@Autowired
	private ShopsCommentsDao shopsCommentsDao;
	
	public ShopsCommentsHistory getShopsCommentsHistory(Integer communityId , String emobId , Integer pageNum , Integer pageSize) throws Exception{
		ShopsCommentsHistory shopsCommentsHistory = shopsCommentsDao.getShopsCommentsHistory(communityId,emobId);
		Page<UserComments> list = shopsCommentsDao.getUserComments(communityId,emobId,pageNum,pageSize);
		shopsCommentsHistory.setList(list);
		return shopsCommentsHistory;
	}
	
	public Page<UserComments> getUserComments(Integer communityId , String emobId , Integer pageNum , Integer pageSize) throws Exception{
		Page<UserComments> page = shopsCommentsDao.getUserComments(communityId, emobId, pageNum, pageSize);
		return page;
	}
}
