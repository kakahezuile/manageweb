package com.xj.resource2;


import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ItemCategory;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopsCategory;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.ShopsCategoryDao;

@Path("/v2/communities/{communityId}/users/{emobIdUser}/itemCategories")
@Component
@Scope("prototype")
public class ItemCategoryResource2 {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	
	@Autowired
	private ShopsCategoryDao shopsCategoryDao;
	
//	@GET
//	public String getCategoryList(){
//		ResultStatusBean resultStatusBean = new ResultStatusBean();
//		resultStatusBean.setStatus("no");
//		try {
//			List<ItemCategory> list = itemCategoryDao.getItemCategoryList();
//			if(list != null){
//				resultStatusBean.setStatus("yes");
//				resultStatusBean.setInfo(list);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
	
//			e.printStackTrace();
//			resultStatusBean.setStatus("error");
//			resultStatusBean.setMessage("get list is error");
//			return gson.toJson(resultStatusBean);
//		}
//		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
//	}
	/**
	 * v2版本-根据店铺类型查询分类列表
	 */
	@GET
	public String getCategoryListByType(@QueryParam("q") String type , @PathParam("communityId") Integer communityId , @QueryParam("status") String status){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<ItemCategory> list = null;
			if(status != null && "shop".equals(status)){
				list = itemCategoryDao.getItemCategoryListByShop(communityId , type , "v2");
			}else{
				list = itemCategoryDao.getItemCategoryListByType(communityId , type , "v2");
			}
			int temp = 0;
			if(list != null){ // 这部分有待修改
				int len = list.size();
				ItemCategory itemCategory = null;
				for(int i = 0 ; i < len ; i ++){
					itemCategory = list.get(i);
					if(itemCategory.getUpdateTime() != null && itemCategory.getCreateTime() != null){
						if(itemCategory.getUpdateTime().intValue() > itemCategory.getCreateTime().intValue()){
							list.get(i).setCreateTime(itemCategory.getUpdateTime());
							if(itemCategory.getUpdateTime() > temp){
								temp = itemCategory.getUpdateTime();
							}
						}
					}
				}
			}
			Integer createTime = itemCategoryDao.maxCreateTime(communityId, "v2");
			if(temp > createTime){
				createTime = temp;
			}
			if(list != null){
				resultStatusBean.setStatus("yes");
				
				resultStatusBean.setInfo(list);
				resultStatusBean.setCreateTime(createTime);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("get list is error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据catid 修改分类内容
	 * @param catId
	 * @param json
	 * @return
	 */
	@PUT
	@Path("{catId}")
	public String updateItemCategory(@PathParam("catId") Integer catId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		ItemCategory itemCategory = null;
		try {
			itemCategory = gson.fromJson(json,ItemCategory.class);
			try {
				itemCategory.setCatId(catId);
				boolean result = itemCategoryDao.updateItemCategory(itemCategory);
				if(result){
					resultStatusBean.setStatus("yes");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultStatusBean.setMessage("update is error");
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("param is error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 添加分类
	 * @param json
	 * @param communityId
	 * @return
	 */
	@POST
	public String addItemCategory(String json , @PathParam("communityId") Integer communityId ){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ItemCategory itemCategory = gson.fromJson(json, ItemCategory.class);
			itemCategory.setCommunityId(communityId);
			itemCategory.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			try {
				Integer result = itemCategoryDao.addItemCategory(itemCategory);
				if(result > 0){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setResultId(result);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultStatusBean.setMessage("add is error");
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 删除分类
	 * @param catId
	 * @return
	 */
	@DELETE
	@Path("{catId}")
	public String deleteItemCategory(@PathParam("catId") Integer catId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setCatId(catId);
		resultStatusBean.setStatus("no");
		try {
			List<ShopsCategory> list = shopsCategoryDao.getShopsCategoryList(catId);
			if(list != null && list.size() > 0){
				if(list.get(0).getShopsCategoryId() > 0){
					resultStatusBean.setStatus("no");
					resultStatusBean.setMessage("当前分类有关联不能被删除");
					return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
				}
			}
			boolean result = itemCategoryDao.deleteItemCategory(itemCategory);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("delete is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
