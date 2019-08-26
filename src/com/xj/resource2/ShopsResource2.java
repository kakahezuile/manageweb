package com.xj.resource2;

import java.util.ArrayList;

import java.util.List;

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

import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;

import com.xj.bean.ShopItem;
import com.xj.bean.ShopItemAndCategory;
import com.xj.bean.ShopItemAndShopName;
import com.xj.bean.ShopItemAttribute;

import com.xj.dao.ShopDao;
import com.xj.service.ShopService;

@Component
@Path("/v2/communities/{communityId}/shops")
@Scope("prototype")
public class ShopsResource2 {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ShopDao shopDao;

	
	@Autowired
	private ShopService shopService;
	

	
	private List<ShopItemAndShopName> getAttr(Page<ShopItemAndShopName> page) throws Exception{
		List<ShopItemAndShopName> list = page.getPageData();
		ShopItemAndShopName shopItemAndShopName = null;
		if(list != null && list.size() > 0){
			int len = list.size();
			for(int i = 0 ; i < len ; i++){
				shopItemAndShopName = list.get(i);
				if("yes".equals(shopItemAndShopName.getMultiattribute())){
					int catId = shopItemAndShopName.getCatId();
					List<ShopItem> attributes = shopDao.getShopItemByAttrId( shopItemAndShopName.getAttrId() , catId);
					list.get(i).setList(attributes);
				}
			}
		}
		return list;
	}
	/**
	 * 根据catId 小区id 获取商品列表
	 * @param communityId
	 * @param catId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Path("/shopItems")
	@GET
	public String getShopItemByCatId(@PathParam("communityId") Integer communityId , @QueryParam("q") Integer catId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			if(pageNum != null && pageSize != null){
				Page<ShopItemAndShopName> page = shopDao.findAllByWIthPageAndName(communityId, -1l, catId, pageNum,
						pageSize, 10);
				List<ShopItemAndShopName> list = getAttr(page);
				page.setPageData(list);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getShopItemByCatId error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 商品搜索
	 * @param communityId
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Path("/goods")
	@GET
	public String getShopItemByServiceName(@PathParam("communityId") Integer communityId , @QueryParam("q") String name , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			if(pageNum != null && pageSize != null){
				Page<ShopItemAndShopName> page = shopDao.findAllBySortAndVersion("2", communityId, name,"v2", pageNum, pageSize, 10);
				List<ShopItemAndShopName> list = getAttr(page);
				page.setPageData(list);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getShopItemByServiceName error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 店家端 根据id获取 分类及内容
	 * @param shopId
	 * @param status
	 * @param communityId
	 * @return
	 */
	@GET
	@Path("/{shopId}/shopItems")
	public String getShopItemByShopId(@PathParam("shopId") Long shopId , @QueryParam("q") String status , @PathParam("communityId") Integer communityId){ // 根据店铺id 查询指定状态商品
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		
		List<ShopItemAndCategory> listResult = new ArrayList<ShopItemAndCategory>();
		try {
			listResult = shopService.getShopItemByShopId2(communityId, shopId, status);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(listResult);
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getShopItemByShopId error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{shopId}/shopItems")
	public String updateShop(@PathParam("shopId") Long shopId , String json){ // 上架下架接口
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ShopItem shopItem = gson.fromJson(json, ShopItem.class);
			
			boolean result = shopDao.updateShopItemByAttrId(shopItem);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{serviceId}")
	public String updateShopItem(@PathParam("serviceId") Integer serviceId , String json){  // 删除商品属性接口
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ShopItem shopItem = gson.fromJson(json, ShopItem.class);
			shopItem.setServiceId(new Long(serviceId));
			boolean result = shopDao.updateShop(shopItem);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 添加商品 或 修改商品  根据 isUpdate
	 * @param shopId
	 * @param json
	 * @return
	 */
	@POST
	@Path("{shopId}/shopItems")
	public String updateShopItem(@PathParam("shopId") Long shopId, String json) {

//		List<ResultStatusBean> listResult = new ArrayList<ResultStatusBean>();
		ResultStatusBean result = new ResultStatusBean();
		if (!"".equals(json) && json != null) {
			try {

				ShopItemAttribute shopItem = gson.fromJson(json,
						ShopItemAttribute.class);
						shopItem.setShopId(shopId);
//						shopItem.setCreateTime(System.currentTimeMillis() / 1000);
						
						result = shopService.updateShopItem(shopItem, shopId);
						
				

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result.setMessage("参数格式有误");
				result.setStatus("no");
				return gson.toJson(result);
			}

		} else {
			result.setMessage("参数不能为空哦");
			result.setStatus("no");
			return gson.toJson(result);
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据属性id 修改商品内容
	 * @param attrId
	 * @param json
	 * @return
	 */
	@PUT
	@Path("{attrId}/itemCategory")
	public String updateItemCategory(@PathParam("attrId") Integer attrId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ShopItem shopItem = gson.fromJson(json, ShopItem.class);
			shopItem.setAttrId(attrId);
			boolean result = shopDao.updateShopItemByAttrId(shopItem);
			if(result){
				resultStatusBean.setStatus("yes");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
