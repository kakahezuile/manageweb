package com.xj.resource;

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
import com.xj.bean.CommunityExpress;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.CommunityExpressDao;

@Path("/communities/{communityId}/communityExpress")
@Scope("prototype")
@Component
public class CommunityExpressResource { // 小区快递地址操作模块
	
	private Gson gson = new Gson();
	
	@Autowired
	private CommunityExpressDao communityExpressDao;
	
	@POST
	public String addCommunityExpress(String json , @PathParam("communityId") Integer communityId){ // 为小区添加快递信息
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			CommunityExpress communityExpress = gson.fromJson(json, CommunityExpress.class);
			communityExpress.setCommunityId(communityId);
			int time = (int) (System.currentTimeMillis() / 1000l);
			communityExpress.setCreateTime(time);
			communityExpress.setUpdateTime(time);
			Integer resultId = communityExpressDao.addCommunityExpress(communityExpress);
			if(resultId > 0){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("参数格式有误");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	public String getCommunityExpress(@PathParam("communityId") Integer communityId , @QueryParam("q") String status){ // 查询该小区快递地址信息
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			if(status != null && "max".equals(status)){
				CommunityExpress communityExpress = communityExpressDao.getCommunityExpressByLevel(communityId);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(communityExpress);
			}else{
				List<CommunityExpress> list = communityExpressDao.getCommunityExpressList(communityId);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("查询时发生错误");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{communityExpressId}")
	public String updateCommunityExpress(String json , @PathParam("communityExpressId") Integer communityExpressId){ // 修改小区快递地址信息
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			CommunityExpress communityExpress = gson.fromJson(json, CommunityExpress.class);
			communityExpress.setCommunityExpressId(communityExpressId);
			communityExpress.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
			boolean result = communityExpressDao.updateCommunityExpress(communityExpress);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("参数格式有误");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
