package com.xj.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.dao.MyUserDao;
import com.xj.mongo.PushMessageMongo;

@Path("/communities/{communityId}/pushstatistics")
@Component
@Scope("prototype")
public class PushStatisticsResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private PushMessageMongo pushMessageMongo;
	
	@Autowired
	private MyUserDao myUserDao;
	/**
	 * 根据CMD_CODE 获取统计列表
	 * @param CMD_CODE
	 * @param communityId
	 * @return
	 */
	@GET
	@Path("{CMD_CODE}")
	public String getPushStatistics(@PathParam("CMD_CODE") Integer CMD_CODE , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<PushStatistics> list = pushMessageMongo.getPushMessageByCode(CMD_CODE, 1, 10);
			List<PushStatistics> listResult = new ArrayList<PushStatistics>();
			List<Users> users = myUserDao.getUserList(communityId);
			Map<String, String> map = new HashMap<String, String>();
			Users user = null;
			Iterator<Users> iterator = users.iterator();
			while(iterator.hasNext()){
				user = iterator.next();
				map.put(user.getEmobId(), "yes");
			}
			if(list != null && list.size() > 0){
				int len = list.size();
				PushStatistics pushStatistics = null;
				for(int i = 0 ; i < len ; i++){
					pushStatistics = list.get(i);
					if("yes".equals(map.get(pushStatistics.getEmobId()))){
						listResult.add(pushStatistics);
					}
				}
			}
			
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(listResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 根据messageId获取详细信息
	 * @param messageId
	 * @return
	 */
	@GET
	@Path("{messageId}/pushMessage")
	public String getPushMessages(@PathParam("messageId") Long messageId , @QueryParam("page") Integer page , @QueryParam("pageSize") Integer pageSize){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<PushMessage> list = pushMessageMongo.getPushMessages(messageId,page,pageSize);
			resultStatusBean.setInfo(list);
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
