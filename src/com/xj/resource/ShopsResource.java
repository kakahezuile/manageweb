package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.CommunityShops;
import com.xj.bean.ItemCategory;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.bean.ShopItemAndCategory;
import com.xj.bean.ShopItemAndShopName;
import com.xj.bean.ShopItemAttribute;
import com.xj.bean.Shops;
import com.xj.bean.ShopsCategory;
import com.xj.bean.ShopsPhone;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.OrdersDao;
import com.xj.dao.ShopDao;
import com.xj.dao.ShopsCategoryDao;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.ShopService;
import com.xj.utils.MD5Util;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/shops")
public class ShopsResource {

	// logger
	private static Logger logger = Logger.getLogger(ShopServlet.class);
	// dao
	@Autowired
	private ShopsDao shopsDao;

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private ItemCategoryDao itemCategoryDao;

	@Autowired
	private ShopsCategoryDao shopsCategoryDao;

	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private ShopService shopService;

	private Gson gson = new Gson();

	private final String myUrl = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";

	/**
	 * 查询店铺
	 * 
	 * @param communityId
	 *            小区id
	 * @return
	 */
	@GET
	@Path("/findShopsById")
	public String findShopsById(@PathParam("communityId") Integer communityId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize) {
		logger.info("community id is :" + communityId);
		if (pageNum != null && pageSize != null) {
			Page<Shops> page = null;
			try {
				page = shopsDao.getShopsListById(communityId, pageNum,
						pageSize, 10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ResultStatusBean resultStatusBean = new ResultStatusBean();
				resultStatusBean.setMessage("error");
				resultStatusBean.setStatus("no");
				return gson.toJson(resultStatusBean);
			}
			return gson.toJson(page).replace("\"null\"", "\"\"");
		}
		List<Shops> listShopsBeans = shopsDao.findAllById(communityId);
		String resultJson = new Gson().toJson(listShopsBeans);
		return resultJson.replace("\"null\"", "\"\"");
	}

	/**
	 * 根据emobId获取店家信息
	 * @param emobId
	 * @return
	 */
	@Path("{emobId}")
	@GET
	public String findShopObjectById(@PathParam("emobId") String emobId) {
		Shops shops = null;
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			shops = shopsDao.getShopsByEmobId(emobId);
			// shops = shopsDao.getShopsByShopId(shopId);
			if (shops != null && shops.getShopId() == null) {
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("当前环信id不存在");
			} else {
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(shops);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("查询出错");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		if (shops == null) {

			resultStatusBean.setMessage("this shopId not have message");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/keyword/{text}")
	public String findShopsByText(@PathParam("text") String text,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@PathParam("communityId") Integer communityId) {
		Page<Shops> page = null;
		if (text != null && !"".equals(text)) {
			try {

				page = shopsDao.getShopsListByText(communityId, text, pageNum,
						pageSize, 10, 1);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ResultStatusBean resultStatusBean = new ResultStatusBean();
				resultStatusBean.setMessage("error");
				resultStatusBean.setStatus("no");
				return gson.toJson(resultStatusBean);
			}
		}
		return gson.toJson(page).replace("\"null\"", "\"\"");
	}

	// @GET
	// @Path("?pageNum={pageNum}&pageSize={pageSize}")
	// public String getShopsListFindById(@PathParam("communityId") Integer
	// communityId , @PathParam("pageNum") Integer pageNum ,
	// @PathParam("pageSize") Integer pageSize){
	// Page<Shops> page = null;
	// try {
	// page = shopsDao.getShopsListById(communityId, pageNum, pageSize, 10);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// ResultStatusBean resultStatusBean = new ResultStatusBean();
	// resultStatusBean.setMessage("error");
	// resultStatusBean.setStatus("no");
	// return gson.toJson(resultStatusBean);
	// }
	// return gson.toJson(page);
	// }
	/**
	 * v1版 商品搜索
	 * 
	 */
	@GET
	@Path("/goods")
	public String findShopItemByText(@QueryParam("q") String text,
			@PathParam("communityId") Integer communityId,
			@QueryParam("sort") String sort) { // sort = 2 快店
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<ShopItemAndShopName> list = shopDao.findAllBySortAndVersion(sort, communityId, text, "v1");
			resultStatusBean.setInfo(list);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	public String findShopsBySort(
			@PathParam("communityId") Integer communityId,
			@QueryParam("q") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize) {
		logger.info("community id is :" + communityId);
		logger.info("sort is :" + sort);
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Page<Shops> page = null;
		try {
			page = shopsDao.getShopsListByText(communityId, sort, pageNum,
					pageSize, 10, 2); // 根据sort查询     2789207738   335617482
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resultJson = new Gson().toJson(resultStatusBean);
		return resultJson.replace("\"null\"", "\"\"");
	}

	/**
	 * 添加商家表
	 */
	@Path("/life")
	@POST
	public String addLifeShops(String json,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Shops shops = gson.fromJson(json, Shops.class);
			shops.setCommunityId(communityId);
			shops.setCreateTime(System.currentTimeMillis() / 1000l);
			Integer resultId = shopsDao.addShops(shops);
			if (resultId > 0) {
				resultStatusBean.setResultId(resultId);
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@Path("/number")
	@POST
	public String addNumberShops(String json,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Shops shops = gson.fromJson(json, Shops.class);
			shops.setCommunityId(communityId);
			shops.setCreateTime(System.currentTimeMillis() / 1000l);
			Integer resultId = shopsDao.addShops(shops);
			if (resultId > 0) {
				resultStatusBean.setResultId(resultId);
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	@Path("/content/{emobId}")
	public String getShopsByEmobId(@PathParam("emobId") String emobId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Shops shops = shopsDao.getShopsByEmobId(emobId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(shops);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 添加店铺
	 * 
	 * @param requestJson
	 * @return
	 */
	@POST
	public String addShops(String requestJson,
			@PathParam("communityId") Integer communityId) {
		Gson gson = new Gson();
		logger.info("request json is :" + requestJson);
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		// parse request to bean

		List<Shops> list = gson.fromJson(requestJson,
				new TypeToken<List<Shops>>() {
				}.getType());
		Iterator<Shops> it = list.iterator();
		resultStatusBean.setStatus("no");
		while (it.hasNext()) {
			Shops shopsBean = it.next();
			shopsBean.setCommunityId(communityId);
			shopsBean.setCreateTime(System.currentTimeMillis() / 1000l);

			shopsBean.setShopName("待开店");

			String fCode = MD5Util.getFcoude();
			Shops shops = null;
			try {
				shops = shopsDao.getShopsByFid(fCode);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			while (shops != null) {
				fCode = MD5Util.getFcoude();
				try {
					shops = shopsDao.getShopsByFid(fCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			shopsBean.setAuthCode(fCode);
			int id = 0;
			try {
				id = shopsDao.addShops(shopsBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("id of new shop is :" + id);
			if (id > 0) {
				resultStatusBean.setStatus("yes");
			}

		}

		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 更改店铺信息
	 */
	@PUT
	@Path("{shopId}")
	public String updateShop(@PathParam("shopId") Long shopId, String jsonStr,
			@PathParam("communityId") int communityId) {
		ResultStatusBean result = new ResultStatusBean();
		boolean resultBoolean = false;
		if (jsonStr != null && !"".equals(jsonStr)) {

//			System.out.println(jsonStr + "==========");

			try {
				Shops shopsBean = gson.fromJson(jsonStr, Shops.class);
				// if(shopsDao.findIdByName(shopsBean.getShopName())>0){
				// result.setStatus("no");
				// result.setMessage("exist");
				// String resultJson = new Gson().toJson(result);
				// return resultJson;
				// }
				shopsBean.setShopId(shopId);
				shopsBean.setCommunityId(communityId);

				resultBoolean = shopsDao.updateShops(shopsBean);
				if (resultBoolean) {
					result.setStatus("yes");
				} else {
					result.setStatus("no");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.info("---更改店铺信息操作参数有误---");
		}

		return gson.toJson(result).replace("\"null\"", "\"\"");
	}

	

	/**
	 * 修改店铺明细 / 添加
	 */

	@POST
	@Path("{shopId}/shopItems")
	public String updateShopItem(@PathParam("shopId") Long shopId, String json) {
		ResultStatusBean result = new ResultStatusBean();
	
		if (!"".equals(json) && json != null) {
			try {

				// isUpdate - 1 true - 0 false
				ShopItemAttribute shopItem = gson.fromJson(json,ShopItemAttribute.class);
				shopItem.setShopId(shopId);
				shopItem.setCreateTime(System.currentTimeMillis() / 1000);
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
	 * 查询店铺所有明细 - 根据shopid
	 */
	@GET
	@Path("{shopId}/shopItems")
	public String getListById(@PathParam("shopId") Long shopId) {
		List<ShopItem> listShopItem = null;
		List<ShopItemAndCategory> listResult = new ArrayList<ShopItemAndCategory>();
		ShopItemAndCategory shopItemAndCategory = null;
		List<ShopItem> listTemp = new ArrayList<ShopItem>();
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (shopId != null) {
			try {
				listShopItem = shopDao.findAllById(shopId);
				List<ItemCategory> listCategory = itemCategoryDao
						.getItemCategoryList();
				for (int i = 0; i < listCategory.size(); i++) {
					Integer catId = listCategory.get(i).getCatId();
					String catName = listCategory.get(i).getCatName();
					for (int j = 0; j < listShopItem.size(); j++) {
						if (catId == listShopItem.get(j).getCatId()) {
							if (shopItemAndCategory == null) {
								shopItemAndCategory = new ShopItemAndCategory();
								shopItemAndCategory.setCatId(catId);
								shopItemAndCategory.setCatName(catName);
							}
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
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(listResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				resultStatusBean.setMessage("error");
				resultStatusBean.setStatus("no");
			}
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 查询f 码
	 * 
	 * @param authCode
	 * @return
	 */
	@GET
	@Path("/shopSorts")
	public String getShopsByCode(@QueryParam("q") String authCode) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Shops shops = shopsDao.getShopsByFid(authCode);
			if (shops != null) {
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(shops);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/shopItems")
	public String findShopItemListByCatId(@QueryParam("q") Integer catId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<ShopItemAndShopName> page = shopDao.findAllByWIthPageAndName(
					communityId, -1l, catId, pageNum, pageSize, 10);
			if (page != null) {
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/{shopId}/status")
	public String getShopTypeByShopId(@PathParam("shopId") Integer shopId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Shops shops = shopsDao.getShopsByShopId(shopId);
			resultStatusBean.setStatus(shops.getStatus());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@POST
	@Path("/quotation")
	public String addQuotation(String json) { // 添加报价表 ----------- 创建维修绑定部分 需要
												// 集体添加
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			ShopItem shopItem = gson.fromJson(json, ShopItem.class);
			Integer catId = shopItem.getCatId();
			String serviceName = shopItem.getServiceName();
			List<ShopItem> listTemp = shopDao.getShopItemList(catId,
					serviceName);
//			System.out.println(listTemp.size());
			if (listTemp != null && listTemp.size() > 0) {
				resultStatusBean.setStatus("isEmpty");
			} else {
				List<ShopsCategory> list = shopsCategoryDao
						.getShopsCategoryList(shopItem.getCatId());
				Iterator<ShopsCategory> it = list.iterator();
				ShopsCategory shopsCategory = null;
				while (it.hasNext()) {
					shopsCategory = it.next();
					Integer shopId = shopsCategory.getShopId();
					String status = shopsCategory.getStatus();
					Long createTime = System.currentTimeMillis() / 1000l;
					if (status != null && "true".equals(status)) {
						shopItem.setShopId(new Long(shopId));
						shopItem.setCreateTime(createTime);

						shopDao.insertShop(shopItem);
					}
				}
				resultStatusBean.setStatus("yes");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/quotation/{catId}")
	public String getQuotationList(@PathParam("communityId") Integer communityId,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@PathParam("catId") Integer catId,
			@QueryParam("status") String status) { // 根据类别查询报价列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Page<ShopItem> page = null;
			if (status != null && !"".equals(status)) {
				page = shopDao.findAllByCatId(status, catId, pageNum, pageSize,
						10);
			} else {
				page = shopDao.findAllByCatId(catId, pageNum, pageSize, 10);
			}

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

	@PUT
	@Path("/quotation/{catId}")
	public String updateQuotation(@PathParam("catId") Integer catId, String json) { // 修改报价内容
																					// 及状态
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			ShopItem shopItem = gson.fromJson(json, ShopItem.class);
			shopItem.setCatId(catId);
			shopItem.setServiceName(shopItem.getServiceName());
			Date date=new Date();
			 Long upDate= date.getTime()/1000;
			 shopItem.setCreateTime(upDate);
			shopDao.updateShopByCatIdAndServiceName(shopItem);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/quotation")
	public String getQuotationList(@QueryParam("q") Integer shopId) { // 报价表类别 及 内容查询
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			
			List<ShopsCategory> shopsCategories = null;
			List<ItemCategory> list = itemCategoryDao.getItemCategoryListByType("5");
			List<ItemCategory> listCategory = new ArrayList<ItemCategory>();
			if(shopId != null){
				shopsCategories = shopsCategoryDao.getShopsCategoryByShopId(shopId);
				if(shopsCategories != null && list != null){
					int len = shopsCategories.size();
					int len2 = list.size();
					for(int i = 0 ; i < len2 ; i++){
						ItemCategory itemCategory = list.get(i);
						for(int j = 0 ; j < len ; j++){
							ShopsCategory shopsCategory = shopsCategories.get(j);
							if(shopsCategory.getCategoryId().equals(itemCategory.getCatId())){
								listCategory.add(itemCategory);
								break;
							}
						}
					}
				}
			}else{
				listCategory.addAll(list);
			}
			

			Iterator<ItemCategory> it = listCategory.iterator();
			List<ShopItemAndCategory> resultList = new ArrayList<ShopItemAndCategory>();
			while (it.hasNext()) {
				ItemCategory itemCategory = it.next();
				Integer catId = itemCategory.getCatId();
				String catName = itemCategory.getCatName();
				List<ShopItem> shopItemList = shopDao
						.findAllByCatIdGroupBy(catId);

				ShopItemAndCategory shopItemAndCategory = new ShopItemAndCategory();
				shopItemAndCategory.setCatId(catId);
				shopItemAndCategory.setCatName(catName);
				shopItemAndCategory.setList(shopItemList);
				resultList.add(shopItemAndCategory);
			}
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(resultList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	@Path("/list/{sort}")
	public String getShopListBySort(@QueryParam("pageSize") Integer pageSize,
			@QueryParam("pageNum") Integer pageNum,
			@PathParam("communityId") Integer communityId,
			@PathParam("sort") String sort) { // 根据sort查询shop列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (pageSize == null || pageNum == null) {
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("param not is null");
		} else {
			try {
				Page<Shops> page = shopsDao.findAllBySortAndCommunityId(
						communityId, sort, pageNum, pageSize, 10);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 搜索 黄页送水 快递 等列表
	 */
	@GET
	@Path("/list/{sort}/likeShops")
	public String getShopListLikeShops(
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("pageNum") Integer pageNum,
			@PathParam("communityId") Integer communityId,
			@PathParam("sort") String sort,
			@QueryParam("shopName") String shopName) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (pageSize == null || pageNum == null) {
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("param not is null");
		} else {
			try {
				Page<ShopsPhone> page = shopsDao.findAllLikeShopsCommunityId(
						communityId, sort, pageNum, pageSize, 10, shopName);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 搜索 黄页送水 快递 等列表
	 */
	@GET
	@Path("/shopLimit/{sort}")
	public String shopLimit(@PathParam("communityId") Integer communityId, @PathParam("sort") String sort) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(shopsDao.shopLimit(communityId, sort));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 搜索 黄页送水 快递 等列表
	 */
	@GET
	@Path("/upShop/{shopId}")
	public String upShop(@PathParam("shopId") String shopId, @QueryParam("deliverLimit") String deliverLimit, @QueryParam("deliverTime") String deliverTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (StringUtils.isBlank(deliverLimit) || StringUtils.isBlank(deliverTime)) {
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("请填写起送费和起送时间!");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		try {
			Integer sp= shopsDao.upShop(shopId, deliverLimit, deliverTime);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(sp);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage(e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 删除 商家
	 */
	@GET
	@Path("/del")
	public String delShops(@QueryParam("shopId") Long shopId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		shopsDao.delShops(shopId);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 添加快递表
	 */
	@Path("/addExpress")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public String addExpress(
			@QueryParam("businessStartTime") String businessStartTime,
			@QueryParam("businessEndTime") String businessEndTime,
			@QueryParam("shopName") String shopName,
			@QueryParam("phone") String phone,
			@QueryParam("sort") String sort,
			@QueryParam("status") String status,
			@PathParam("communityId") Integer communityId,
			@QueryParam("shopId") String shopId,
			@FormDataParam("shops_file") InputStream uploadFile,
			@FormDataParam("shops_file") FormDataContentDisposition fileDisposition) {

		String fileFullName = fileDisposition.getFileName();

		// 获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/")
				.getPath();

		File file = writeToFile(uploadFile, path + fileFullName);
		try {
			QiniuFileSystemUtil.upload(file, fileFullName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			// Shops shops = gson.fromJson(json, Shops.class);
			Shops shops = new Shops();
			shops.setShopName(shopName);
			// shops.setAddress(address);
			shops.setPhone(phone);
			shops.setSort(sort);
			shops.setBusinessEndTime(businessEndTime);
			shops.setBusinessStartTime(businessStartTime);
			shops.setCommunityId(communityId);
			shops.setLogo(myUrl + fileFullName);
			shops.setCreateTime(System.currentTimeMillis() / 1000);
			shops.setStatus(status);
			Integer resultId = shopsDao.addShops(shops);
			if (resultId > 0) {
				resultStatusBean.setResultId(resultId);
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	/**
	 * 图片上传的方法
	 */
	private File writeToFile(InputStream is, String uploadedFileLocation) {
		// TODO Auto-generated method stub
		File file = new File(uploadedFileLocation);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((is.read(buffer)) != -1) {
				os.write(buffer);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		System.out.println(uploadedFileLocation + "文件大小" + file.length());
		if (file.length() < 5) {
			file.delete();
			return null;
		}
		return file;

	}

	/**
	 * 修改快递表
	 */
	@Path("/upExpress")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public String upExpress(
			@QueryParam("businessStartTime") String businessStartTime,
			@QueryParam("businessEndTime") String businessEndTime,
			@QueryParam("shopName") String shopName,
			@QueryParam("phone") String phone,
			@QueryParam("sort") String sort,
			@QueryParam("shopId") Long shopId,
			@FormDataParam("shops_file_edit") InputStream uploadFile,
			@FormDataParam("shops_file_edit") FormDataContentDisposition fileDisposition) {
		Shops shops = new Shops();
		String fileFullName = fileDisposition.getFileName();

		// 获取工程根目录
		String path = this.getClass().getClassLoader().getResource("/")
				.getPath();

		if (fileFullName != null&&!fileFullName.equals("")) {
			File file = writeToFile(uploadFile, path + fileFullName);
			try {
				QiniuFileSystemUtil.upload(file, fileFullName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shops.setLogo(myUrl + fileFullName);
		}

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			// Shops shops = gson.fromJson(json, Shops.class);
			
			shops.setShopName(shopName);
			// shops.setAddress(address);
			shops.setPhone(phone);
			shops.setSort(sort);
			shops.setBusinessEndTime(businessEndTime);
			shops.setBusinessStartTime(businessStartTime);
			
			shops.setShopId(shopId);

			boolean resultBoolean = shopsDao.updateShops(shops);
			if (resultBoolean) {
				resultStatusBean.setStatus("yes");
			} else {
				resultStatusBean.setStatus("no");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	@Path("/{emobId}/incomes")
	public String getIncome(@PathParam("emobId") String emobId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal_1 = Calendar.getInstance();// 获取当前日期
			cal_1.add(Calendar.MONTH, 0);
			cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			Integer startTime = (int) (cal_1.getTimeInMillis() / 1000l);

			Calendar cale = Calendar.getInstance();
			cale.set(Calendar.DAY_OF_MONTH,
					cale.getActualMaximum(Calendar.DAY_OF_MONTH));

			Integer endTime = (int) (cale.getTimeInMillis() / 1000l);

			String result = ordersDao.getOrdersNumbers(emobId, startTime,
					endTime);

			resultStatusBean.setStatus("yes");
			if (result != null) {
				resultStatusBean.setInfo(result);
			} else {
				resultStatusBean.setInfo(0);
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
	 * 查看所有 未开通服务
	 */
	@GET
	@Path("/findNoneWordShops")
	public String findNoneWordShops(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@PathParam("communityId") Integer communityId) {
		Page<Shops> page = null;
		try {

			page = shopsDao.findNoneWordShops(communityId, "suspend", pageNum,
					pageSize, 10);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(page).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 维修报价表
	 */
	@GET
	@Path("/getUpShopItem")
	public String getUpShopItem(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Page<ShopItem> page=shopDao.getUpShopItem();
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 快店列表
	 */
	@GET
	@Path("/getShopList")
	public String getShopList(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<Shops> page=shopDao.getShopList();
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 
	 */
	@POST
	@Path("/upCommShops")
	public String upCommShops(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		CommunityShops shops = gson.fromJson(json, CommunityShops.class);
		Date date=new Date();
		 Integer upDate= (int) (date.getTime()/1000);
		shops.setCreateTime(upDate);
		try {
			Integer i=shopDao.upCommShops(shops);
			resultStatusBean.setInfo(i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@SuppressWarnings("unused")
	@POST
	@Path("/getShopName")
	public String getShopName(@PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Date date=new Date();
		Integer upDate= (int) (date.getTime()/1000);
		try {
			CommunityShops sh=shopDao.getShopName(communityId);
			String shopName="";
			if(sh!=null){
		      Shops	shops=	shopsDao.getShopsByShopId(sh.getShopId());
		      shopName=shops.getShopName();
			}
			
			resultStatusBean.setInfo(shopName);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
