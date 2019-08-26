package com.xj.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.bean.ExtNode;
import com.xj.bean.Messages;
import com.xj.bean.MoralStatistics;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.TryOut;
import com.xj.bean.UserPartner;
import com.xj.bean.UserRegister;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.Liveness;
import com.xj.dao.MyUserDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;
import com.xj.thread.PushNotification2Andriod;
import com.xj.thread.PushNotification2Ios;
import com.xj.thread.PushNotification2Mi;
import com.xj.utils.MD5Util;

@Service("userService")
public class UserService {

	@Autowired 
	private MyUserDao myUserDao;
	
	public Users getUserByName(String userName) throws Exception{
		return myUserDao.getNameByUser(userName);
	}
	
	public Users getUserByCommunityId(String userName , Integer communityId) throws Exception{
		return myUserDao.getUserByCommunityId(communityId, userName);
	}
	
	/**
	 * 游客注册
	 * @param communityId
	 * @param equipment
	 * @return
	 * @throws Exception
	 */
	public Users defaultAddUsers(Integer communityId , String equipment) throws Exception{
		long time = System.currentTimeMillis();
		String emobId = MD5Util.getMD5Str(time + "");
		String passWord = MD5Util.getMD5Str("123");
		String str = MD5Util.getStr();
		
		Users u = new Users();
		u.setCommunityId(communityId);
		u.setCreateTime((int)(time / 1000l));
		u.setEmobId(emobId);
		u.setRole("owner");
		u.setPassword(MD5Util.getMD5Str(passWord + str));
		u.setSalt(str);
		u.setEquipment(equipment);
		u.setUserId(myUserDao.insert(u));
		
		ObjectNode objectNode = EasemobIMUsers.addUser(emobId, passWord);
		JsonNode node = objectNode.get("entities");
		if (null == node) {
			throw new RuntimeException();
		}
		
		boolean result = ((ArrayNode) node).get(0).get("activated").asBoolean();
		if (!result) {
			throw new RuntimeException();
		}
		
		return u;
	}

