package com.xj.dao;


import java.util.List;
import java.util.Map;

import com.xj.bean.Facilities;
import com.xj.bean.FacilitiesClass;
import com.xj.bean.FacilitiesList;
import com.xj.bean.Page;

/**
 * update by lidongquan on 14/12/30.
 */
public interface FacilitiesDao extends MyBaseDao{
	/**
	 * 根据小区id获取设备信息列表
	 * @param communityId
	 * @return 设备信息集合
	 */
    public List<Facilities> findAllById(String communityId);
    
    /**
     * 根据小区id及sort获取小区设备信息
     * @param communityId
     * @param sort
     * @return 设备信息集合
     */
    public List<Facilities> findAllBySort(String communityId, String sort);
    
    /**
     * 添加设备信息
     * @param facilities
     * @return 成功与否
     * @throws Exception
     */
    public Integer addFacilities(Facilities facilities) throws Exception;
    
    /**
     * 修改设备信息
     * @param facilities
     * @return 成功与否
     * @throws Exception
     */
    public boolean updateFacilities(Facilities facilities) throws Exception;
    
    public Page<Facilities> findFacilitiesByTag(Integer communityId , String sort , Integer type , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<Facilities> findeFacilitiesByClassId(Integer communityId , Integer facilitiesClassId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;


    /**
     * 查询周边
     */
    public Page<FacilitiesList> selectFacilitiesName(Integer communityId ,Integer type , Integer pageNum , Integer pageSize , Integer nvm,String facilitiesName) throws Exception;
    
    
    /**
     * 按类别查询周边
     */
    public Page<FacilitiesList> selectFacilitiesType(Integer communityId ,String type , Integer pageNum , Integer pageSize , Integer nvm,String facilitiesName) throws Exception;
    
    /**
     * 统计周边 商家数量
     */
    public Map<String,Integer> conntFacilitiesTop(Integer communityId) throws Exception;
    
    public Integer getNumberByAdminId(Integer adminId , Integer startTime , Integer endTime) throws Exception;

	public List<FacilitiesClass> getFacilitiesType()throws Exception;

}
