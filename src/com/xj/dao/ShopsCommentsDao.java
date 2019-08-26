package com.xj.dao;



import com.xj.bean.Page;
import com.xj.bean.ShopsCommentsHistory;
import com.xj.bean.UserComments;

public interface ShopsCommentsDao extends MyBaseDao {
	
	public Page<UserComments> getUserComments(Integer communityId , String emobId , Integer pageNum , Integer pageSize) throws Exception;
	
	public ShopsCommentsHistory getShopsCommentsHistory(Integer communityId , String emobId) throws Exception;
}