	/**
	 * 添加用户
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public ResultStatusBean addUsers(Users u) throws Exception { // 添加用户
		ResultStatusBean result = new ResultStatusBean();
		if (myUserDao.getNameByUser(u.getUsername()) != null) {
			result.setMessage("当前用户已经存在");
			result.setStatus("no");
			return result;
		}
		
		if (StringUtils.isBlank(u.getPassword())) {
			return result;
		}
		
		String password = u.getPassword();
		String emobId = MD5Util.getMD5Str(u.getUsername());
		
		ObjectNode objectNode = EasemobIMUsers.addUser(emobId, MD5Util.getMD5Str("&bang#bang@" + password));
		if (objectNode == null || objectNode.get("statusCode") == null || !"200".equals(objectNode.get("statusCode").asText())) {
			result.setStatus("no");
			result.setMessage("添加出现错误此账号已在环信存在但由于不明原因在服务器端没能注册成功");
			return result;
		}
		
		String salt = MD5Util.getStr();
		u.setSalt(salt);
		u.setPassword(MD5Util.getMD5Str(password + salt));
		u.setEmobId(emobId);
		
		Integer resultId = myUserDao.insert(u);
		if (resultId > 0) {
			result.setResultId(resultId);
			result.setEmobId(emobId);
			result.setStatus("yes");
		} else {
			result.setStatus("no");
		}
		
		return result;
	}
	
	public Integer insert(Users u) throws Exception{
		return myUserDao.insert(u);
	}

	public boolean update(Users u) throws Exception{
		return myUserDao.update(u);
	}

	public List<Users> getListUser(Integer communityId) throws Exception{
		return myUserDao.getUserList(communityId);
	}

	public List<Users> getListUser(Integer communityId,String role) throws Exception{
		return myUserDao.getUserList(communityId,role);
	}
	
	public List<String> getEmobIdList(Integer communityId,String role) throws Exception{
		return myUserDao.getEmobIdList(communityId, role);
	}

	public UsersApp getUserByEmobId(String emobId) throws Exception{
		return myUserDao.getUserByEmobId(emobId);
	}

	public Users getUserByUserId(Integer userId) throws Exception{
		return myUserDao.getUserByUserId(userId);
	}

	public List<Users> getShopList() throws Exception{
		return myUserDao.getShopList();
	}

	public Users getUserByPhone(String phone) throws Exception{
		return myUserDao.getUserByPhone(phone);
	}

	public Integer getUserCount() throws Exception{
		return myUserDao.getUserCount();
	}

	public List<Users> getUserList() throws Exception{
		return myUserDao.getUserList();
	}

	public boolean updateUser(Users users) throws Exception{
		return myUserDao.updateUser(users);
	}

	public List<Users> getUsers(Integer communityId,String phone,String room,String userFloor)throws Exception{
		return myUserDao.getUsers(communityId,phone,room,userFloor);
	}

	public List<String> selectUserFloor(Integer communityId)throws Exception {
		return myUserDao.selectUserFloor(communityId);
	}

	public List<String> selectUserNuit(Integer communityId, String userFloor) throws Exception{
		return myUserDao.selectUserNuit(communityId,userFloor);
	}

	public List<String> selectUserRoom(Integer communityId, String userFloor, String userNuit)throws Exception {
		return myUserDao.selectUserRoom(communityId,userFloor,userNuit);
	}

	public List<Users> selectFloorUnitUser(Integer communityId, String userFloor, String userUnit, String room)  throws Exception{
		return myUserDao.selectFloorUnitUser(communityId,userFloor,userUnit,room);
	}

	public UserRegister getUserRegister(Integer communityId)  throws Exception{
		return myUserDao.getUserRegister(communityId);
	}

	public UserRegister getUserRegisterEndTime(Integer communityId, Integer endTimeInt) throws Exception{
		return myUserDao.getUserRegisterEndTime(communityId,endTimeInt);
	}

	public UserRegister getUserRegisterTime(Integer communityId,Integer startTimeInt, Integer endTimeInt) throws Exception{
		return myUserDao.getUserRegisterTime(communityId,startTimeInt,endTimeInt);
	}

	public List<UserRegister> getUserRegisterTimeList(Integer communityId,Integer startTimeInt, Integer endTimeInt) throws Exception{
		return myUserDao.getUserRegisterTimeList(communityId,startTimeInt,endTimeInt);
	}
	
	public List<Users> getUsersList(Integer communityId)throws Exception{
		return myUserDao.getUserList(communityId);
	}

	public List<UserRegister> getUserRegisterTimeGroup(Integer communityId, Integer startTimeInt, Integer endTimeInt,List<Integer> endList)throws Exception{
		return myUserDao.getUserRegisterTimeGroup(communityId,startTimeInt,endTimeInt,endList);
	}

	public List<TryOut> getListTryOut()throws Exception{
		return  myUserDao.getListTryOut();
	}

	public MoralStatistics getMoralStatistics(Integer communityId)throws Exception{
		return myUserDao.getMoralStatistics(communityId);
	}

	public List<Users> getMoralList(Integer communityId)throws Exception{
		return myUserDao.getMoralList(communityId);
	}

	public List<Users> getListUsers(Integer communityId,Integer startTime, Integer endTime)throws Exception{
		return myUserDao.getListUsers(communityId,startTime,endTime);
	}

	public List<Users> getListReg(Integer communityId) throws Exception{
		return myUserDao.getListReg(communityId);
	}
	
	public List<Users> getList(Integer communityId , String role) throws Exception{
		return myUserDao.getUserList(communityId, role);
	}

	public List<Users> registerList(Integer communityId)  throws Exception{
		return myUserDao.registerList(communityId);
	}

	public List<UserPartner>  getUserRegisterCommunity(String  emobId) throws Exception{
		return myUserDao.getUserRegisterCommunity(emobId);
	}

	@SuppressWarnings("rawtypes")
	public List<com.xj.stat.po.Users> getRegisterActiveUsersDay(Set set,Integer communityId) throws Exception{
		return myUserDao.getRegisterActiveUsersDay(set,communityId);
	}

	@SuppressWarnings("rawtypes")
	public List<com.xj.stat.po.Users> getRegisterActiveUsersDay2(Set set,Integer communityId) throws Exception{
		return myUserDao.getRegisterActiveUsersDay2(set,communityId);
	}

	public boolean upLiveness(Liveness liveness) throws Exception{
		return myUserDao.upLiveness(liveness);
	}

	public Liveness getLiveness(Integer  communityId) throws Exception{
		return myUserDao.getLiveness(communityId);
	}

	public Page<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales)  throws Exception{
		return myUserDao.selectNearbyCrazySalesShop(crazySales);
	}

	public void insertMessage(Messages ms)  throws Exception{
		 myUserDao.insertMessage(ms);
	}

	public List<Users> getListTryOutUser(Integer communityId) throws Exception{
		return myUserDao.getListTryOutUser(communityId);
	}

	public List<Users> getSetupUsersWithoutTest(Integer communityId, Integer startTime, Integer endTime) throws Exception {
		return myUserDao.getSetupUsersWithoutTest(communityId, startTime, endTime);
	}
	
	/**
	 * 分页获取指定小区用户
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @param query 
	 * @return
	 */
	public Page<Users> getUserByCommunityIdWithPage(Integer communityId,
			Integer pageNum, Integer pageSize, String query) {
		if(StringUtils.isNotBlank(query)){
			return myUserDao.getUserByCommunityIdWithPageAndCondition(communityId,pageNum,pageSize,query);
		}else{
			return myUserDao.getUserByCommunityIdWithPage(communityId,pageNum,pageSize);
		}
		
	}

