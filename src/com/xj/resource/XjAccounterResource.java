package com.xj.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.XjAccounter;
import com.xj.dao.XjAccounterDao;

@Component
@Path("/communities/{communityId}/accounts")
@Scope("prototype")
public class XjAccounterResource {
	
	@Autowired
	private XjAccounterDao xjAccounterDao;
	
	private Gson gson = new Gson();
	/**
	 * 根据环信id 获取结款列表
	 * @param emobId
	 * @return
	 */
	@GET
	@Path("/{emobId}")
	public String getAccounterList(@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<XjAccounter> list = xjAccounterDao.getXjAccountList(emobId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
