package com.xj.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.CommunityFeature;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.service.BonuscoinService;

/**
 * 帮帮币相关配置模块 
 * @author Administrator
 *
 */
@Path("/communities/bonusCoin")
@Component
@Scope("prototype")
public class BonusCoinResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BonuscoinService bonuscoinService;
	
	/**
	 * 获取帮帮币相关配置信息
	 * @return
	 */
	@GET
	@Path("/conf")
	public String getBonusCoinConfig(@DefaultValue("1")@QueryParam("pageNum")Integer pageNum,@DefaultValue("10")@QueryParam("pageSize")Integer pageSize,@QueryParam("query")String query){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		Page<CommunityFeature> page= bonuscoinService.getBonusCoinConfig(query,pageNum,pageSize);
		if(page!=null){
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("/conf")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Viewable configBonusCoin(
			@FormDataParam("communityId") Integer communityId,
			@FormDataParam("status") String status,
			@FormDataParam("exchange") Integer exchange,
			@FormDataParam("exchangeCoin") Integer exchangeCoin,
			@FormDataParam("shareCoin") Integer shareCoin,
			@FormDataParam("expense") Integer expense,
			@FormDataParam("expenseCoin") Integer expenseCoin){
		String uri = "/jsp/admin/error.jsp";
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		CommunityFeature communityFeature = new CommunityFeature("showBonuscoin", "bonuscoin", status, communityId, expense, expenseCoin, shareCoin, exchangeCoin, exchange);
		if(bonuscoinService.configBonusCoin(communityFeature)){
			resultStatusBean.setStatus("yes");
			uri = "/jsp/admin/bonus_coin_conf.jsp";
		}
		return  new Viewable(uri, null);
	}
}