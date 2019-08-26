package com.xj.resource;

import java.util.ArrayList;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ItemCategory;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.ShopDao;

@Path("/communities/{communityId}/prices")
@Component
@Scope("prototype")
public class CleaningResource {
	
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	
	@Autowired
	private ShopDao shopDao;
	
	private Gson gson = new Gson();
	
	@GET
	public String getCleaningShop(@QueryParam("q") Integer catId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){ // 获取保洁报价
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if(catId != null && pageNum != null && pageSize != null){
			try {
				Page<ShopItem> page = null;
			
					page = shopDao.findAllByCatId(catId, pageNum,
							pageSize, 10);
			
				
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
			}
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
				
		try {
			List<ItemCategory> list = itemCategoryDao.getItemCategoryListByType("6");
			resultStatusBean.setStatus("yes");
			int len = list.size();
			List<String> listString = new ArrayList<String>();
			
			for(int i = 0 ; i < len ; i++){
				
				ItemCategory itemCategory = list.get(i);
			
				String result = itemCategory.getCatName()+"(";
				List<ShopItem> shopItemList = shopDao.findAllByCatIdGroupBy(itemCategory.getCatId());
				int itemLen = shopItemList.size();
				for(int j = 0 ; j < itemLen ; j++){
					
					ShopItem shopItem = shopItemList.get(j);
					if(i == 0 && j == 0){
						listString.add(shopItem.getOriginPrice());
					}
					if(j != itemLen - 1){
						result = result + shopItem.getServiceName() + "、";
					}else{
						result = result + shopItem.getServiceName();
					}
				}
				result += ")";
				
				
			}
			String str = "服务范围：客厅、卧室、厨房、卫生间的地板表面、墙面、家居、家电等表面的清洁";
			listString.add(str);
			resultStatusBean.setInfo(listString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
}
