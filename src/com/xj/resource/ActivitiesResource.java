package com.xj.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.Activities;
import com.xj.bean.ActivitiesSimple;
import com.xj.bean.ActivityGroupBean;
import com.xj.bean.ActivityMembers;
import com.xj.bean.ActivityPhoto;
import com.xj.bean.ActivityUpdateBean;
import com.xj.bean.MemberBean;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.ActivitiesDao;
import com.xj.dao.ActivityGroupDao;
import com.xj.httpclient.apidemo.EasemobChatGroups;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.service.ActivityService;

/**
 * 活动模块
 */
@Component
@Scope("prototype")
@Path("/communities/{communityId}/users/{emobIdUser}/activities")
public class ActivitiesResource {
	
    Logger logger = Logger.getLogger(ActivitiesResource.class);
    
    @Autowired
    private ActivitiesDao activitiesDao;
    
    @Autowired
    private ActivityGroupDao activityGroupDao;
    
    @Autowired
    private ActivityService activityService;
   
    
    private Gson gson = new Gson();
    private static final String qiniuUrlRoot = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
    /**
     * 添加活动
     * @param requestJson
     * @param communityId
     * @param emobIdUser
     * @return
     */
    @POST
    public String addActivity(String requestJson , @PathParam("communityId") Integer communityId , @PathParam("emobIdUser") String emobIdUser){
    
        Gson gson = new Gson();

        //parse request to bean
        Activities activitiesBean = gson.fromJson(requestJson, Activities.class);
        activitiesBean.setEmobIdOwner(emobIdUser);
        activitiesBean.setCommunityId(communityId);
        activitiesBean.setType("activity");
        //result bean
        String result = "";
        try {
        	result = activityService.addActivity(activitiesBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("add activity is error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return result;
    }
    
    
    
    /**
     * 修改活动
     * @param json
     * @param emobGroupId
     * @return
     */
    @PUT
    @Path("/{emobGroupId}/update")
    public String updateActivity(String json , @PathParam("emobGroupId") String emobGroupId){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	resultStatusBean.setStatus("no");
    	try {
			
			Activities activities = gson.fromJson(json, Activities.class);
			String name = activities.getActivityTitle();
			boolean result = EasemobChatGroups.updateGroup(emobGroupId, name);
			if(result){
				boolean update = activitiesDao.updateActivity(emobGroupId, name);
				if(update){
					resultStatusBean.setStatus("yes");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("update Activity error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
    @PUT
    @Path("/{activityId}")
    public String modifyOrderState(@PathParam("activityId") Integer activityId ,String requestJson , @PathParam("emobIdUser") String emobIdUser , @PathParam("communityId") Integer communityId){
    	//--------------------------真实创建群组
    	
        logger.info("request json is :"+requestJson);
        Gson gson = new Gson();
        ActivityGroupBean groupBeam = gson.fromJson(requestJson, ActivityGroupBean.class);
        groupBeam.setOwner(emobIdUser);
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode(); // 群组成员集合
        ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode(); //创建群组信息
        List<MemberBean> numbers = groupBeam.getMumbers();
        MemberBean memberBean = new MemberBean();
        memberBean.setMemberName(emobIdUser);
        if(numbers != null){
        	 Iterator<MemberBean> it = numbers.iterator();
             while(it.hasNext()){
             	arrayNode.add(it.next().getMemberName()); // 为成员列表添加成员
             }
        }
       
        dataObjectNode.put("groupname", groupBeam.getGroupname()); // 群组的名字        *必须填写
        dataObjectNode.put("desc", groupBeam.getDesc()); // 群组的描述      *必须填写
        boolean isPublic = false;
        if(groupBeam.getIspublic() == 1){
        	isPublic = true;
        }
        boolean isApproval = false;
        if(groupBeam.getApproval() == 1){
        	isApproval = true;
        }
        dataObjectNode.put("public", isPublic); // 是否公开    *必须填写
        dataObjectNode.put("maxusers", groupBeam.getMaxusers()); // 最大人数
        dataObjectNode.put("approval", isApproval); //加入公开群是否需要批准, 没有这个属性的话默认是true, 此属性为可选的
        dataObjectNode.put("owner", groupBeam.getOwner()); //群组的管理员, 此属性为必须的
        dataObjectNode.put("members", arrayNode); // 群组成员
//        System.out.println(dataObjectNode + " ======== data node");
        ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
        Long tempStamp = creatChatGroupNode.get("timestamp").longValue();
        JsonNode objectNode = creatChatGroupNode.get("data");
//        System.out.println(creatChatGroupNode.get("data"));
        String groupId = objectNode.get("groupid").asText();
        
//        System.out.println(creatChatGroupNode.toString());  // 创建群组的返回值
        
        //---------------------------创建群组完毕
        ActivityUpdateBean activityUpdateBean = new ActivityUpdateBean();
        activityUpdateBean.setStatus("ongoing");
        Activities activities = new Activities();
        activities.setActivityId(activityId);
        activities.setEmobGroupId(groupId);
        activities.setStatus("ongoing");
        activities.setCreateTime((int)(tempStamp / 1000l));
        boolean isUpdateSuccess = false;
        try {
			isUpdateSuccess = activitiesDao.updateActivies(activities);
			ActivityMembers activityGroup2 = activityGroupDao.isEmpty(emobIdUser,groupId);
			if(activityGroup2 == null){
				activityGroup2 = new ActivityMembers();
				activityGroup2.setEmobGroupId(groupId);
				activityGroup2.setCommunityId(communityId);
				activityGroup2.setEmobUserId(emobIdUser);
				activityGroup2.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				activityGroup2.setGroupStatus("block");
				activityGroupDao.addActivityGroup(activityGroup2);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    //    activitiesDao.modifyActivityState(activityId, activityUpdateBean);
        ResultStatusBean resultStatusBean = new ResultStatusBean();
        if(isUpdateSuccess){
            resultStatusBean.setStatus("yes");
          
            return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
        }
        
        return null;
    }
    
//    @GET
//    @Path("/list/{text}")
//    public String getActivities(@PathParam("communityId") Integer communityId , @PathParam("text") String text , @QueryParam("pageSize") Integer pageSize , @QueryParam("pageNum") Integer pageNum){
//    	ResultStatusBean resultStatusBean = new ResultStatusBean();
//    	try {
//			Page<Activities> page = activitiesDao.getActivitiesByStatusAndText(text, communityId, "ongoing", pageNum, pageSize, 10);
//			resultStatusBean.setStatus("yes");
//			resultStatusBean.setInfo(page);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			resultStatusBean.setStatus("error");
//			return gson.toJson(resultStatusBean);
//		}
//    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
//    }
//    
    
    
//    /**
//     * 查询活动 - 根据活动名
//     */
//    @GET
//    public String getActivities(@PathParam("emobIdUser") String userId , @QueryParam("q") String text , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , @PathParam("communityId") Integer communityId){
//    	ResultStatusBean result = new ResultStatusBean();
//    	Page<Activities> page = null;
//    	result.setStatus("no");
//    	if(pageNum == null || pageSize == null){
//    		result.setMessage("pageNum或pageSize不能为空");
//    		result.setStatus("error");
//    		return gson.toJson(result).replace("\"null\"", "\"\"");
//    	}
//    	if(text != null){
//    		try {
//				page = activitiesDao.getActivitiesByStatusAndText(text, communityId, "ongoing", "activity", pageNum, pageSize, 10);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				result.setStatus("error");
//				return gson.toJson(result).replace("\"null\"", "\"\"");
//			}
//    		result.setStatus("yes");
//    		result.setInfo(page);
//    		return gson.toJson(result).replace("\"null\"", "\"\"");
//    	}
//    	
//    	if(userId != null){
//    		try {
//    			page = activitiesDao.getListWithPage(communityId,userId, pageNum, pageSize, 10);
//    			if(page != null){
//    				List<Activities> list = page.getPageData();
//    				for(int i = 0 ; i < list.size() ; i++){
//    					Integer activityId = list.get(i).getActivityId();
//    					List<ActivityPhoto> photoList = activityPhotoDao.getPhotoList(activityId);
//    					list.get(i).setPhotoList(photoList);
//    				}
//    				page.setPageData(list);
//    				result.setStatus("yes");
//    				result.setInfo(page);
//    				return gson.toJson(result).replace("\"null\"", "\"\"");
//    			}else{
//    				result.setMessage("没有搜索到任何信息");
//    	    		result.setStatus("no");
//    			}
//    		} catch (Exception e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    			result.setMessage("查询信息时发生错误了");
//        		result.setStatus("no");
//        		return gson.toJson(result).replace("\"null\"", "\"\"");
//    		}
//    	}else{
//    		result.setMessage("userId不能为空");
//    		result.setStatus("no");
//    	}
//    	
//    	return gson.toJson(result).replace("\"null\"", "\"\"");
//    }
    
    /**
	 * 查询活动 - 根据活动名
	 * @param userId
	 * @param text
	 * @param pageNum
	 * @param pageSize
	 * @param communityId
	 * @return
	 */
	@GET 
	public String getActivities(@PathParam("emobIdUser") String userId , @QueryParam("q") String text , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , @PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		if (pageNum == null || pageSize == null) {
			result.setMessage("pageNum或pageSize不能为空");
			result.setStatus("error");
			return gson.toJson(result).replace("\"null\"", "\"\"");
		}
		String photoUrl = activityService.getActivityPhoto(communityId,"photo","activity");//获取活动的主题图片地址
		
		if (text != null) {
			try {
				Page<ActivitiesSimple> page = activityService.getActivitiesByStatusAndText(text, communityId, "ongoing", "activity", pageNum, pageSize, 10);
				result.setInfo(page);
				result.setOthers(photoUrl);
				result.setStatus("yes");
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus("error");
			}
			return gson.toJson(result).replace("\"null\"", "\"\"");
		}
		
		if (userId == null) {
			result.setMessage("userId不能为空");
			result.setStatus("no");
			return gson.toJson(result).replace("\"null\"", "\"\"");
		}
		
		try {
			result.setStatus("yes");
			Page<ActivitiesSimple> page = activityService.getListWithPage(communityId, userId, pageNum, pageSize, 10);
			result.setOthers(photoUrl);
			result.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("查询信息时发生错误了");
			result.setStatus("no");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}    
    
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public void addNewActivity(
							@Context HttpServletRequest request,
							@Context HttpServletResponse response,
							@FormDataParam("activityTitle") String activityTitle,
							@FormDataParam("member.birth") String time,
							@FormDataParam("place") String place,
							@FormDataParam("communitiesTotal") String communitiesTotal,
							@FormDataParam("tryOutUser") String tryOutUser,
							@FormDataParam("activityDetail") String activityDetail,
							@FormDataParam("activity_Img") InputStream posterInputStream,
							@FormDataParam("activity_Img") FormDataContentDisposition posterFileDisposition,
							@FormDataParam("community_id") String community_id,
							@FormDataParam("communityName") String communityName
							) throws ServletException, IOException{
		//String uri = "/jsp/activity/activities-new.jsp";
		String message = "";
		String posterFile = uploadImage(posterInputStream, posterFileDisposition);
		if (StringUtils.isBlank(posterFile)) {
			message += "上传图片失败!";
		}
		
		Activities as = new Activities();
		as.setActivityTitle(activityTitle);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date d;  
		try {
			d = sdf.parse(time);
			Integer l = (int)(d.getTime()/1000l);
			
			as.setActivityTime(l);
			
			as.setPlace(place);
			
			Integer communityId = Integer.parseInt(communitiesTotal);
			as.setCommunityId(communityId);
			as.setEmobIdOwner(tryOutUser);
			as.setActivityDetail(activityDetail);
			ActivityPhoto ap = new ActivityPhoto();
			ap.setPhotoUrl(posterFile);
			List<ActivityPhoto> list = new ArrayList<ActivityPhoto>();
			list.add(ap);
			as.setPhotoList(list);
			activityService.addActivity(as);
			//uri = "/jsp/activity/activities_all?community_id="+communityId+"&communityName="+communityName;
		} catch (Exception e) {
			e.printStackTrace();
		}  
		message="发布成功";
		request.setAttribute("message", message);
		String path = request.getContextPath();
		communityName= java.net.URLEncoder.encode(communityName.toString(),"UTF-8"); 
		String url = path+"/jsp/activity/activities-new.jsp?community_id="+community_id+"&communityName="+communityName;
		response.sendRedirect(url);
	}
	
	
	@GET
	@Path("/history")
	public String getHistoryById(@QueryParam("messageTo") String messageTo,@QueryParam("chatType") String chatType){

		 	ResultStatusBean resultStatusBean = new ResultStatusBean();
	        List<DBObject> result = new ArrayList<DBObject>();
	        try {
	        	result = activityService.getHistoryById(messageTo, chatType);
	        	resultStatusBean.setInfo(result);
	        	resultStatusBean.setStatus("yes");
			} catch (Exception e) {
				e.printStackTrace();
				resultStatusBean.setStatus("error");
			}
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");	
	}
   
	
	private String uploadImage(InputStream inputStream, FormDataContentDisposition posterFileDisposition) {
		if (null != posterFileDisposition && StringUtils.isBlank(posterFileDisposition.getFileName())) {
			return null;
		}
		
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
