package com.xj.dao;

import java.util.List;

import com.xj.bean.ActivityPhoto;

public interface ActivityPhotoDao extends MyBaseDao {
	/**
	 * 添加活动图片
	 * @param activityPhoto
	 * @return
	 * @throws Exception
	 */
	public Integer addPhoto(ActivityPhoto activityPhoto) throws Exception;
	/**
	 * 根据活动id获取图片列表
	 * @param activityId
	 * @return
	 * @throws Exception
	 */
	public List<ActivityPhoto> getPhotoList(Integer activityId) throws Exception;
	/**
	 * 修改活动图片
	 * @param activityPhoto
	 * @return
	 * @throws Exception
	 */
	public boolean updatePhoto(ActivityPhoto activityPhoto) throws Exception;
	
}
