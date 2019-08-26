package com.xj.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopType;
import com.xj.dao.ShopTypeDao;

@Component
@Scope("prototype")
@Path("/communities/shoptype")
public class ShopTypeResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ShopTypeDao shopTypeDao;
	
	@GET
	public String getTypeList(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<ShopType> shopType = shopTypeDao.getTypeList();
			if(shopType != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(shopType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
	}
}
