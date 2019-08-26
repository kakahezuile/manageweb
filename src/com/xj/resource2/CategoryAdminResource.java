package com.xj.resource2;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ItemCategory;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.ShopDao;
/**
 * 更正分类下的商品数
 * @author Administrator
 *
 */
@Path("v2/communities/{communityId}/catAdmin")
@Component
@Scope("prototype")
public class CategoryAdminResource {
	
	private Gson gson =new Gson();
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	
	@PUT
	public String catAdmin(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			resultStatusBean.setStatus("yes");
			List<ItemCategory> list = itemCategoryDao.getItemCategoryList();
			if(list != null && list.size() > 0){
				ItemCategory itemCategory = null;
				Iterator<ItemCategory> iterator = list.iterator();
				int catId = 0;
				List<ShopItem> shopItems = null;
				int count = 0;
				ItemCategory itemCategory2 = null;
				while(iterator.hasNext()){
					itemCategory = iterator.next();
					catId = itemCategory.getCatId();
					shopItems = shopDao.getCountByCutId(catId);
					if(shopItems != null && shopItems.size() > 0){
						count = shopItems.size();
						itemCategory2 = new ItemCategory();
						itemCategory2.setCatId(catId);
						itemCategory2.setItemNumber(count);
						itemCategory2.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
						itemCategoryDao.updateItemCategory(itemCategory2);
					}else{
						itemCategory2 = new ItemCategory();
						itemCategory2.setCatId(catId);
						itemCategory2.setItemNumber(0);
						itemCategory2.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
						itemCategoryDao.updateItemCategory(itemCategory2);
					}
				}
			}
 		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
