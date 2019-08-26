package com.xj.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.ResultStatusBean;

@Path("/communities/{communityId}/sendmessage")
@Scope("prototype")
@Component
public class SendMessageForServiceResource {
	
	private Gson gson = new Gson();
	
	@POST
	public String sendMessage(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		ExtNode extNode = gson.fromJson(json, ExtNode.class);
		//发送相关信息
		int sort = 0;
		switch (sort) {
			case 1:
				extNode.setCMD_CODE(150);
				break;
			case 2:
				extNode.setCMD_CODE(130);
				break;
			case 3:
				extNode.setCMD_CODE(140);
				break;
			case 4:
				extNode.setCMD_CODE(160);
				break;
			case 5:
				extNode.setCMD_CODE(190);
				break;
			case 6:
				extNode.setCMD_CODE(1110);
				break;
			case 7:
				extNode.setCMD_CODE(1130);
				break;
			case 8:
				extNode.setCMD_CODE(1120);
				break;	
			case 9:
				extNode.setCMD_CODE(1140);
				break;
			case 10:
				extNode.setCMD_CODE(1150);
				break;
			case 11:
				extNode.setCMD_CODE(1100);
				break;
			case 12:
				extNode.setCMD_CODE(180);
				break;
			default:
				break;
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
