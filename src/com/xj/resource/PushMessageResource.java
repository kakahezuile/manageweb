package com.xj.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.PushMessage;
import com.xj.bean.PushMessageTime;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.mongo.PushMessageMongo;
import com.xj.thread.PushThreadObj;

@Path("/communities/{communityId}/pushmessage")
@Scope("prototype")
@Component
public class PushMessageResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private PushMessageMongo pushMessageMongo;
	
	@GET
	@Path("{emobId}/unread")
	public String getUnreadMessage(@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			resultStatusBean.setStatus("yes");
			List<PushMessage> list = pushMessageMongo.getPushMessages(emobId, 0);
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getUnreadMessage error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{emobId}")
	public String updateMessage(String json , @PathParam("emobId") String emobId){ // 修改推送状态
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			PushMessage pushMessage = gson.fromJson(json, PushMessage.class);
			pushMessage.setEmobId(emobId);
			boolean update = pushMessageMongo.updatePushMessage(pushMessage);
			if(update){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("update message error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("/up/{emobId}")
	public String getMessage(@PathParam("emobId") String emobId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			resultStatusBean.setStatus("yes");
			PushMessageTime time = gson.fromJson(json, PushMessageTime.class);
			ExecutorService exService = Executors.newSingleThreadExecutor();
			List<Users> users = new ArrayList<Users>();
			Users u = new Users();
			u.setEmobId(emobId);
			users.add(u);
			PushTask pushTask = new PushTask();
			pushTask.setScene("push message");
			pushTask.setServiceName("pushMessage");
			pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000));
			exService.execute(new PushThreadObj(users, pushMessageMongo ,time.getTime()));
			exService.shutdown();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("get push is error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
