package com.xj.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopsAndCategory;
import com.xj.dao.ShopsCategoryDao;

@Path("/communities/{communityId}/shopsCategory")
@Component
@Scope("prototype")
public class ShopsCategoryResource {
	
	@Autowired
	private ShopsCategoryDao shopsCategoryDao;
	
	private Gson gson = new Gson();
	
	@GET
	@Path("/{catId}")
	public String getShopsCategoryList(@PathParam("communityId") Integer communityId ,@PathParam("catId") Integer catId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if(pageNum == null || pageSize == null || "".equals(pageSize) || "".equals(pageNum)){
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("param not is null");
		}else{
			try {
				Page<ShopsAndCategory> page = shopsCategoryDao.getShopsAndCategoryList(communityId , catId, pageNum, pageSize, 10);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(page);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				return gson.toJson(resultStatusBean);
			}
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
