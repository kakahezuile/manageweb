package com.xj.dao;

import java.util.List;

import com.xj.bean.ItemCategory;

public interface ItemCategoryDao extends MyBaseDao{
	
	/**
	 * 添加分类
	 * @param itemCategory
	 * @return
	 * @throws Exception
	 */
	public Integer addItemCategory(ItemCategory itemCategory) throws Exception ;
	
	/**
	 * 获取分类列表
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getItemCategoryList() throws Exception;
	
	/**
	 * 修改分类  字段为null 则不修改
	 * @param itemCategory
	 * @return
	 * @throws Exception
	 */
	public boolean updateItemCategory(ItemCategory itemCategory) throws Exception;
	
	public boolean deleteItemCategory(ItemCategory itemCategory) throws Exception;
	
	/**
	 * 根据店铺类型获取分类列表
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getItemCategoryListByType(String type) throws Exception;
	
	/**
	 * 根据小区id、店铺类型  获取分类列表
	 * @param communityId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getItemCategoryListByType(Integer communityId , String type ) throws Exception;
	
	/**
	 * 根据小区id、店铺类型、版本id 获取分类列表
	 * @param communityId
	 * @param type
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getItemCategoryListByType(Integer communityId , String type , String version) throws Exception;
	
	public Integer maxCreateTime(Integer communityId , String version) throws Exception;
	
	/**
	 * 根据catId修改分类商品数量
	 * @param catId 分类id
	 * @param type 类型 -   1-加   2-减
	 * @return
	 * @throws Exception
	 */
	public boolean updateItemCategory(int catId , int type) throws Exception;
	
	public boolean updateItemCat(int catId , int num) throws Exception;
	
	/**
	 * 根据小区id 、店铺类型、版本id  获取分类列表
	 * @param communityId
	 * @param type
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getItemCategoryListByShop(Integer communityId , String type , String version) throws Exception;
}
