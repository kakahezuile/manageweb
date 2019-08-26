package com.xj.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.StandardEntry;
import com.xj.bean.StandardEntryInApp;
import com.xj.bean.XjStandard;
import com.xj.dao.StandardEntryDao;
import com.xj.dao.XjStandardDao;

@Path("/communities/{communityId}/payStandards")
@Component
@Scope("prototype")
public class StandardEntryResource {

	private Gson gson = new Gson();
	
	@Autowired
	private StandardEntryDao standardEntryDao;
	
	@Autowired
	private XjStandardDao xjStandardDao;
	
	@POST
	public String addStandardEntry(@PathParam("communityId") Integer communityId , String json){ // 添加额度对应费用
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			StandardEntry standardEntry = gson.fromJson(json, StandardEntry.class);
			standardEntry.setCommunityId(communityId);
			standardEntry.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			XjStandard xjStandard = xjStandardDao.getXjStandard(communityId, standardEntry.getSort());
			String price = xjStandard.getPrice();
			Double result = Double.parseDouble(price) * Double.parseDouble(standardEntry.getEntrySum());
			standardEntry.setPrice(result+"");
			Integer resultId = standardEntryDao.addStandardEntry(standardEntry);
			resultStatusBean.setResultId(resultId);
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
	@Path("{sort}")
	public String getStandardEntryList(@PathParam("communityId") Integer communityId , @PathParam("sort") String sort){ // 获取额度对应价格
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<StandardEntry> list = standardEntryDao.getStandardEntryList(communityId, sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@DELETE
	@Path("{entryId}")
	public String deleteStandardEntry(@PathParam("entryId") Integer entryId){ // 删除对应额度价格
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			boolean result = standardEntryDao.deleteStandardEntry(entryId);
			if(result){
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

	public String getStandardEntryListInApp(@PathParam("communityId") Integer communityId , @QueryParam("q") String sort){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			XjStandard xjStandard = xjStandardDao.getXjStandard(communityId, sort);
			List<StandardEntry> list = standardEntryDao.getStandardEntryList(communityId, sort);
			StandardEntryInApp app = new StandardEntryInApp();
			app.setList(list);
			app.setUnitPrice(xjStandard.getPrice());
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(app);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
}
