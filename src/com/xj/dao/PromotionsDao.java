package com.xj.dao;

import java.util.List;

import com.xj.bean.Promotions;

/**
 * 促销dao
 * @author Administrator
 *
 */
public interface PromotionsDao extends MyBaseDao {
	
	public Integer addPromotion(Promotions promotions) throws Exception;
	
	public boolean updatePromotion(Promotions promotions) throws Exception;
	
	public List<Promotions> getPromotions(Integer communityId , String sort) throws Exception;
	
	public Promotions getPromotion(Integer communityId , String sort) throws Exception;
}
