package com.xj.resource;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.xj.bean.Messages;
import com.xj.bean.ResultStatusBean;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.httpclient.utils.ListSort;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.mongo.MessageMongo;
import com.xj.service.MessagesService;
import com.xj.service.SensitiveWordsHistoryService;
import com.xj.thread.MessageSelectThread;
import com.xj.timer.MessageTransfer;

@Component
@Scope("prototype")
@Path("/usersMessages")
public class MessageResource {
	
	private static Logger LOGGER = LoggerFactory.getLogger(MessageResource.class);
	
	@Autowired
	private MessagesService messagesService;
	
	@Autowired
	private MessageMongo messageMongo;
	
	@Autowired
	private SensitiveWordsHistoryService sensitiveWordsHistoryService;
	
	private Gson gson = new Gson();
	
	@GET
	@Path("/medias")
	public String getMediaMessage(@QueryParam("msgId") String msgId, @Context HttpServletRequest request) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		if (StringUtils.isBlank(msgId)) {
			resultStatusBean.setMessage("消息id不能为空!");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		try {
			resultStatusBean.setInfo(messageMongo.getMessageByMsgId(msgId.split(",")));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			resultStatusBean.setInfo(e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobGroupId}")
	public String getActivityMessage(@PathParam("emobGroupId") String emobGroupId, @Context HttpServletRequest request){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		
		try {
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(new MessageSelectThread(messageMongo, sensitiveWordsHistoryService));
			executorService.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resultStatusBean.setInfo(messageMongo.getListmessage(emobGroupId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			resultStatusBean.setInfo(e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@GET
	public String getUserMessages(@QueryParam("messageFrom") String messageFrom , @QueryParam("messageTo") String messageTo , @Context HttpServletRequest request) throws Exception{
		List<Messages> list = messageMongo.getListMessage(messageFrom, messageTo);
		List<Messages> list2 = messageMongo.getListMessage(messageTo, messageFrom);
		list.addAll(list2);
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		if(list != null){
			ListSort<Messages> listSort = new ListSort<Messages>();
			listSort.Sort(list, "getTimestamp", "");
			int len = list.size();
			Long timeStamp = 0l; 
			if(len > 0 ) list.get(len - 1).getTimestamp();
			Messages messages = messageMongo.getMessageInMaxTime();
			if(messages != null){
				timeStamp = messages.getTimestamp();
			}
			
			try {
				new MessageTransfer().save(timeStamp, System.currentTimeMillis(), messageMongo, sensitiveWordsHistoryService);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			list.addAll(messageMongo.getListMessageNext(messageFrom, messageTo, timeStamp));
			list.addAll(messageMongo.getListMessageNext(messageTo, messageFrom, timeStamp));
			listSort.Sort(list, "getTimestamp", "");
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@Path("/test")
	@GET
	public String getUserMessages2(@QueryParam("messageFrom") String messageFrom , @QueryParam("messageTo") String messageTo , @Context HttpServletRequest request) throws Exception{
		List<Messages> list = messageMongo.getListMessage(messageFrom, messageTo);
		list.addAll(messageMongo.getListMessage(messageTo, messageFrom));
		
		ListSort<Messages> listSort = new ListSort<Messages>();
		listSort.Sort(list, "getTimestamp", "");
		
		int len = list.size();
		if (len > 0) {
			list.get(len - 1).getTimestamp();
		}
		
		Long timeStamp = 0l; 
		Messages messages = messageMongo.getMessageInMaxTime();
		if (messages != null) {
			timeStamp = messages.getTimestamp();
		}
		
		list.addAll(messageMongo.getListMessageNext(messageFrom, messageTo, timeStamp));
		list.addAll(messageMongo.getListMessageNext(messageTo, messageFrom, timeStamp));
		listSort.Sort(list, "getTimestamp", "");
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("yes");
		resultStatusBean.setInfo(list);
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("{messageFrom}/{messageTo}/{timestampMin}/{timestampMax}")
	public String getUserWithUserMessage(@PathParam("messageFrom") String messageFrom , @PathParam("messageTo") String messageTo , @PathParam("timestampMin") Integer timestampMin , @PathParam("timestampMax") Integer timestampMax){
		List<Messages> list = null;
		if(!"".equals(messageFrom) && !"".equals(messageTo) && messageFrom != null && messageTo != null){
			try {
				list = messagesService.getListWithTime(messageFrom, messageTo, timestampMin ,timestampMax);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return gson.toJson(list).replace("\"null\"", "\"\""); 
	}
	
	@POST
	@Path("{userId}")
	public String getUserMessageSaveInDb(@Context HttpServletRequest request){
		ResultStatusBean resultBean = new ResultStatusBean();
//		//参数构建
//		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		//获取聊天记录
		ObjectNode resultObjectNode = EasemobChatMessage.getChatMessages(null,null,null,null);
//		System.out.println(resultObjectNode);
		List<Messages> listBean = getUserMessageBean(resultObjectNode);
		Iterator<Messages> it = listBean.iterator();
		Messages ms = null;
		while(it.hasNext()){
			ms = it.next();
			if(!"txt".equals(ms.getMsgType())){
				if(ms.getUrl() != null && !"".equals(ms.getUrl())){
					try {
						QiniuFileSystemUtil.fileDownload(ms.getUrl(), ms.getFilename());
					} catch (ClientProtocolException e) {
						e.printStackTrace();
						resultBean.setStatus("error");
						resultBean.setMessage("下载文件失败");
						return gson.toJson(resultBean);
					} catch (IOException e) {
						e.printStackTrace();
						resultBean.setStatus("error");
						resultBean.setMessage("下载文件失败");
						return gson.toJson(resultBean);
					}finally{
						try {
							QiniuFileSystemUtil.upload(new File(request.getRequestURI()+"/qiniu"+ms.getFilename()), ms.getFilename());
							ms.setUrl(QiniuFileSystemUtil.myProject+ms.getFilename() );
						} catch (Exception e) {
							e.printStackTrace();
							resultBean.setStatus("error");
							resultBean.setMessage("上传文件失败");
							return gson.toJson(resultBean);
						}
					}
				}
				try {
					messageMongo.insertMessage(ms);
				} catch (Exception e) {
					e.printStackTrace();
					resultBean.setStatus("error");
					resultBean.setMessage("保存数据过程中发生错误");
					return gson.toJson(resultBean);
				}finally{
					resultBean.setMessage("数据保存成功");
					resultBean.setStatus("yes");
				}
			}
		}
		
		return gson.toJson(resultBean).replace("\"null\"", "\"\"");
	}

	private static Object getNodeString(JsonNode objectNode , String key , Object object){
		if(object instanceof String){
			return objectNode.get(key) != null ? objectNode.get(key).asText() : "";
		}else if(object instanceof Long){
			return objectNode.get(key) != null ? objectNode.get(key).asLong() : 0l;
		}else if(object instanceof Integer){
			return objectNode.get(key) != null ? objectNode.get(key).asInt() : 0;
		}else{
			return null;
		}
	}
	
	//从聊天记录对象中获取数据
	private static List<Messages> getUserMessageBean(ObjectNode resultObjectNode){
		Messages bean = new Messages();
		String statusCode = "";
		List<Messages> list = new ArrayList<Messages>();
		if(null != resultObjectNode){
			if(resultObjectNode.get("statusCode") != null){
				statusCode = resultObjectNode.get("statusCode").asText();
			}
			
			if("200".equals(statusCode)){
				JsonNode array = JsonNodeFactory.instance.arrayNode();
				array = resultObjectNode.get("entities");
				Iterator<JsonNode> it = array.iterator();
				JsonNode jsonNode = null;
				while(it.hasNext()){
					jsonNode = it.next();
					bean.setCMD_CODE(statusCode);
					bean.setUuid((String)getNodeString(jsonNode,"uuid",""));
					bean.setChatType((String)getNodeString(jsonNode,"chat_type",""));
					bean.setType((String)getNodeString(jsonNode,"type",""));
					bean.setMessageFrom((String)getNodeString(jsonNode,"from",""));
					bean.setMessageTo((String)getNodeString(jsonNode,"to",""));
					bean.setMsgId((String)getNodeString(jsonNode,"msg_id",""));
					bean.setTimestamp((Long)getNodeString(jsonNode,"timestamp",0l));
					bean.setCreated((Long)getNodeString(jsonNode,"created",0l));
					bean.setModified((Long)getNodeString(jsonNode,"modified",0l));
					bean.setDirection((String)getNodeString(jsonNode,"direction",""));

//					BodiesBean bodiesBean = new BodiesBean();
					if(jsonNode.get("payload") != null){
						JsonNode jsonNode2 = jsonNode.get("payload");
						JsonNode arrayNode = jsonNode2.get("bodies");
						Iterator<JsonNode> it2 = arrayNode.iterator();
						if(it2.hasNext()){
							JsonNode payLoadBean = it2.next();
							
							bean.setAddr((String)getNodeString(payLoadBean,"addr",""));
							bean.setFilename((String)getNodeString(payLoadBean,"filename",""));
							bean.setLat((String)getNodeString(payLoadBean,"lat",""));
							bean.setVideoLength((Integer)getNodeString(payLoadBean,"length",0));
							bean.setLng((String)getNodeString(payLoadBean,"lng",""));
							bean.setMsg((String)getNodeString(payLoadBean,"msg",""));
							bean.setSecret((String)getNodeString(payLoadBean,"secret",""));
							bean.setMsgType((String)getNodeString(payLoadBean,"type",""));
							bean.setUrl((String)getNodeString(payLoadBean,"url",""));
						}
					}
					list.add(bean);
				}
			}else{
				LOGGER.error("没有获取到聊天记录");
			}
		}else{
			LOGGER.error("没有获取到聊天记录");
		}
		return list;
	}
}
