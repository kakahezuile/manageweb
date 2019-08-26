package com.xj.dao;

import java.util.List;

import com.xj.bean.Page;
import com.xj.bean.ShopLimit;
import com.xj.bean.Shops;
import com.xj.bean.ShopsPhone;

public interface ShopsDao extends MyBaseDao {

	public int findIdByName(String name);

	/**
	 * 根据小区id获取店铺列表
	 * 
	 * @param communityId 小区id
	 * @return 店铺列表
	 */
	public List<Shops> findAllById(Integer communityId);

	/**
	 * 根据小区id及sort 获取店铺列表
	 * 
	 * @param communityId
	 * @param sort
	 * @return 店铺列表
	 */
	public List<Shops> findAllBySort(Integer communityId, String sort);

	/**
	 * 修改店铺信息
	 * 
	 * @param shopsBean
	 * @return
	 * @throws Exception
	 */
	public boolean updateShops(Shops shopsBean) throws Exception;

	/**
	 * 添加店铺信息
	 * 
	 * @param shopsBean
	 * @return
	 */
	public int insert(Shops shopsBean);

	public Page<Shops> getShopsListById(Integer communityId, Integer pageNum, Integer pageSize, Integer navNum) throws Exception;

	public Page<Shops> getShopsListByText(Integer communityId, String text, Integer pageNum, Integer pageSize, Integer navNum, Integer type) throws Exception;

	public String getShopsSortWithUserId(Integer userId) throws Exception;

	public Shops getShopsByShopId(Integer shopId) throws Exception;

	public Integer getShopsIdByEmobId(String emobId) throws Exception;

	public Shops getShopsByFid(String authCode) throws Exception;

	public Integer addShops(Shops shops) throws Exception;

	public Page<Shops> findAllBySortAndCommunityId(Integer communityId, String sort, Integer pageNum, Integer pageSize, Integer nvm) throws Exception;

	/**
	 * 查询 黄页 商家列表
	 */
	public Page<ShopsPhone> findAllLikeShopsCommunityId(Integer communityId, String sort, Integer pageNum, Integer pageSize, Integer nvm, String shop) throws Exception;

	public Shops getShopsByEmobId(String emobId) throws Exception;

	/**
	 * 删除 商家
	 */
	public boolean delShops(Long id);

	/**
	 * 查询未开通服务
	 */
	public Page<Shops> findNoneWordShops(Integer communityId, String status, Integer pageNum, Integer pageSize, Integer navNum) throws Exception;

	public ShopLimit shopLimit(Integer communityId, String sort) throws Exception;

	public Integer upShop(String shopId, String deliverLimit, String deliverTime) throws Exception;
}