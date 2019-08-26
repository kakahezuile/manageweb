package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Messages;
import com.xj.bean.MonitorComplaints;
import com.xj.bean.MonitorComplaintsTop;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.MonitorComplaintsDao;
import com.xj.httpclient.utils.ListSort;
import com.xj.mongo.MessageMongo;

@Component
@Path("/communities/{communityId}/monitorComplaints")
@Scope("prototype")
public class MonitorComplaintsResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private MonitorComplaintsDao monitorComplaintsDao;
	
	@Autowired
	private MessageMongo messageMongo;
	
	@GET
	public String getMonitorComplaintsList(@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize 
			, @QueryParam("startTime") String startTime , @QueryParam("endTime") String endTime , @QueryParam("sort") String sort
			, @QueryParam("name") String name , @PathParam("communityId") Integer communityId){ // 获取投诉监控列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Integer start = null;
			Integer end = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startTime != null && endTime != null && !"".equals(startTime) && !"".equals(endTime)){
				startTime += " 00:00:01";
				endTime += " 23:59:59";
				start = (int)(format.parse(startTime).getTime() / 1000l);
				end = (int)(format.parse(endTime).getTime() / 1000l);
			}
			Page<MonitorComplaints> page = monitorComplaintsDao.getMonitorComplaintsList(communityId, start, end, sort, name, pageNum, pageSize, 10);
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
	
	@GET
	@Path("/top")
	public String getTop(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			MonitorComplaintsTop monitorComplaintsTop = monitorComplaintsDao.getComplaintsTop();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(monitorComplaintsTop);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/messages")
	public String getMessages(@QueryParam("emobIdUser") String emobIdUser , @QueryParam("emobIdShop") String emobIdShop 
			, @QueryParam("timestamp") Long timetamp , @QueryParam("isMoreThan") String isMoreThan){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			boolean isMoreThanBoolean = true;
			if("true".equals(isMoreThan)){
				isMoreThanBoolean = true;
			}else if("false".equals(isMoreThan)){
				isMoreThanBoolean = false;
			}
			List<Messages> list = messageMongo.getListMessage(emobIdUser, emobIdShop, timetamp , isMoreThanBoolean);
			List<Messages> list2 = messageMongo.getListMessage(emobIdShop, emobIdUser, timetamp , isMoreThanBoolean);
			List<Messages> listResult = new ArrayList<Messages>();
			listResult.addAll(list);
			listResult.addAll(list2);
			ListSort<Messages> sort = new ListSort<Messages>();
			sort.Sort(listResult, "getTimestamp", "");
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
}
