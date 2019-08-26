package com.xj.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Admin;
import com.xj.bean.MessageList;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.AdminDao;
import com.xj.dao.MessageListDao;

@Path("/communities/{communityId}/messageList")
@Component
@Scope("prototype")
public class MessageListResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private MessageListDao messageListDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@GET
	@Path("{adminId}")
	public String getMessageList(@PathParam("adminId") Integer adminId) throws Exception{
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("yes");
		Admin admin = adminDao.getAdminById(adminId);
		List<MessageList> list = messageListDao.getMessageList(admin.getEmobId());
		resultStatusBean.setInfo(list);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	public String addMessageList(String json) throws Exception{
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		
		try {
			MessageList messageList = gson.fromJson(json, MessageList.class);
			messageList.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			messageListDao.addMessageList(messageList);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
