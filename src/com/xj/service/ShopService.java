package com.xj.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Attribute;
import com.xj.bean.ItemCategory;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.bean.ShopItemAndCategory;
import com.xj.bean.ShopItemAttribute;
import com.xj.dao.AttributeDao;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.ShopDao;

@Service("shopService")
public class ShopService {
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	
	@Autowired
	private AttributeDao attributeDao;
	
	/**
	 * v2版本 - 根据小区id 店铺id 商品状态获取 分类及商品列表
	 * @param communityId
	 * @param shopId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<ShopItemAndCategory> getShopItemByShopId2(Integer communityId , Long shopId , String status) throws Exception{
		List<ItemCategory> listCategory = itemCategoryDao.getItemCategoryListByShop(communityId , "2" , "v2");
		List<ShopItemAndCategory> list = new ArrayList<ShopItemAndCategory>();
		if(listCategory != null && listCategory.size() > 0){
			int len = listCategory.size();
			ItemCategory itemCategory = null;
			List<ShopItem> shopItems = null;
			ShopItemAndCategory shopItemAndCategory = null;
			for(int i = 0 ; i < len ; i++){
				itemCategory = listCategory.get(i);
				shopItems = shopDao.getShopItems(shopId, itemCategory.getCatId(), status);
				if(shopItems != null && shopItems.size() > 0){
					int length = shopItems.size();
					ShopItem shopItem = null;
					for(int j = 0 ; j < length ; j++){
						shopItem = shopItems.get(j);
						if("yes".equals(shopItem.getMultiattribute())){
							int catId = shopItem.getCatId();
							List<ShopItem> lists = shopDao.getShopItemByAttrId(shopId , catId , shopItem.getAttrId());
							shopItem.setList(lists);
						}
					}
				}
				shopItemAndCategory = new ShopItemAndCategory();
				shopItemAndCategory.setCatId(itemCategory.getCatId());
				shopItemAndCategory.setCatName(itemCategory.getCatName());
				shopItemAndCategory.setKeywords(itemCategory.getKeywords());
				shopItemAndCategory.setList(shopItems);
				list.add(shopItemAndCategory);
			}
		}
		return list;
	}
	
	public List<ShopItemAndCategory> getShopItemByShopId(Integer communityId,Long shopId,String status) throws Exception{
		List<ShopItem> listShopItem = null;
		ShopItemAndCategory shopItemAndCategory = null;
		List<ShopItem> listTemp = new ArrayList<ShopItem>();
		List<ShopItemAndCategory> listResult = new ArrayList<ShopItemAndCategory>();
		listShopItem = shopDao.findAllByIdAndStatus(shopId,status);
		List<ItemCategory> listCategory = itemCategoryDao.getItemCategoryListByShop(communityId , "2" , "v2");
		if(listShopItem != null && listShopItem.size() > 0){
			int len = listShopItem.size();
			ShopItem shopItem = null;
			for(int i = 0 ; i < len ;i++){
				shopItem = listShopItem.get(i);
				if("yes".equals(shopItem.getMultiattribute())){
					int catId = shopItem.getCatId();
					List<ShopItem> list = shopDao.getShopItemByAttrId(shopItem.getAttrId() , catId);
					shopItem.setList(list);
				}
			}
		}
		if(listCategory != null && listCategory.size() > 0){
			int len = listCategory.size();
			for (int i = 0; i < len; i++) {
				Integer catId = listCategory.get(i).getCatId();
				String catName = listCategory.get(i).getCatName();
				if (shopItemAndCategory == null) {
					shopItemAndCategory = new ShopItemAndCategory();
					shopItemAndCategory.setCatId(catId);
					shopItemAndCategory.setCatName(catName);
					shopItemAndCategory.setKeywords(listCategory.get(i).getKeywords());
				}
				for (int j = 0; j < listShopItem.size(); j++) {
					if (catId == listShopItem.get(j).getCatId()) {
						
						listTemp.add(listShopItem.get(j));
					}
				}
				if (shopItemAndCategory != null) {
					shopItemAndCategory.setList(listTemp);
				}
				if (shopItemAndCategory != null) {
					listResult.add(shopItemAndCategory);
					shopItemAndCategory = null;
					listTemp = new ArrayList<ShopItem>();
				}

			}
		}
		
		return listResult;
	}
	
	private ShopItem itemToCategory(ShopItemAttribute shopItemCategory){
		ShopItem shopItem = new ShopItem();
		shopItem.setBrandId(shopItemCategory.getBrandId());
		shopItem.setCatId(shopItemCategory.getCatId());
		shopItem.setCreateTime(shopItemCategory.getCreateTime());
		shopItem.setCurrentPrice(shopItemCategory.getCurrentPrice());
		shopItem.setLevel(shopItemCategory.getLevel());
		shopItem.setMultiattribute(shopItemCategory.getMultiattribute());
		shopItem.setOriginPrice(shopItemCategory.getOriginPrice());
		shopItem.setServiceId(new Long(shopItemCategory.getServiceId()));
		shopItem.setServiceImg(shopItemCategory.getServiceImg());
		shopItem.setServiceName(shopItemCategory.getServiceName());
		shopItem.setShopId(shopItemCategory.getShopId());
		shopItem.setStatus(shopItemCategory.getStatus()); 
		shopItem.setStock(shopItemCategory.getStock());
		shopItem.setVersion(shopItemCategory.getVersion());
		shopItem.setPurchase(shopItemCategory.getPurchase());
		
		return shopItem;
	}
	
	public ResultStatusBean updateShopItem(ShopItemAttribute shopItemCategory , Long shopId) throws Exception{
		ResultStatusBean resultTemp = new ResultStatusBean();
		ShopItem shopItem = itemToCategory(shopItemCategory);
		
		int isUpdate = shopItemCategory.getIsUpdate();
		if (isUpdate == 1) { // 修改店铺明细
			if(shopItem.getLevel() != null && shopItemCategory.getAttrId() != null){ // 置顶操作
				int level = shopDao.getMaxLevel(shopId);
				level+=1;
				
				boolean update = shopDao.updateShopItemByAttrId(shopItemCategory.getAttrId(), level);
				if(update){
					resultTemp.setStatus("yes");
					shopItem.setLevel(null);
				}
			}else{
				if("yes".equals(shopItemCategory.getMultiattribute())){
					shopDao.updateShop(shopItem);
					ShopItem shopItem2 = null;
					List<Attribute> list = shopItemCategory.getList();
					if(list != null && list.size() > 0){
						Iterator<Attribute> iterator = list.iterator();
						Attribute attribute = null;
						List<Integer> ids = new ArrayList<Integer>();
						while(iterator.hasNext()){
							attribute = iterator.next();
							if(attribute.getIsUpdate() == 1){ // 修改
								shopItem2 = new ShopItem();
								shopItem2.setServiceId(new Long(attribute.getServiceId()));
								shopItem2.setAttrName(attribute.getAttrName());
								shopItem2.setCurrentPrice(attribute.getPrice());
								if(attribute.getCatId() != null){
									shopItem2.setCatId(attribute.getCatId());
								}
								shopItem2.setUpdateTime((int)(System.currentTimeMillis() / 1000));
								boolean result = shopDao.updateShop(shopItem2);
								System.out.println(result);
							}else if(attribute.getIsUpdate() == 0){
								int catId = 0;
								if(shopItem.getCatId() != null){
									catId = shopItem.getCatId();
								}
								List<ShopItem> lists = shopDao.getShopItemByAttrId(attribute.getAttrId() , catId);
								String url = "";
								if(lists != null && lists.size() > 0){
									url = lists.get(0).getServiceImg();
								}
								shopItem2 = new ShopItem();
								shopItem2.setAttrName(attribute.getAttrName());
								shopItem2.setCurrentPrice(attribute.getPrice());
								shopItem2.setServiceName(shopItem.getServiceName());
								shopItem2.setAttrId(attribute.getAttrId());
								shopItem2.setCreateTime(System.currentTimeMillis() / 1000);
								shopItem2.setServiceImg(url);
								shopItem2.setShopId(shopId);
								shopItem2.setCatId(shopItem.getCatId());
								shopItem2.setBrandId(shopItem.getBrandId());
								shopItem2.setMultiattribute("yes");
								shopItem2.setStatus("available");
								shopItem2.setVersion(0);
								int id = shopDao.insertShop(shopItem2);
								ids.add(id);
							}
							
						}
						resultTemp.setStatus("yes");
						resultTemp.setShopItemIds(ids);
					}
				}else{
					ShopItem shopItem2 = shopDao.getShopItem(shopItem.getServiceId().intValue());
					String status = shopItem2.getStatus();
					shopItem.setUpdateTime((int)(System.currentTimeMillis() / 1000));
					boolean updateResult = shopDao.updateShop(shopItem);
					if (updateResult) {
						if(!"available".equals(status)){
							if("available".equals(shopItem.getStatus())){
								itemCategoryDao.updateItemCategory(shopItem2.getCatId(), 1);
							}
						}else{
							if(!"available".equals(shopItem.getStatus())){
								itemCategoryDao.updateItemCategory(shopItem2.getCatId(), 2);
							}
						}
						resultTemp = new ResultStatusBean();
						resultTemp.setMessage("update-"
								+ shopItem.getServiceId());
						resultTemp.setStatus("yes");
					} else {
						resultTemp = new ResultStatusBean();
						resultTemp.setMessage(shopItem.getServiceId()
								+ "");
						resultTemp.setStatus("no");
					}
				}
			}
			
			
		} else if (isUpdate == 0) { // 增加店铺明细
			int type = 1;
			if(shopItem.getBrandId() != null && shopItem.getBrandId() == 1){
				int count = shopDao.getBrandCount(shopId);
				if(count >= 8){
					resultTemp = new ResultStatusBean();
					resultTemp.setMessage("特卖商品不能大于8个");
					resultTemp.setStatus("no");
					type = -1;
				}
			}else{
				if(shopItem.getLevel() != null){
					int level = shopDao.getMaxLevel(shopId);
					level+=1;
					shopItem.setLevel(level);
				}
			}
			if(type == 1){
				if(shopItem.getLevel() != null){
					int level = shopDao.getMaxLevel(shopId);
					level+=1;
					shopItem.setLevel(level);
				}
				
				
				Attribute attribute2 = new Attribute();
				int result = attributeDao.addAttribute(attribute2);
				
				resultTemp = new ResultStatusBean();
				if (result > 0) {
					if("yes".equals(shopItemCategory.getMultiattribute())){
						
						List<Attribute> list = shopItemCategory.getList();
						Iterator<Attribute> iterator = list.iterator();
						Attribute attribute = null;
						List<Integer> resultIds = new ArrayList<Integer>();
						while(iterator.hasNext()){
							attribute = iterator.next();
							shopItem.setAttrName(attribute.getAttrName());
							shopItem.setCurrentPrice(attribute.getPrice());
							shopItem.setAttrId(result);
							shopItem.setCreateTime(System.currentTimeMillis() / 1000);
							shopItem.setShopId(shopId);
							shopItem.setStatus("available");
							shopItem.setVersion(0);
							int id = shopDao.insertShop(shopItem);
							resultIds.add(id);
							
						}
						resultTemp.setShopItemIds(resultIds);
						resultTemp.setStatus("yes");
						
					}else{
						shopItem.setAttrId(result);
						shopItem.setAttrName("");
						shopItem.setCreateTime(System.currentTimeMillis() / 1000);
						int id = shopDao.insertShop(shopItem);
						List<Integer> list = new ArrayList<Integer>();
						list.add(id);
						resultTemp.setShopItemIds(list);
						resultTemp.setResultId(id);
					}
					
//					shopDao.insertShop(shopItem);
					itemCategoryDao.updateItemCategory(shopItem.getCatId(), 1);
					
					
					resultTemp.setMessage("insert-"
							+ shopItem.getServiceName());
					resultTemp.setStatus("yes");
				
				} else {
					resultTemp = new ResultStatusBean();
					resultTemp.setMessage("insert-"
							+ shopItem.getServiceName());
					resultTemp.setStatus("no");
				}
			}
			
			
		}
		return resultTemp;
	}
}
