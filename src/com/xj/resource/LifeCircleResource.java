package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.ExtNode;
import com.xj.bean.IdCount;
import com.xj.bean.LifeCircle;
import com.xj.bean.LifeCircleDetail;
import com.xj.bean.LifePraise;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.bean.life.Favorite;
import com.xj.dao.MyUserDao;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.CirclePraiseService;
import com.xj.service.LifeCircleService;
import com.xj.thread.PushThread;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/lifeCircle")
public class LifeCircleResource {

	private Gson gson = new Gson();
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private LifeCircleService lifeCircleService;
	
	@Autowired
	private CirclePraiseService circlePraiseService;
	
	private static final String qiniuUrlRoot = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	
	/**
	 * 使用量头部统计
	 */
	@GET
	@Path("/getLifeCircleTop")
	public String getLifeCircleTop(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(lifeCircleService.getLifeCircleTop(communityId, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 使用列表
	 */
	@GET
	@Path("/getLifeCircleList")
	public String getLifeCircleList(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("sqlTime") List<String> sqlTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			List<Integer> sqlTime2 = new ArrayList<Integer>(sqlTime.size());
			for (int i = 0; i < sqlTime.size(); i++) {
				sqlTime2.add((int) (sdf.parse(sqlTime.get(i)).getTime()/1000));
			}
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(lifeCircleService.getLifeCircleList(communityId, startTimeInt, endTimeInt,sqlTime2));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 话题列表
	 */
	@GET
	@Path("/selectLifeCircleList")
	public String selectLifeCircleList(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("type") String type) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(lifeCircleService.selectLifeCircleList(communityId, startTimeInt, endTimeInt,type));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 话题列表
	 */
	@GET
	@Path("/getLifeCircleListH")
	public String getLifeCircleListH(@PathParam("communityId") Integer communityId,@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.getLifeCircleList(communityId,pageNum,pageSize));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	
	/**
	 * 话题列表
	 */
	@GET
	@Path("/getLifeCireDelit")
	public String getLifeCireDelit(@QueryParam("life_circle_id") String life_circle_id) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.getLifeCireDelit(life_circle_id));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 *  评论
	 */
	@GET
	@Path("/getLifeCircleDetail")
	public String getLifeCircleDetail(@QueryParam("emob_id_to") String emob_id_to) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.getLifeCircleDetail(emob_id_to));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 敏感词
	 */
	@GET
	@Path("/getSensitive")
	public String getSensitive(@PathParam("communityId") Integer communityId,@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.getSensitive(communityId,pageNum,pageSize));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 屏蔽
	 */
	@GET
	@Path("/pingbi/{lifeCircleId}")
	public String pingbi(@PathParam("lifeCircleId") Integer lifeCircleId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.pingbi(lifeCircleId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 修改敏感词状态
	 */
	@GET
	@Path("/upStatus")
	public String upStatus(@QueryParam("id") Integer id,@QueryParam("status") String status) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.upStatus(id,status));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@POST
	@Path("/addLifeCircle")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public void addLifeCircle(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			FormDataMultiPart form,
			@FormDataParam("existImages") List<FormDataBodyPart> existImages,
			@FormDataParam("communityIds") String communityIds,
			@FormDataParam("emobIds") String emobIds,
			@FormDataParam("lifeContent") String lifeContent,
			@FormDataParam("group") Integer group,
			@FormDataParam("community_id") String community_id,
			@FormDataParam("communityName") String communityName
			) throws Exception { // 发布生活圈
		String message = "";
		List<String> photoes = new ArrayList<String>();
		List<FormDataBodyPart> images = form.getFields("file_");
		if (!CollectionUtils.isEmpty(images)) {
			for (int i = 0; i < images.size(); i++) {
				String contentFile = uploadImage(images.get(i).getValueAs(InputStream.class));
				if (null == contentFile) {
					message += "上传图片失败!";
				} else {
					photoes.add(contentFile);
				}
			}
		}
		if (!CollectionUtils.isEmpty(existImages)) {
			for (int i = 0; i < existImages.size(); i++) {
				String file = existImages.get(i).getValue();
				if (StringUtils.isNotBlank(file)) {
					photoes.add(file);
				}
			}
		}
		
		try {
			String[] _communityIds = communityIds.split(",");
			String[] _emobIds = emobIds.split(",");
			if (_communityIds.length != _emobIds.length) {
				throw new IllegalArgumentException("请传入小区和对应的小区发布者");
			}
			
			int time = (int)(System.currentTimeMillis() / 1000L);
			for (int i = 0; i < _communityIds.length; i++) {
				LifeCircle lifeCircle = new LifeCircle();
				lifeCircle.setCommunityId(Integer.valueOf(_communityIds[i]));
				lifeCircle.setEmobId(_emobIds[i]);
				lifeCircle.setLifeContent(lifeContent);
				lifeCircle.setIsCreate(group);
				lifeCircle.setCreateTime(time);
				lifeCircle.setUpdateTime(time);
				if(CollectionUtils.isNotEmpty(photoes)){
					StringBuilder sb = new StringBuilder();
					for (String photoe : photoes) {
						sb.append(photoe+",");
					}
					sb.deleteCharAt(sb.length()-1);
					lifeCircle.setPhotoes(sb.toString());
				}
				
				lifeCircleService.addLifeCircleService(lifeCircle);
			}
			message = "发布成功!";
		} catch (Exception e) {
			e.printStackTrace();
			message = "发布失败!";
		}
		
		request.setAttribute("message", message);
		String path = request.getContextPath();
		communityName= java.net.URLEncoder.encode(communityName.toString(),"UTF-8"); 
		String url = path+"/jsp/navy/navy-life.jsp?community_id="+community_id+"&communityName="+communityName;
		response.sendRedirect(url);
	}
	
	@POST
	@Path("{emobId}")
	public String addLifeCircleContent(String json , @PathParam("emobId") String emobId , @PathParam("communityId") Integer communityId){ // 评论
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			LifeCircleDetail lifeCircleDetail = gson.fromJson(json, LifeCircleDetail.class);
			int time = (int)(System.currentTimeMillis() / 1000l);
			lifeCircleDetail.setCreateTime(time);
			lifeCircleDetail.setUpdateTime(time);
			lifeCircleDetail.setPraiseSum(0);
			lifeCircleDetail.setEmobIdFrom(emobId);
			int resultId = lifeCircleService.addLifeCircleDetail(lifeCircleDetail);
			if(resultId > 0){
				List<Users> users = new ArrayList<Users>();
				Users user = new Users();
				UsersApp usersApp = myUserDao.getUserByEmobId(lifeCircleDetail.getEmobIdTo());
				user.setEmobId(lifeCircleDetail.getEmobIdTo());
				user.setEquipment(usersApp.getEquipment());
//				users.add(lifeCircleDetail.getEmobIdTo());
				users.add(user);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
				ExtNode ext = new ExtNode();
				usersApp = myUserDao.getUserByEmobId(emobId);
	        	ext.setCMD_CODE(121);
	        	ext.setTitle("新评论");
	        	String content = lifeCircleDetail.getDetailContent();
	        	if(content.length() > 14){
	        		content = content.substring(0,14);
	        	}
	        	if(usersApp != null){
	        		ext.setContent("评论了你的生活圈");
	        	}
	        	ext.setCommunityId(communityId);
	        	ext.setNickname(usersApp.getNickname());
	        	ExecutorService executorService = Executors.newSingleThreadExecutor();
	        	PushTask pushTask = new PushTask();
				pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
			
				pushTask.setScene("新评论");
				pushTask.setServiceName("circleAddDetailContent");
////				pushTaskDao.addPushTask(pushTask);
	        	executorService.execute(new PushThread(ext, users, content));
	        	executorService.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("addLifeCircleContent error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("{emobId}/praise")
	public String addLifeCirclePraise( @PathParam("emobId") String emobId , String json , @PathParam("communityId") Integer communityId){ //  status == 1  为发布者点赞  status == 2 为评论者点赞
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			LifePraise lifePraise = gson.fromJson(json, LifePraise.class);
			lifePraise.setEmobIdFrom(emobId);
			lifePraise.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			boolean result = circlePraiseService.addCirclePraise(lifePraise, lifePraise.getStatus());
			if(result){
				resultStatusBean.setStatus("yes");
				List<Users> users = new ArrayList<Users>();
				Users user = new Users();
				user.setEmobId(lifePraise.getEmobIdTo());

	        	UsersApp userApp = myUserDao.getUserByEmobId(lifePraise.getEmobIdTo());
	        	user.setEquipment(userApp.getEquipment());
//				users.add(lifeCircleDetail.getEmobIdTo());
				users.add(user);
			
				ExtNode ext = new ExtNode();
	        	ext.setCMD_CODE(122);
	        	ext.setTitle("新的赞");
	        	userApp = myUserDao.getUserByEmobId(emobId);
	        	ext.setContent("赞了你的人品");
	        	ext.setNickname(userApp.getNickname());
	        	ext.setCommunityId(communityId);
	        	ExecutorService executorService = Executors.newSingleThreadExecutor();
	        	PushTask pushTask = new PushTask();
				pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
			
				pushTask.setScene("新的赞");
				pushTask.setServiceName("circleAddDetailContent");
////				pushTaskDao.addPushTask(pushTask);
	        	executorService.execute(new PushThread(ext, users, ext.getContent()));
	        	executorService.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("addLifeCircleContent error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 *检查生活圈更新条数
	 */
	@GET
	@Path("/getLifeCircleUpdate")
	public String getLifeCircleUpdate(@PathParam("communityId") Integer communityId,@QueryParam("lastQuitTime") Integer lastQuitTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			List<IdCount> icList = lifeCircleService.getLifeCircleUpdateCount(communityId, lastQuitTime);
			resultStatusBean.setInfo(icList);
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("no");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 *添加收藏 
	 */
	@POST
	@Path("/favorite")
	public String addFavorite(String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Favorite favorite = gson.fromJson(json,Favorite.class);
			favorite.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			lifeCircleService.addFavorite(favorite);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("addLifeCircleContent error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 删除收藏
	 */
	@GET
	@Path("/delectFavorite")
	public String delFavorite(@QueryParam("favorite_id") Integer favorite_id) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.delFavorite(favorite_id));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 话题列表
	 */
	@GET
	@Path("/getFavoriteLifeCire")
	public String getFavoriteLifeCire(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		
		try {
			resultStatusBean.setInfo(lifeCircleService.getFavoriteLifeCire(pageNum,pageSize));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 删除图片
	 */
	@GET
	@Path("/delectLifePhoto")
	public String delectLifePhoto(@QueryParam("life_photo_id") Integer life_photo_id) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.delectLifePhoto(life_photo_id));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 对已收藏话题检索已存在小区
	 */
	@GET
	@Path("/getExisting")
	public String getExisting(@QueryParam("life_circle_id") Integer life_circle_id) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(lifeCircleService.getExisting(life_circle_id));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	private String uploadImage(InputStream inputStream) {
		String fileName = UUID.randomUUID().toString().replaceAll("-", "");
		File file = writeToFile(inputStream, this.getClass().getClassLoader().getResource("/").getPath() + fileName);
		if (null == file) {
			return null;
		}
		
		try {
			QiniuFileSystemUtil.upload(file, fileName);
			
			return qiniuUrlRoot + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private File writeToFile(InputStream is, String location) {
		File file = new File(location);
		try (OutputStream os = new FileOutputStream(file)) {
			byte buffer[] = new byte[4 * 1024];
			while ((is.read(buffer)) != -1) {
				os.write(buffer);
			}
			os.flush();
			
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}