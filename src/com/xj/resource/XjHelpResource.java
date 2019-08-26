package com.xj.resource;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.XjHelp;
import com.xj.dao.XjHelpDao;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/shops/{emobId}/assists")
public class XjHelpResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private XjHelpDao xjHelpDao;
	
	@POST
	public String addXjHelp(@PathParam("communityId") Integer communityId , String json , @PathParam("emobId") String emobId){ // 申请协助
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			XjHelp xjHelp = gson.fromJson(json, XjHelp.class);
			xjHelp.setCommunityId(communityId);
			xjHelp.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			xjHelp.setEmobIdFrom(emobId);
			Integer resultId = xjHelpDao.addXjHelp(xjHelp);
			resultStatusBean.setResultId(resultId);
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
	
	public String updateXjHelp(@PathParam("emobId") String emobId , @QueryParam("q") String status , String json){ // 完成协助
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			XjHelp xjHelp = gson.fromJson(json, XjHelp.class);
		
			xjHelp.setEndTime((int)(System.currentTimeMillis() / 1000l));
			boolean result = false;
			if("to".equals(status)){
				xjHelp.setEmobIdTo(emobId);
				result = xjHelpDao.updateHelpByEmobIdTo(xjHelp);
			}else if("from".equals(status)){
				xjHelp.setEmobIdFrom(emobId);
				result = xjHelpDao.updateHelpByEmobIdFrom(xjHelp);
				
			}
			
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
