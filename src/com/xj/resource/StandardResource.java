package com.xj.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.XjStandard;
import com.xj.dao.XjStandardDao;

@Path("/communities/{communityId}/standard")
@Component
@Scope("prototype")
public class StandardResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private XjStandardDao xjStandardDao;
	
	@POST
	public String addStandard(String json , @PathParam("communityId") Integer communityId){ // 添加收费标准
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			XjStandard xjStandard = gson.fromJson(json, XjStandard.class);
			xjStandard.setCommunityId(communityId);
			XjStandard xjStandard2 = xjStandardDao.getXjStandard(communityId, xjStandard.getSort());
			if(xjStandard2 != null){
				xjStandard.setStandardId(xjStandard2.getStandardId());
				xjStandardDao.updateXjStandard(xjStandard);
			}else{
				Integer resultId = xjStandardDao.addXjStandard(xjStandard);
				resultStatusBean.setResultId(resultId);
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
	
	@PUT
	@Path("{standardId}")
	public String updateStandard(String json , @PathParam("communityId") Integer communityId , @PathParam("standardId") Integer standardId){ // 修改收费标准
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			XjStandard xjStandard = gson.fromJson(json, XjStandard.class);
			xjStandard.setStandardId(standardId);
			boolean reseult = xjStandardDao.updateXjStandard(xjStandard);
			if(reseult){
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
	
	@GET
	@Path("{sort}")
	public String getStandard(@PathParam("sort") String sort , @PathParam("communityId") Integer communityId){ // 根据类型查询标准
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			XjStandard xjStandard = xjStandardDao.getXjStandard(communityId, sort);
			if(xjStandard != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(xjStandard);
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
