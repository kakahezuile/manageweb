package com.xj.resource;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.CatIdNumber;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.ItemCategoryDao;
import com.xj.dao.ShopDao;

@Path("/communities/{communityId}/catnumber")
@Component
@Scope("prototype")
public class CatIdNumberResource {
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	
	private Gson gson = new Gson();
	
	@PUT
	@Path("{shopId}")
	public String updateCat(@PathParam("shopId") Long shopId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<CatIdNumber> list = shopDao.getCatIdNumber(shopId);
			Iterator<CatIdNumber> iterator = list.iterator();
			CatIdNumber catIdNumber = null;
			while(iterator.hasNext()){
				catIdNumber = iterator.next();
				itemCategoryDao.updateItemCat(catIdNumber.getCatId(), catIdNumber.getNum());
			}
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
