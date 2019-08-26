package com.xj.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.httpclient.apidemo.EasemobChatGroups;

@Path("/communities/{communityId}/emobGroup")
@Component
@Scope("prototype")
public class EmobGroupResource {
	
	private Gson gson = new Gson();
	
	@GET
	@Path("{emobGroupId}")
	public String isEmpty(@PathParam("emobGroupId") String emobGroupId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		ObjectNode objectNode = EasemobChatGroups.getGroupList(emobGroupId);

		resultStatusBean.setStatus("yes");
		ArrayNode arrayNode = (ArrayNode)objectNode.get("data");
		resultStatusBean.setInfo("ongoing");
		if(arrayNode != null && arrayNode.size() > 0){
			if(arrayNode.get(0).get("error") != null){
				String string = arrayNode.get(0).get("error").asText();
				if(string != null && "group id doesn't exist".equals(string)){
//					System.out.println("群组不存在了");
					resultStatusBean.setMessage("群组不在了");
					resultStatusBean.setInfo("deleted");
				}
			}
			
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
