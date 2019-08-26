package com.xj.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.service.CopyService;

@Path("/communities/{communityId}/copy")
@Component
@Scope("prototype")
public class CopyResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private CopyService copyService;
	
	@POST
	@Path("{catId}/category/{catIdTo}")
	public String copyCategory(@PathParam("communityId") Integer communityId , @PathParam("catId") Integer catId , @PathParam("catIdTo") Integer catIdTo){ // 复制分类下的商品
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			boolean result = copyService.copyCategory(catId, catIdTo);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("copy category error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
