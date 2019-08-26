package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.xj.bean.Express;
import com.xj.bean.ExtNode;
import com.xj.bean.Page;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.dao.ExpressDao;
import com.xj.service.UserService;
import com.xj.thread.PushThread;
import com.xj.utils.SingletonClient;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/express")
public class ExpressResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ExpressDao expressDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 添加快件
	 */
	@POST
	public String addExpress(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Express express = gson.fromJson(json, Express.class);
			express.setCommunityId(communityId);
			express.setExpressCreateTime((int)(System.currentTimeMillis() / 1000l));
			Users users = userService.getUserByPhone(express.getExpressPhone());
			if(users != null){ // 发送app消息通知
				express.setExpressUserType(0); // app用户
				ExtNode ext = new ExtNode();
				String text = express.getExpressStationInform();
				text = text.replace("XXX", express.getExpressName());
				ext.setContent(text);
				ext.setCMD_CODE(104);
				ext.setTitle("通知公告");
				ext.setNickname("帮帮");
				Long i= new Date().getTime();
				ext.setTimestamp(i / 1000l);
				List<Users> list = new ArrayList<Users>();
				list.add(users);
				try {
					PushTask pushTask = new PushTask();
					pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
					pushTask.setScene("快递通知");
					pushTask.setServiceName("express");
					ExecutorService executorService = Executors.newSingleThreadExecutor();
					executorService.execute(new PushThread(ext, list, text));
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
				resultStatusBean.setMessage("发送成功");
				resultStatusBean.setStatus("yes");
			}else{ // 发送短信
				String phone = express.getExpressPhone();
				String expressMessageInform=express.getExpressMessageInform();
				//发送短信方法
				int i=SingletonClient.getClient().sendSMS(new String[] {phone}, "【帮帮】"+expressMessageInform,"",5);//带扩展码
				if(i>=0){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setMessage("发送成功");
				}else{
					i=SingletonClient.getClient().sendSMS(new String[] {phone}, "【帮帮】"+expressMessageInform,"",5);//带扩展码
					if(i>=0){
						resultStatusBean.setMessage("发送成功");
					}else{
						resultStatusBean.setMessage("发送失败");
					}
				}
				resultStatusBean.setResultId(i);
				express.setExpressUserType(1); // 普通用户
			}
			express.setExpressStatus(0);
			express.setExpressResult(1);
			express.setExpressCollectionTime((int)(System.currentTimeMillis() / 1000l));
			Integer resultId = expressDao.addExpress(express); // 添加一个快递信息
			if(resultId > 0){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 查询快件信息
	 */
	@GET
	public String getExpressList(@PathParam("communityId") Integer communityId,
			@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , 
			@QueryParam("expressType") Integer type){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<Express> page = expressDao.findAllByType(communityId, type, pageNum, pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@GET
	@Path("/like/{text}")
	public String getExpressListByText(@PathParam("communityId") Integer communityId,
			@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , 
			@QueryParam("expressType") Integer type , @PathParam("text") String text){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			resultStatusBean.setInfo(expressDao.findAllByText(communityId, type, text, pageNum, pageSize, 10));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@PUT
	@Path("/{expressId}")
	public String updateExpress(String json , @PathParam("expressId") Integer expressId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Express express = gson.fromJson(json, Express.class);
			express.setExpressId(expressId);
			boolean result = expressDao.updateExpress(express);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	/**
	 * 快递统计 头部
	 */
	@GET
	@Path("/getExpressTop")
	public String getExpressTop(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = Integer.parseInt(String.valueOf(sdf.parse(startTime).getTime()).substring(0, 10));
			Integer endTimeInt = Integer.parseInt(String.valueOf(sdf.parse(endTime).getTime()).substring(0, 10));
			
			resultStatusBean.setInfo(expressDao.getExpressTop(startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 快递统计列表
	 */
	@GET
	@Path("/getExpressList")
	public String getExpressList(
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = Integer.parseInt(String.valueOf(sdf.parse(startTime).getTime()).substring(0, 10));
			Integer endTimeInt = Integer.parseInt(String.valueOf(sdf.parse(endTime).getTime()).substring(0, 10));
			
			resultStatusBean.setInfo(expressDao.getExpressList(pageNum,pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 快递统计详情
	 */
	@GET
	@Path("/getExpressDetail")
	public String getExpressDetail(@QueryParam("type") Integer type,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = Integer.parseInt(String.valueOf(sdf.parse(startTime).getTime()).substring(0, 10));
			Integer endTimeInt = Integer.parseInt(String.valueOf(sdf.parse(endTime).getTime()).substring(0, 10));
			
			resultStatusBean.setInfo(expressDao.getExpressDetail(type,pageNum,pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}