package com.xj.dao;

import java.util.List;

import com.xj.bean.LifePhoto;

public interface LifePhotoDao extends MyBaseDao{
	
	public Integer addLifePhoto(LifePhoto lifePhoto) throws Exception;
	
	public boolean updateLifePhoto(LifePhoto lifePhoto) throws Exception;
	
	public List<LifePhoto> getLifephotos(Integer lifeCircleId) throws Exception;
}
