package com.xj.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xj.bean.Activities;
import com.xj.bean.ActivitiesSimple;
import com.xj.bean.ActivityGroupBean;
import com.xj.bean.ActivityMembers;
import com.xj.bean.ActivityPhoto;
import com.xj.bean.ExtNode;
import com.xj.bean.MemberBean;
import com.xj.bean.Page;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.dao.ActivitiesDao;
import com.xj.dao.ActivityGroupDao;
import com.xj.dao.ActivityPhotoDao;
import com.xj.dao.MyUserDao;
import com.xj.httpclient.apidemo.EasemobChatGroups;
import com.xj.jodis.JodisUtils;
import com.xj.mongo.MongoUtils;
import com.xj.thread.PushThread;

import redis.clients.jedis.Jedis;

@Service("activityService")
public class ActivityService {
	
	Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
	private ActivitiesDao activitiesDao;
	
	@Autowired
	private ActivityPhotoDao activityphotodao;
	
	@Autowired
	private ActivityGroupDao activityGroupDao;
	
	@Autowired
	private MyUserDao myUserDao;

	private Gson gson = new Gson();
	
	/**
	 * 添加活动
	 * @param activities
	 * @return
	 * @throws Exception
	 */
	public String addActivity(Activities activities) throws Exception{
		  Long unixTime = System.currentTimeMillis();
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		 if(activities.getActivityTime() == null){
			 activities.setActivityTime((int)(System.currentTimeMillis() / 1000l));
	        }
	        ActivityGroupBean groupBeam = new ActivityGroupBean();
	        groupBeam.setIspublic(1);
	        groupBeam.setMaxusers(500);
	        groupBeam.setApproval(0);
	        groupBeam.setOwner(activities.getEmobIdOwner());
	        groupBeam.setGroupname(activities.getActivityTitle());
	        groupBeam.setDesc(activities.getActivityDetail());
	        
	        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode(); // 群组成员集合
	        ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode(); //创建群组信息
	        List<MemberBean> numbers = groupBeam.getMumbers();
	        MemberBean memberBean = new MemberBean();
	        memberBean.setMemberName(activities.getEmobIdOwner());
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
//	        System.out.println(dataObjectNode + " ======== data node");
	      
	        ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
	        LOGGER.error("huan xin create used time is -------"+(System.currentTimeMillis() - unixTime));
	        	JsonNode objectNode = creatChatGroupNode.get("data");
	          String groupId =  objectNode.get("groupid").asText();
			
	        
	        activities.setEmobGroupId(groupId);
	        int id = activitiesDao.addAcitivity(activities);
	        LOGGER.error("addActivity time is -------"+(System.currentTimeMillis() - unixTime));
	        if(id > 0){
	        	List<Users> list = myUserDao.getUserList(activities.getCommunityId(), "owner");
	        	ExtNode ext = new ExtNode();
	        	ext.setCMD_CODE(110);
	        	ext.setTitle("新活动");
	        	ext.setContent(groupBeam.getGroupname());
	        	ext.setNickname("新活动");
	        	ExecutorService executorService = Executors.newSingleThreadExecutor();
	        	PushTask pushTask = new PushTask();
				pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
//				pushTask.setPushStr(result);
				pushTask.setScene("新建活动");
				pushTask.setServiceName("activity");
//				pushTaskDao.addPushTask(pushTask);
	        	executorService.execute(new PushThread(ext, list, groupBeam.getGroupname()));
	        	executorService.shutdown();
//	        	EasemobChatMessage.testChatMessages(list, EasemobIMUsers.getAminEmobId(), "新活动："+groupBeam.getGroupname(), ext);
	        	 LOGGER.error("huanxin message is -------"+(System.currentTimeMillis() - unixTime));
	        	 
	        	 
	        	 if(activities.getPhotoList()!=null && activities.getPhotoList().size()>0){
	        		 ActivityPhoto ap = new ActivityPhoto();
		        	 ap.setActivityId(id);
	        		 ap.setPhotoUrl(activities.getPhotoList().get(0).getPhotoUrl());
	        		 ap.setCreateTime((int)(System.currentTimeMillis() / 1000l));
	        		 activityphotodao.addPhoto(ap);
	        	 }

	        }
	        ActivityMembers activityGroup2 = activityGroupDao.isEmpty(activities.getEmobIdOwner(),groupId);
			if(activityGroup2 == null){
				activityGroup2 = new ActivityMembers();
				activityGroup2.setEmobGroupId(groupId);
				activityGroup2.setCommunityId(activities.getCommunityId());
				activityGroup2.setEmobUserId(activities.getEmobIdOwner());
				activityGroup2.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				activityGroup2.setGroupStatus("block");
				activityGroupDao.addActivityGroup(activityGroup2);
				LOGGER.error(" add group is -------"+(System.currentTimeMillis() - unixTime));
			}
	        if(id>0){
	            resultStatusBean.setStatus("yes");
	            resultStatusBean.setResultId(id);
	            return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	        }else{
	            resultStatusBean.setStatus("no");
	            return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	        }
		
		
	}
	/**
	 * 生活圈添加群
	 * @param activities
	 * @return
	 * @throws Exception
	 */
	public String addCircleActivity(Activities activities) throws Exception{
		  Long unixTime = System.currentTimeMillis();
		
		 if(activities.getActivityTime() == null){
			 activities.setActivityTime((int)(System.currentTimeMillis() / 1000l));
	        }
	        ActivityGroupBean groupBeam = new ActivityGroupBean();
	        groupBeam.setIspublic(1);
	        groupBeam.setMaxusers(500);
	        groupBeam.setApproval(0);
	        groupBeam.setOwner(activities.getEmobIdOwner());
	        groupBeam.setGroupname(activities.getActivityTitle());
	        groupBeam.setDesc(activities.getActivityDetail());
	        
	        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode(); // 群组成员集合
	        ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode(); //创建群组信息
	        List<MemberBean> numbers = groupBeam.getMumbers();
	        MemberBean memberBean = new MemberBean();
	        memberBean.setMemberName(activities.getEmobIdOwner());
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
	      
	        ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
	        LOGGER.error("huan xin create used time is -------"+(System.currentTimeMillis() - unixTime));
	        	JsonNode objectNode = creatChatGroupNode.get("data");
	          String groupId =  objectNode.get("groupid").asText();
			
	        
	        activities.setEmobGroupId(groupId);
	        activitiesDao.addAcitivity(activities);
	        ActivityMembers activityGroup2 = activityGroupDao.isEmpty(activities.getEmobIdOwner(),groupId);
			if(activityGroup2 == null){
				activityGroup2 = new ActivityMembers();
				activityGroup2.setEmobGroupId(groupId);
				activityGroup2.setCommunityId(activities.getCommunityId());
				activityGroup2.setEmobUserId(activities.getEmobIdOwner());
				activityGroup2.setCreateTime((int)(System.currentTimeMillis() / 1000l));
				activityGroup2.setGroupStatus("block");
				activityGroupDao.addActivityGroup(activityGroup2);
				LOGGER.error(" add group is -------"+(System.currentTimeMillis() - unixTime));
			}
			return groupId;
	}
	
	
	public Page<ActivitiesSimple> getActivitiesByStatusAndText(String text, Integer communityId, String status, String string2, Integer pageNum, Integer pageSize, int i) throws Exception {
		Page<ActivitiesSimple> page = activitiesDao.getActivitiesByStatusAndTextSimple(text, communityId, status, "activity", pageNum, pageSize, 10);
		List<ActivitiesSimple> pageData = new ArrayList<ActivitiesSimple>();
		try (Jedis jedis = JodisUtils.getResource()) {
			String key = "bangzhu_activity_praise_" + communityId;
			String string = jedis.get(key);
			if (StringUtils.isNotBlank(string)) {
				if (pageNum == 1) {
					ActivitiesSimple activities = activitiesDao.getActivitiesByIdSimple(Integer.parseInt(string));
					activities.setPhotoList(activitiesDao.getActivitiePhotos(activities.getActivityId()));
//					activities.setSuperPraise("yes");
//					activities.setBzPraiseSum(0);
					pageData.add(activities);
				}
				List<ActivitiesSimple> list = page.getPageData();
				for (ActivitiesSimple activities : list) {
					if (activities.getActivityId() == Integer.parseInt(string)) {
						continue;
					}
					pageData.add(activities);
				}
				page.setPageData(pageData);
			}
		}
		return page;
	}
	
