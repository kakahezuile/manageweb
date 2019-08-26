package com.xj.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.xj.bean.EAmobGroup;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.EAmobGroupDao;
import com.xj.httpclient.apidemo.EasemobChatGroups;

@Path("/communities/{communityId}/eagroup")
@Component
@Scope("prototype")
public class EAmobGroupResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private EAmobGroupDao eAmobGroupDao;
	
	@GET
	@Path("{emobGroupId}")
	public String getEAgroup(@PathParam("emobGroupId") String emobGroupId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			EAmobGroup eAmobGroup = eAmobGroupDao.getEAmobGroup(emobGroupId);
			if(eAmobGroup != null){
				resultStatusBean.setStatus("yes");
				ObjectNode objectNode = EasemobChatGroups.getGroupList(emobGroupId);
				if(objectNode != null){
					Integer sum = objectNode.get("data").get(0).get("maxusers").asInt();
					eAmobGroup.setMaxValue(sum);
				}
				resultStatusBean.setInfo(eAmobGroup);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getEAgroup error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