	//推送消息
	public void pushMessage(Integer communityId, ExtNode extNode) {
		Map<String, Set<Users>> pushTarget = getPushTarget(communityId);
		
		ExecutorService executorService = Executors.newFixedThreadPool(pushTarget.entrySet().size());
		if(CollectionUtils.isNotEmpty(pushTarget.get("ios"))){
			executorService.execute(new PushNotification2Ios(extNode,pushTarget.get("ios")));
		}
		if(CollectionUtils.isNotEmpty(pushTarget.get("android"))){
			executorService.execute(new PushNotification2Andriod(extNode,pushTarget.get("android")));
		}
		if(CollectionUtils.isNotEmpty(pushTarget.get("mi"))){
			executorService.execute(new PushNotification2Mi(extNode,pushTarget.get("mi")));
		}
		executorService.shutdown();
	}
	
	
	/**
	 * 获取推送目标
	 * @param bulletin
	 * @return
	 */
	private Map<String, Set<Users>> getPushTarget(Integer communityId) {
		Map<String, Set<Users>> map = new HashMap<String, Set<Users>>();
		List<Users> ios = myUserDao.getIosUserEquipmentTokenByCommunity(communityId);
		List<Users> android = myUserDao.getUserEquipmentAliasByCommunity(communityId,"android");
		List<Users> mi = myUserDao.getUserEquipmentAliasByCommunity(communityId,"mi");
		
		if(CollectionUtils.isNotEmpty(ios)){
			map.put("ios", new HashSet<Users>(ios));
			ios.clear();
		}
		if(CollectionUtils.isNotEmpty(android)){
			map.put("android", new HashSet<Users>(android));
			ios.clear();
		}
		if(CollectionUtils.isNotEmpty(mi)){
			map.put("mi", new HashSet<Users>(mi));
			mi.clear();
		}
		return map;
	}


	public void pushMessageByIds(List<Integer> userId, ExtNode extNode) {
		
		Map<String, Set<Users>> map = groupUsersByEquipment( myUserDao.getUsersByIds(userId));
		
		ExecutorService executorService = Executors.newFixedThreadPool(map.entrySet().size());
		
		if(CollectionUtils.isNotEmpty(map.get("ios"))){
			executorService.execute(new PushNotification2Ios(extNode,map.get("ios")));
		}
		if(CollectionUtils.isNotEmpty(map.get("android"))){
			executorService.execute(new PushNotification2Andriod(extNode,map.get("android")));
		}
		if(CollectionUtils.isNotEmpty(map.get("mi"))){
			executorService.execute(new PushNotification2Mi(extNode,map.get("mi")));
		}
		executorService.shutdown();
	}

	private Map<String, Set<Users>> groupUsersByEquipment(List<Users> list) {
		Map<String, Set<Users>> map = new HashMap<String, Set<Users>>();
		for (Users users : list) {
			if("android".equals(users.getEquipment())){
				Set<Users> set = map.get("android");
				if(CollectionUtils.isEmpty(set)){
					Set<Users> android = new HashSet<Users>();
					map.put("android", android);
				}
				map.get("android").add(users);
			}
			if("mi".equals(users.getEquipment())){
				Set<Users> set = map.get("mi");
				if(CollectionUtils.isEmpty(set)){
					Set<Users> mi = new HashSet<Users>();
					map.put("mi", mi);
				}
				map.get("mi").add(users);
			}
			if("ios".equals(users.getEquipment())){
				Set<Users> set = map.get("ios");
				if(CollectionUtils.isEmpty(set)){
					Set<Users> ios = new HashSet<Users>();
					map.put("ios", ios);
				}
				map.get("ios").add(users);
			}
			
		}
		return map;
	}
}