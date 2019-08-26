package com.xj.resource2;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Attribute;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.AttributeDao;

@Path("/communities/{communityId}/attribute")
@Component
@Scope("prototype")
public class AttributeResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private AttributeDao attributeDao;
	
	@GET
	@Path("{serviceId}")
	public String getAttributes(@PathParam("serviceId") Integer serviceId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Attribute> list = attributeDao.getAttributes(serviceId);
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
