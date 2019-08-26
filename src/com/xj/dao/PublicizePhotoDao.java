package com.xj.dao;

import java.util.List;

import com.xj.bean.PublicizePhotos;

public interface PublicizePhotoDao extends MyBaseDao{
	
	public Integer addPublicizePhoto(PublicizePhotos publicizePhotos) throws Exception;
	
	public boolean updatePublicizePhoto(PublicizePhotos publicizePhotos) throws Exception;
	
	public List<PublicizePhotos> getPhotos(Integer communityId) throws Exception;
	
	public List<PublicizePhotos> getPhotos(Integer communityId , Integer serviceId) throws Exception;
	
	public List<PublicizePhotos> getPhotos(Integer communityId , Integer serviceId , String photoModule) throws Exception;
}
