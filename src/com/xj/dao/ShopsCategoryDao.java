package com.xj.dao;

import java.util.List;

import com.xj.bean.Page;
import com.xj.bean.ShopsAndCategory;
import com.xj.bean.ShopsCategory;

public interface ShopsCategoryDao extends MyBaseDao {
	
	public Integer addShopsCategory(ShopsCategory shopsCategory) throws Exception;
	
	public boolean updateShopsCategory(ShopsCategory shopsCategory) throws Exception;
	
	public List<ShopsCategory> getShopsCategoryList(Integer catId) throws Exception;
	
	public Page<ShopsAndCategory> getShopsAndCategoryList(Integer communityId , Integer categoryId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public ShopsCategory getShopsCategory(Integer shopId , Integer catId) throws Exception;
	
	public List<ShopsCategory> getShopsCategoryByShopId(Integer shopId) throws Exception;
}