	/**
	 * 根据小区id获取小区专属属性
	 * 
	 * @param communityId
	 * @param type 属性类别 图片还是其他
	 * @param service 模块名
	 * @return
	 * @throws Exception
	 */
	public String getActivityPhoto(Integer communityId, String type, String service) {
		try {
			return activitiesDao.getActivityThemePhoto(communityId, type, service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Page<ActivitiesSimple> getListWithPage(Integer communityId, String userId, Integer pageNum, Integer pageSize, int i) throws Exception {
		Page<ActivitiesSimple> page = activitiesDao.getListWithPageSimple(communityId, pageNum, pageSize, 10);// activitiesDao.getListWithPage中实现了对photo的填充
		List<ActivitiesSimple> pageData = new ArrayList<ActivitiesSimple>();
		try (Jedis jedis = JodisUtils.getResource()) {
			String key = "bangzhu_activity_praise_" + communityId;
			String string = jedis.get(key);
			if (StringUtils.isNotBlank(string)) {
				if (pageNum == 1) {
					ActivitiesSimple activities = activitiesDao.getActivitiesByIdSimple(Integer.parseInt(string));
					activities.setPhotoList(activitiesDao.getActivitiePhotos(activities.getActivityId()));
//					activities.setSuperPraise("yes");
//					activities.setBzPraiseSum(0);
					pageData.add(activities);
				}

				List<ActivitiesSimple> list = page.getPageData();
				for (ActivitiesSimple activities : list) {
					if (activities.getActivityId() == Integer.parseInt(string)) {
						continue;
					}
					pageData.add(activities);
				}
				page.setPageData(pageData);
			}
		}
		return page;
	}
	
	public List<DBObject> getHistoryById(String messageTo,String chatType){
		DBObject ref = new BasicDBObject();
		ref.put("messageTo", messageTo);
		ref.put("chatType", chatType);
		
		BasicDBObject keys = new BasicDBObject();
		keys.put("created", 1);
		keys.put("nickname", 1);
		keys.put("avatar", 1);
		keys.put("messageFrom", 1);
		keys.put("msgType", 1);
		keys.put("msg", 1);
		keys.put("url", 1);
		keys.put("_id", 0);
		
		DBCursor modify = MongoUtils.getCollection("huanXinMassage").find(ref, keys);
		List<DBObject> result = new ArrayList<DBObject>();
		while (modify.hasNext()) {
			result.add(modify.next());
		}
		
		return result;
	}
}