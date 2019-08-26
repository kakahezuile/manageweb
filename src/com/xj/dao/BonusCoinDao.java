package com.xj.dao;

import com.xj.bean.CommunityFeature;
import com.xj.bean.Page;

/**
 * @author lence
 * @date 2015-7-15 下午01:47:08
 * @version 1.0
 */
public interface BonusCoinDao extends MyBaseDao {

	float findBonusRatio(Integer communityId, String emobIdShop) throws Exception;

	/**
	 * 获取各个小区帮帮币配置
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	Page<CommunityFeature> getBonusCoinConfig(Integer pageNum, Integer pageSize) throws Exception;
	
	/**
	 * 搜索各个小区帮帮币配置
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	Page<CommunityFeature> getBonusCoinConfig(String query, Integer pageNum, Integer pageSize) throws Exception;

	/**
	 * 更新各小区帮帮币相关配置
	 * @param communityFeature
	 * @return
	 * @throws Exception 
	 */
	Boolean saveOrUpdateBonusCoinConfig(CommunityFeature communityFeature) throws Exception;

}