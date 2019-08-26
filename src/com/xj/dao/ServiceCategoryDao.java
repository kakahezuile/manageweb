package com.xj.dao;

import java.util.List;

import com.xj.bean.Services;

public interface ServiceCategoryDao extends MyBaseDao  {
	
	public Integer addCategory(Services serviceCategory) throws Exception;
	
	public boolean updateCategory(Services serviceCategory) throws Exception;
	
	public boolean deleteCategory(Integer categoryId) throws Exception;
	
	public List<Services> getCategoryList() throws Exception;
	
	public List<Services> getIsHaveCategoryList(Integer communityId) throws Exception;
	
	public List<Services> getIsHaveCategoryList(Integer communityId , Integer appVersionId) throws Exception;
	
	public List<Services> getIsHaveCategoryList(String emobId) throws Exception;
}
