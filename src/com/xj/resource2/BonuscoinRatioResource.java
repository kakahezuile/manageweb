package com.xj.resource2;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.BonuscoinRatio;
import com.xj.bean.ResultStatusBean;
import com.xj.service.BonuscoinRatioService;

@Component
@Scope("prototype")
@Path("v2/communities/{communityId}/bonuscoin")
public class BonuscoinRatioResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BonuscoinRatioService bonuscoinRatioService;
	
	@Path("/web")
	@POST
	public String addBonuscoin(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			BonuscoinRatio bonuscoinRatio = gson.fromJson(json, BonuscoinRatio.class);
			bonuscoinRatio.setCommunityId(communityId);
			boolean result = bonuscoinRatioService.addBonuscoinRatio(bonuscoinRatio);
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
	
	@Path("{sort}/web")
	@GET
	public String getBonuscoin(@PathParam("communityId") Integer communityId , @PathParam("sort") Integer sort){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			BonuscoinRatio bonuscoinRatio = bonuscoinRatioService.getBonuscoinRatio(communityId, sort);
			if(bonuscoinRatio != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(bonuscoinRatio);
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
