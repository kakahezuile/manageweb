package com.xj.dao;

import java.util.List;

import com.xj.bean.Activities;
import com.xj.bean.ActivitiesDate;
import com.xj.bean.ActivitiesSimple;
import com.xj.bean.ActivitiesTop;
import com.xj.bean.ActivitiesUserList;
import com.xj.bean.ActivityPhoto;
import com.xj.bean.ActivityUpdateBean;
import com.xj.bean.AddActivity;
import com.xj.bean.Page;
import com.xj.bean.Users;
import com.xj.bean.WebImActivies;

/**
 * 活动dao
 */
public interface ActivitiesDao extends MyBaseDao{
	/**
	 * 添加活动
	 * @param activitiesBean
	 * @return
	 */
    public int addAcitivity(Activities activitiesBean);
    /**
     * 修改活动
     * @param activityId
     * @param activityUpdateBean
     * @return
     */
    public boolean modifyActivityState(Integer activityId, ActivityUpdateBean activityUpdateBean);
    /**
     * 获取活动集合根据ownerId
     * @param ownerId
     * @return
     * @throws Exception
     */
    public List<Activities> getListByEmobId(String emobGroupId , String ownerId) throws Exception;
    
    /**
     * 根据环信提供的groupId获取活动集合
     * @param groupId
     * @return
     * @throws Exception
     */
    public List<Activities> getListByGroupId(String groupId) throws Exception;
    /**
     * 分页查询 - 根据小区id以及创建者获取活动列表
     * @param communityId 小区id
     * @param ownerId 创建者
     * @param pageNum 第N页
     * @param pageSize 每页显示个数
     * @param nvm
     * @return
     * @throws Exception
     */
    public Page<Activities> getListWithPage(Integer communityId ,String ownerId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    /**
     * @Title: getListWithPageSimple
     * @Description: 根据小区id获取活动列表，简化版：为后台水军使用(现在替换getListWithPage方法)
     * @param communityId
     * @param pageNum
     * @param pageSize
     * @param nvm
     * @return Page<Activities>
     * @throws Exception
     * @author: 刘振
     * @date: 2015-11-5
     */
    public Page<ActivitiesSimple> getListWithPageSimple(Integer communityId  , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    /**
     * 修改活动内容 - 为null时不修改
     * @param activities
     * @return
     * @throws Exception
     */
    public boolean updateActivies(Activities activities) throws Exception;
    /**
     * 分页查询 - 根据小区id以及 活动类型获取活动列表
     * @param communityId 小区id
     * @param status 活动类型
     * @param pageNum 页
     * @param pageSize 每页显示个数
     * @param nvm
     * @return
     * @throws Exception
     */
    public Page<Activities> getListWithPageByCommunityId(Integer communityId , String status , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    
    
    public Page<WebImActivies> getListByWebIm(Integer communityId , String status , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    /**
     * 分页查询 - 模糊查询：根据关键字以及小区id查询活动列表
     * @param text
     * @param communityId
     * @param status
     * @param pageNum
     * @param pageSize
     * @param nvm
     * @return
     * @throws Exception
     */
    public Page<WebImActivies> getListInWebImByText(String text , Integer communityId , String status , Integer pageNum , Integer pageSize , Integer nvm ) throws Exception;
    /**
     * 分页查询  - 模糊查询：根据关键字以及小区id 和活动类型查询活动列表
     * @param text
     * @param communityId
     * @param status
     * @param pageNum
     * @param pageSize
     * @param nvm
     * @return
     * @throws Exception
     */
    
    public Page<WebImActivies> getActivitiesText(String text , Integer communityId , String status , Integer pageNum , Integer pageSize , Integer nvm ) throws Exception;
    
    /**
     * 分页查询 - 模糊查询 ： 根据关键词 小区id 活动类型 活动状态 查询活动列表
     * @param text
     * @param communityId
     * @param status
     * @param type
     * @param pageNum
     * @param pageSize
     * @param nvm
     * @return
     * @throws Exception
     */
    public Page<Activities> getActivitiesByStatusAndText(String text , Integer communityId , String status , String type , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<ActivitiesSimple> getActivitiesByStatusAndTextSimple(String text , Integer communityId , String status , String type , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    /**
     * 活动使用量统计
     */
    public ActivitiesTop getActivitiesTop(Integer communityId,Integer startTime, Integer endTime)throws Exception;
    /**
     * 活动统计列表
     */
	public List<ActivitiesTop> getActivitiesList(Integer communityId, Integer startTime, Integer endTime,List<Integer> sqlTime)throws Exception;
	
	public Page<ActivitiesDate> getActivitiesDateList(Integer communityId,Integer pageNum, Integer pageSize, Integer startTime, Integer endTime) throws Exception;
	
	public Page<ActivitiesTop> getActivitiesDetailList(Integer activitiesId, Integer pageNum, Integer pageSize, Integer startTimeInt, Integer endTimeInt)  throws Exception;
	
	/**
	 * 活动详情
	 */
	public WebImActivies getActivitiesDetail(Integer activitiesId) throws Exception;
	
	public List<Users> getListUser(String emobGroupId)throws Exception;
	
	public AddActivity getAddActivity(Integer communityId,Integer startTime,Integer endTime)throws Exception;
	
	public List<ActivitiesDate> newActivitiesList(Integer communityId, Integer startTimeInt, Integer endTimeInt)throws Exception;
	
	public List<ActivitiesUserList> newActivitiesListDetail(String emobGroupId, Integer startTimeInt, Integer endTimeInt)throws Exception;
	
	public boolean updateActivity(String emobGroupId ,String activity_title)throws Exception; 
	
	/**
	 * 根据活动id获取活动
	 * @param parseInt
	 * @return
	 * @throws Exception 
	 */
	public Activities getActivitiesById(int parseInt) throws Exception;
	
	/**
	 * @Title: getActivitisByIdSimple
	 * @Description: 根据活动id获取活动详情，简化版：为后台水军使用(现在替换getActivitiesById方法)
	 * @param activityId
	 * @return Activities
	 * @throws Exception
	 * @author: 刘振
	 * @date: 2015-11-5
	 */
	public ActivitiesSimple getActivitiesByIdSimple(int activityId) throws Exception;
	/**
	 * 根据活动id获取活动图片
	 * @param activityId
	 * @return
	 * @throws Exception 
	 */
	public List<ActivityPhoto> getActivitiePhotos(Integer activityId) throws Exception;
	
	/**
	 * 获取小区模块专属属性
	 * @param communityId
	 * @param type
	 * @param service
	 * @return
	 * @throws Exception 
	 */
	public String getActivityThemePhoto(Integer communityId, String type,
			String service) throws Exception; 
	
}