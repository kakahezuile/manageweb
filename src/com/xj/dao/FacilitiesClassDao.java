package com.xj.dao;

import java.util.List;

import com.xj.bean.FacilitiesClass;

public interface FacilitiesClassDao extends MyBaseDao{
	/**
	 * 添加周边分类
	 * @param facilitiesClass
	 * @return
	 * @throws Exception
	 */
	public Integer insert(FacilitiesClass facilitiesClass) throws Exception;
	
	/**
	 * 修改周边分类   字段为null 则不修改
	 * @param facilitiesClass
	 * @return
	 * @throws Exception
	 */
	public boolean updateFacilities(FacilitiesClass facilitiesClass) throws Exception;
	
	/**
	 * 根据分类id 获取分类信息
	 * @param facilitiesClassId
	 * @return
	 * @throws Exception
	 */
	public FacilitiesClass getFacilitiesClass(Integer facilitiesClassId) throws Exception;
	
	/**
	 * 根据小区id 获取分类列表
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public List<FacilitiesClass> getListFacilitiesClassWithCommunityId(Integer communityId) throws Exception;
	
	
}
