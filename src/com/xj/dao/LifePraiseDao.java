package com.xj.dao;

import java.util.List;

import com.xj.bean.LifeCircleNumer;
import com.xj.bean.LifeCircleTopVO;
import com.xj.bean.LifePraise;
import com.xj.bean.LifePraise2;
import com.xj.bean.LifePraiseContent;
import com.xj.bean.Page;


public interface LifePraiseDao extends MyBaseDao{
	
	public Integer addLifePraise(LifePraise lifePraise) throws Exception;
	
	public LifePraise getLifePraise(String from , String to , Integer start , Integer end) throws Exception;
	
	public Page<LifePraiseContent> getLifepraises(String to , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public List<LifePraise2> getLifePraises(String to , Integer time) throws Exception;
	
	public LifeCircleNumer getLifeCircleNumber(String to , Integer createTime) throws Exception;
	
	public List<LifePraiseContent> getLifePraises(Integer lifeCircleId) throws Exception;
	
	public Integer getUserCharacterValues(Integer start , Integer end , String emobId , Integer communityId) throws Exception; // 周人品值
	
	public Integer getUserCount(Integer start , Integer end , Integer sum , Integer communityId) throws Exception; // 周排行
	
	public List<LifeCircleTopVO> getLifeTop(Integer start , Integer end , Integer communityId) throws Exception; // 周排行列表
	
	
}
