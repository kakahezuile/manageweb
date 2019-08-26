package com.xj.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.ShopItem;
import com.xj.dao.ShopDao;

@Service("copyService")
public class CopyService {
	
	@Autowired
	private ShopDao shopDao;
	
	public boolean copyCategory(int catId , int catIdTo){
		boolean result = false;
		try {
			List<ShopItem> list = shopDao.getShopItemByCatId(catId); 
			Iterator<ShopItem> iterator = list.iterator();
			ShopItem shopItem = null;
			int num = 0;
			while(iterator.hasNext()){
				shopItem = iterator.next();
				shopItem.setCatId(catIdTo);
				num = shopDao.insertShop(shopItem);
			}
			if(num > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
