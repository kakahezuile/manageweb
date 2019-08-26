package com.xj.dao;



import java.util.List;

import com.xj.bean.CatIdNumber;
import com.xj.bean.CommunityShops;
import com.xj.bean.ItemCategory;
import com.xj.bean.Page;
import com.xj.bean.ShopItem;
import com.xj.bean.ShopItemAndShopName;
import com.xj.bean.Shops;

/**
 * Created by dongquan on 14/12/26.
 */
public interface ShopDao extends MyBaseDao {
	
	/**
	 * 根据shopId获取店铺详细信息
	 * @param shopId
	 * @return 店铺详细信息
	 * @throws Exception
	 */
    public List<ShopItem> findAllById(Long shopId) throws Exception;
    
    public ShopItem getShopItemByServiceId(Integer serviceId) throws Exception;
    
    public List<ShopItemAndShopName> findAllBySortAndVersion(String sort,
			Integer communityId, String text , String version) throws Exception;
    
    public List<ShopItem> findAllByIdAndStatus(Long shopId , String status) throws Exception;
    
    public Integer getMaxLevel(Long shopId) throws Exception;
    
    public Integer getNewItem(Integer communityId , Integer time , String sort) throws Exception;
    
    /**
     * 更改店铺详细内容
     * @param shopItem
     * @return 
     * @throws Exception
     */
    public boolean updateShop(ShopItem shopItem) throws Exception;
    
    public boolean updateShopByName(ShopItem shopItem) throws Exception;
    
    /**
     * 添加店铺详细内容
     * @param shopItem
     * @return
     * @throws Exception
     */
    public Integer insertShop(ShopItem shopItem) throws Exception;
    
    public Page<ShopItem> finAllByIdWithPage(Long shopId , Integer catId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public List<ShopItemAndShopName> findAllBySort(String sort , Integer communityId , String text) throws Exception;
    
    public Page<ShopItemAndShopName> findAllByWIthPageAndName(Integer communityId , Long ShopId  , Integer catId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<ShopItem> findAllByCatId(Integer catId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<ShopItem> findAllByCatId(String status , Integer catId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public List<ShopItem> findAllByCatIdGroupBy(Integer catId) throws Exception;
    
    public List<ShopItem> getShopItemList(Integer catId , String serviceName) throws Exception;
    
    public boolean updateShopByCatIdAndServiceName(ShopItem shopItem) throws Exception;
    
    public List<ShopItem> getShopItemByParam(Integer shopId , Integer catId , String serviceName) throws Exception;
    
    public Page<ShopItemAndShopName> findAllBySort(String sort,
			Integer communityId, String text , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<ShopItemAndShopName> findAllBySortAndVersion(String sort,
			Integer communityId, String text , String version , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    /**
     * 查询维修项目
     */
    public List<ItemCategory> getItemCategory(long shopId) throws Exception;
    
    public ShopItem getShopItem(Integer serviceId) throws Exception;
    
    public boolean updateShopItemByVersion(ShopItem shopItem) throws Exception;

	public Page<ShopItem> getUpShopItem() throws Exception;
	
	public Page<ShopItem> getShopItemByCommunityIdAndCatId(Integer communityId , Integer catId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public Page<ShopItem> getShopByShopName(Integer communityId , String serviceName , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public Integer getBrandCount(Long shopId) throws Exception;
	
	public List<ShopItem> getShopItemByCatId(Integer catId) throws Exception;
	
	public List<CatIdNumber> getCatIdNumber(Long shopId) throws Exception;
	
	public List<ShopItem> getShopItems() throws Exception;
	
	public List<ShopItem> getShopItemByAttrId( Integer attrId , Integer catId) throws Exception;
	
	public List<ShopItem> getShopItemByAttrId(Long shopId , Integer catId , Integer attrId) throws Exception;
	
	public boolean updateShopItemByAttrId(Integer attrId , Integer level)throws Exception;
	
	public boolean updateShopItemByAttrId(ShopItem shopItem) throws Exception;
	
	public List<ShopItem> getCountByCutId(Integer catId)throws Exception;
	
	public List<ShopItem> getShopItems(Long shopId , Integer catId , String status) throws Exception;

	public List<Shops> getShopList()throws Exception;

	public Integer upCommShops(CommunityShops shops)throws Exception;

	public CommunityShops getShopName(Integer communityId)throws Exception;

}
