package com.xj.dao;

import java.util.List;

import com.xj.bean.Communities;
import com.xj.bean.CommunityService;
import com.xj.bean.CommunityRelationAndName;

public interface CommunityRelationDao extends MyBaseDao {
	/**
	 * 为小区绑定模块
	 * @param communityRelation
	 * @return
	 * @throws Exception
	 */
	public Integer addRelation(CommunityService communityRelation) throws Exception;
	
	/**
	 * 根据小区id获取绑定模块列表
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public List<CommunityService> getRelationList(Integer communityId) throws Exception;
	
	public boolean updateRelation(CommunityService communityRelation) throws Exception;
	
	
	/**
	 * 根据小区id  模块id 判断当前小区是否绑定当前模块
	 * @param communityId
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public CommunityService isEmpty(Integer communityId , Integer categoryId) throws Exception;
	
	/**
	 * 根据小区id 模块id 版本id 判断 当前版本下 小区是否绑定当前模块
	 * @param communityId
	 * @param categoryId
	 * @param appVersionId
	 * @return
	 * @throws Exception
	 */
	public CommunityService isEmpty(Integer communityId , Integer categoryId , Integer appVersionId) throws Exception;
	
	
	public boolean updateRelationByCommunityIdAndCategoryId(Integer communityId , Integer categoryId ) throws Exception;
	
	public boolean updateRelationByCommunityIdAndCategoryId(Integer communityId , Integer categoryId , Integer appVersionId ) throws Exception;
	
	/**
	 * 根据小区id 获取绑定模块内容集合
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(Integer communityId) throws Exception;
	
	/**
	 * 根据小id 版本id 获取小区绑定模块集合
	 * @param communityId
	 * @param appVersionId
	 * @return
	 * @throws Exception
	 */
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(Integer communityId , Integer appVersionId) throws Exception;
	
	/**
	 * 根据最后修改时间  小区id 版本id 获取 小区绑定模块集合
	 * @param communityId
	 * @param appVersionId
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(Integer communityId , Integer appVersionId , Integer time) throws Exception;

	public Communities getCommunity(Integer communityId)throws Exception;
	
	public Integer getMaxTime(Integer communityId)throws Exception;
}
