package com.xj.resource;

import java.util.ArrayList;
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
import com.xj.bean.Messages;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.SensitiveWordsHistory;
import com.xj.dao.SensitiveWordsHistoryDao;
import com.xj.httpclient.utils.ListSort;
import com.xj.mongo.MessageMongo;

@Path("/communities/{communityId}/sensitiveWordsHistory")
@Component
@Scope("prototype")
public class SensitiveWordsHistoryResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private SensitiveWordsHistoryDao sensitiveWordsHistoryDao;
	
	@Autowired
	private MessageMongo messageMongo;
	
	@GET
	public String getSensitiveWordsHistory(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){ // 获取敏感词使用历史列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<SensitiveWordsHistory> page = sensitiveWordsHistoryDao.getSensitiveWordsHistoryList(communityId , pageNum, pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	public String addSensitiveWordsHistory(String json){ // 添加敏感词使用历史
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			SensitiveWordsHistory sensitiveWordsHistory = gson.fromJson(json, SensitiveWordsHistory.class);
			Integer resultId = sensitiveWordsHistoryDao.addSensitiveWordsHistory(sensitiveWordsHistory);
			if(resultId > 0){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@PUT
	@Path("{sensitiveWordsHistoryId}")
	public String updateSensitiveWordsHistory(@PathParam("sensitiveWordsHistoryId") Integer sensitiveWordsHistoryId , String json){ // 修改敏感词历史内容
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			SensitiveWordsHistory sensitiveWordsHistory = gson.fromJson(json, SensitiveWordsHistory.class);
			sensitiveWordsHistory.setSensitiveWordsHistoryId(sensitiveWordsHistoryId);
			boolean result = sensitiveWordsHistoryDao.updateSensitiveWordsHistory(sensitiveWordsHistory);
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
	
	@GET
	@Path("/{uuid}/messages")
	public String getHistoryMessage(@PathParam("uuid") String uuid){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<Messages> list = new ArrayList<Messages>();
		resultStatusBean.setStatus("no");
		try {
			Messages messages = messageMongo.getMessageByUuid(uuid);
			List<Messages> listFirst = messageMongo.getListMessage(messages.getMessageTo(), messages.getTimestamp(), false);
			list.addAll(listFirst);
			list.add(messages);
			List<Messages> listNext = messageMongo.getListMessage(messages.getMessageTo(), messages.getTimestamp() , true);
			list.addAll(listNext);
			ListSort<Messages> listSort = new ListSort<Messages>();
			listSort.Sort(list, "getTimestamp", "");
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
	
	@GET
	@Path("/{emobGroupId}/messages/{status}")
	public String getHistoryMessageByStatus(@PathParam("emobGroupId") String emobGroupId , @PathParam("status") String status , @QueryParam("time") Long time){ // 获取聊天记录 前十条或后十条
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		List<Messages> list = null;
		try {
			if(status != null && status.equals("last")){ // 前十条
				list = messageMongo.getListMessage(emobGroupId, time, false);
			}else if(status != null && status.equals("next")){ // 后十条
				list = messageMongo.getListMessage(emobGroupId, time, true);
			}
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
