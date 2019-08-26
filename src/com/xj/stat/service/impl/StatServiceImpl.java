package com.xj.stat.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.bean.XjIp;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.MyUserDao2;
import com.xj.dao.TryOutDao;
import com.xj.service.UserService;
import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.bean.resource.TopUser;
import com.xj.stat.dao.UserDao;
import com.xj.stat.mongo.MongoService;
import com.xj.stat.po.StatisticsModule;
import com.xj.stat.po.StatisticsUser;
import com.xj.stat.po.TryOut;
import com.xj.stat.po.Users;
import com.xj.stat.service.StatService;
import com.xj.stat.service.StatisticsModuleService;
import com.xj.stat.service.StatisticsUserService;
import com.xj.utils.DateUtils;
import com.xj.utils.MongoUtils;

/**
 * @author lence
 * @date 2015年7月9日上午1:11:55
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatServiceImpl implements StatService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TryOutDao tryOutDao;

	@Autowired
	private UserService userService;

	@Autowired
	private MyUserDao2 myUserDao2;

	@Autowired
	private StatisticsUserService statisticsUserService;

	@Autowired
	private StatisticsModuleService statisticsModuleService;

	@Autowired
	private MongoService mongoService;
	
	@Autowired
	private  CommunitiesDao  communitiesDao;

	public List<String> statUsers2(String startday, String endDay, Integer communityId) throws Exception {
		/**
		 * 取并集，set中就是安装过app 并今天操作过的用户数据 安装用户活跃数
		 */
		Set<String> set = new HashSet<String>();
		set.addAll(getModulesClickUser(startday, endDay, communityId));// 查询所有点击过模块的用户
		set.addAll(getInstallUsersActiveDay(startday, endDay, communityId));// 查询安装用户今日活跃
		
		List<Users> list2 = userService.getRegisterActiveUsersDay2(set, communityId);
		List<String> list = new ArrayList<String>(list2.size());
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i).getEmobId());
			
			StatisticsUser statisticsUser = new StatisticsUser();
			statisticsUser.setEmobId(list2.get(i).getEmobId());
			statisticsUser.setStatisticsTime(Integer.parseInt(startday));
			statisticsUser.setCommunityId(communityId);
			
			statisticsUserService.insertStatisticsUser(statisticsUser);
		}
		
		return list;
	}

	public List<Integer> statUsers(String startday, String endDay, Integer communityId) throws Exception {
		//取并集，set中就是安装过app 并今天操作过的用户数据 安装用户活跃数
		Set<String> installActiveUserSet = new HashSet<String>();
		installActiveUserSet.addAll(getModulesClickUser(startday, endDay, communityId));// 查询所有点击过模块的用户
		installActiveUserSet.addAll(getInstallUsersActiveDay(startday, endDay, communityId));// 查询安装用户活跃
		
		if(CollectionUtils.isNotEmpty(installActiveUserSet)){
			List<String> installActiveUserList = userDao.getInstallActiveUsers(installActiveUserSet, communityId, endDay);//获取活跃安装用户数
			installActiveUserSet.clear();
			installActiveUserSet.addAll(installActiveUserList);
		}
		
		Set<String> registerActiveUserSet = new HashSet<String>();
		Set<String> registerUserSet = new HashSet<String>();// 所有已经注册用户
		if(CollectionUtils.isNotEmpty(installActiveUserSet)){
			List<String> registerActiveUserList = userDao.getRegisterActiveUsers(installActiveUserSet, communityId,endDay);//获取安装用户中注册活跃用户
			registerActiveUserSet.addAll(registerActiveUserList);
			registerUserSet.addAll(registerActiveUserList);
		}
		
		// 水军集合
		 Set<String> shuijunSet = userDao.getAllTest(communityId);
		
		//去除总安装用户中的水军
		int shuijunCount = 0;
		for (String emobId : installActiveUserSet) {
			if (shuijunSet.contains(emobId)) {
				shuijunCount++;
			}
		}
		
		//去除注册用户中的水军
		int registerShuijunCount = 0;
		for (String emobId : registerUserSet) {
			if (shuijunSet.contains(emobId)) {
				registerShuijunCount++;
			}
		}
		
		List<Integer> result = new ArrayList<Integer>(6);
		result.add(shuijunCount);
		result.add(installActiveUserSet.size()-shuijunCount);
		result.add(registerShuijunCount);
		result.add(registerUserSet.size()-registerShuijunCount);
		result.add(userDao.findAllInstallUserIdByCommunityAndTime(endDay, communityId));//获取截止到给定时间的总安装数
		result.add(userDao.findAllRegisServiceByCommunityAndTime(endDay, communityId));
		
		return result;
	}

	/**
	 * 获取已安装用户中，当天的活跃用户数量
	 * 
	 * @param day
	 * @return 返回的是一个带有全部用户id的集合
	 * @throws Exception
	 */
	private Set<String> getInstallUsersActiveDay(String startday, String endday, Integer communityId) throws Exception {
		Long end = (new Long(endday)) - 1l;
		String url = "http://115.28.73.37:9090/api/V1/communities/" + communityId + "/active_user_count/statistics/" + startday + "/" + end;
		String result = com.xj.stat.utils.HttpUtil.httpUrl(url);
		DBObject dbobject = (DBObject) JSON.parse(result);
		Set<String> clickUsers = new HashSet<String>();
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> info = (ArrayList) dbobject.get("info");
			List<Users> users = userDao.findUsersByCommunityId(communityId);
			Map<String, String> map = new HashMap<String, String>();
			for (Users u : users) {
				map.put(u.getEmobId(), "yes");
			}

			for (DBObject click : info) {
				DBObject user = (DBObject) click.get("userClick");
				String emobId = (String) user.get("emobId");
				if ("yes".equals(map.get(emobId))) {
					clickUsers.add(emobId);
				}
			}
		}
		return clickUsers;
	}

	/**
	 * 获取所有点击过模块的用户数，不论点击多少次
	 * 
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public Set<String> getModulesClickUser(String startday, String endday, Integer communityId) throws Exception {
		Long end = (new Long(endday)) - 1l;
		String url = "http://115.28.73.37:9090/api/V1/communities/" + communityId + "/modules/statistics/" + startday + "/" + end;
		String result = com.xj.stat.utils.HttpUtil.httpUrl(url);
		DBObject dbobject = (DBObject) JSON.parse(result);
		Set<String> clickUsers = new HashSet<String>();
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> info = (ArrayList) dbobject.get("info");
			List<Users> users = userDao.findUsersByCommunityId(communityId);
			Map<String, String> map = new HashMap<String, String>();
			for (Users u : users) {
				map.put(u.getEmobId(), "yes");
			}
			for (DBObject click : info) {
				if (!"".equals(click.get("info"))) {
					ArrayList<DBObject> userClickInfos = (ArrayList) click.get("info");
					for (DBObject userClickInfo : userClickInfos) {
						String serviceId = (java.lang.String) userClickInfo.get("serviceId");
						String serviceName = (java.lang.String) userClickInfo.get("serviceName");
						if (serviceId.equals("0") && !serviceName.equals("通知公告")) {
							continue;
						}
						
						ArrayList<DBObject> userClicks = (ArrayList<DBObject>) userClickInfo.get("userClick");
						for (DBObject dbObject2 : userClicks) {
							String emobId = (String) dbObject2.get("emobId");
							if ("yes".equals(map.get(emobId))) {
								clickUsers.add(emobId);
							}
						}
					}
				}
			}
		}
		return clickUsers;
	}
	
	/**
	 * 获取所有点击过模块的用户数，不论点击多少次
	 * 
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public Map<String,ClickMobule> getModulesClickModul(Integer startday, Integer endday, Integer communityId) throws Exception {
		Long end = (new Long(endday)) - 1l;
		String url = "http://115.28.73.37:9090/api/V1/communities/" + communityId + "/modules/statistics/" + startday + "/" + end;
		String result = com.xj.stat.utils.HttpUtil.httpUrl(url);
		
		DBObject dbobject = (DBObject) JSON.parse(result);
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> info = (ArrayList) dbobject.get("info");
			List<Users> users = userDao.findUsersByCommunityId(communityId);
			Map<String, String> map = new HashMap<String, String>();
			for (Users u : users) {
				map.put(u.getEmobId(), "yes");
			}
			for (DBObject click : info) {
				if (!"".equals(click.get("info"))) {
					ArrayList<DBObject> userClickInfos = (ArrayList) click.get("info");
					for (DBObject userClickInfo : userClickInfos) {
						String serviceId = (java.lang.String) userClickInfo.get("serviceId");
						String serviceName = (java.lang.String) userClickInfo.get("serviceName");
						if (serviceId.equals("0") && !serviceName.equals("通知公告")) {
							continue;
						}
						
						ArrayList<DBObject> userClicks = (ArrayList<DBObject>) userClickInfo.get("userClick");
						Map<String,Integer> mapCount = new HashMap<String, Integer>();
						for (DBObject dbObject2 : userClicks) {
							Integer count = (Integer) dbObject2.get("click");
							if(count==null){
								count=0;
							}
							String emobId = (String) dbObject2.get("emobId");
							if ("yes".equals(map.get(emobId))) {
								if (mapCount.containsKey(emobId)) {
									mapCount.put(emobId,count + mapCount.get(emobId));
								} else {
									mapCount.put(emobId, count);
								}
							}
						}
						
						StatisticsModule statisticsModule = new StatisticsModule();
						for (Map.Entry<String, Integer> entry : mapCount.entrySet()) {
							statisticsModule.setClick( entry.getValue());
							statisticsModule.setEmobId(entry.getKey());
							statisticsModule.setStatisticsTime(startday);
							statisticsModule.setCommunityId(communityId);
							statisticsModule.setType(Integer.parseInt(serviceId));
							
							statisticsModuleService.insertStatisticsModule(statisticsModule);
						}
					}
				}
				
			}
		}
		return null;
	}

	/**
	 * 获取注册活跃用户
	 * 
	 * @return
	 */
	public Set<Users> getRegisterActiveUsersDay(Set<String> set, Integer communityId) {
		return new HashSet<Users>(userDao.getUsersByArray(set.toArray(), communityId));
	}

	public Map<String, Integer> getData(int communityid, int modules, int start, int end) {
		return mongoService.getCircumDetails(communityid, modules, start, end);
	}

	public Map<String, Integer> getAllModulesCount(int communityid, int start, int end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String str = MongoUtils.getModulesInTime(3, start, end);
		DBObject dbobject = (DBObject) JSON.parse(str);
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> infos1 = (ArrayList<DBObject>) dbobject.get("info");
			for (DBObject infos : infos1) {
				if (!"".equals("info")) {
					ArrayList<DBObject> infos2 = (ArrayList<DBObject>) infos.get("info");
					if (infos2 != null && infos2.size() > 0) {
						for (DBObject info : infos2) {
							String serviceId = (String) info.get("serviceId");
							String serviceName = (String) info.get("serviceName");
							if ("0".equals(serviceId) && !"通知公告".equals(serviceName)) {
								continue;
							}
							if ("".equals(serviceName)) {
								continue;
							}
							
							if (!"".equals(info.get("userClick"))) {
								ArrayList<DBObject> clicks = (ArrayList<DBObject>) info.get("userClick");
								for (DBObject click : clicks) {
									Integer count = (Integer) click.get("click");
									String emobId = (String) click.get("emobId");
									if (emobId != null && !"".equals(emobId) && emobId.length() == 32) {
										if (map.containsKey(emobId)) {
											map.put(emobId, count + map.get(emobId));
										} else {
											map.put(emobId, count);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return map;
	}

	public Integer getCount(Map<String, Integer> map, int communityid) {
		/**
		 * 获取查到数据的 本小区用户
		 */
		List<String> userList = userDao.getUsersByEmobIdsWithVisitor(map.keySet().toArray());

		/**
		 * 获取数据中的水军
		 */
		List<String> tryoutList = tryOutDao.getTryOutByEmobIds(userList.toArray());
		for (String string : tryoutList) {
			userList.remove(string);
		}

		Map<String, Integer> map2 = new HashMap<String, Integer>();
		for (String string : userList) {
			map2.put(string, map.get(string));
		}

		int count = 0;
		Set<String> keySet2 = map2.keySet();
		for (String string : keySet2) {
			count = count + map2.get(string);
		}
		
		return count;
	}

	@Override
	public List<String> getuserIP() {
		try {
			List<XjIp> getuserIP = myUserDao2.getuserIP();
			if (getuserIP != null && getuserIP.size() > 0) {
				List<String> ips = new ArrayList<String>();
				for (XjIp xjIp : getuserIP) {
					ips.add(xjIp.getXjIp());
				}
				return ips;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TopUser statisticsUserNum(Set<String> set, BeanUtil beanUtil) throws Exception {
		List<String> emobIdList = statisticsUserService.selectUserEmob(beanUtil);
		for (int i = 0; i < emobIdList.size(); i++) {
			set.add(emobIdList.get(i));
		}

		List<Users> lis2 = userService.getRegisterActiveUsersDay2(set, beanUtil.getCommunityId());
		Set<String> userList2 = new HashSet<String>();
		for (int i = 0; i < lis2.size(); i++) {
			userList2.add(lis2.get(i).getEmobId());
		}
		set = userList2;

		/**
		 * 注册用户今日活跃用户，返回的是Users，只需要emobid
		 */
		// Set<Users> userList = getRegisterActiveUsersDay(set,communityId);
		List<Users> lis = userService.getRegisterActiveUsersDay(set, beanUtil.getCommunityId());
		Set<Users> userList = new HashSet<Users>();
		for (int i = 0; i < lis.size(); i++) {
			userList.add(lis.get(i));
		}

		// 水军集合
		Map<String, String> emobids = new HashMap<String, String>();
		List<TryOut> allTryOut = tryOutDao.getAllTryOut();
		for (TryOut tryOut : allTryOut) {
			emobids.put(tryOut.getEmobId(), "yes");
		}

		// 所有已经注册用户
		Set<String> setRegist = new HashSet<String>();
		for (Users users : userList) {
			setRegist.add(users.getEmobId());
		}
		
		//去除注册用户中的水军
		int count2 = 0;
		for (String emobId : setRegist) {
			if (!"yes".equals(emobids.get(emobId))) {
				count2++;
			}
		}

		/**
		 * 去除总安装用户中的水军
		 */
		int total = 0;
		int thisUser = 0;
		for (String emobId : set) {
			if (!"yes".equals(emobids.get(emobId))) {
				total++;
			} else {
				thisUser++;
			}
		}
		
		TopUser topUser = new TopUser();
		topUser.setThisUser(thisUser + "");
		topUser.setRegister(count2 + "");
		topUser.setTotal(total + "");
		topUser.setRegisterLiveness(liveness(count2, userDao.findAllRegisServiceByCommunityAndTime(beanUtil.getEndTime() + "", beanUtil.getCommunityId())));
		topUser.setTotalLiveness(liveness(total, userDao.findAllInstallUserIdByCommunityAndTime(beanUtil.getEndTime() + "", beanUtil.getCommunityId())));//获取截止到目前时间的总安装数

		return topUser;
	}

	public String liveness(Integer num, Integer sum) {
		if (sum == 0) {
			return "0";
		}
		return new DecimalFormat("0.00").format((num / sum) * 100);// 返回的是String类型的
	}

	@Override
	public Set<String> thisDay(Integer communityId) throws Exception {
		String[] times = getTodayTime();
		
		/**
		 * 取并集，set中就是安装过app 并今天操作过的用户数据 安装用户活跃数
		 */
		Set<String> set = new HashSet<String>();
		set.addAll(getInstallUsersActiveDay(times[0], times[1], communityId));// 查询安装用户今日活跃
		set.addAll(getModulesClickUser(times[0], times[1], communityId));// 查询所有点击过模块的用户
		
		return set;
	}

	private String[] getTodayTime() throws Exception {
		List<String> times = DateUtils.get24Time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		return new String[] {times.get(0).split("#")[1], times.get(times.size() - 1).split("#")[2]};
	}

	@Override
	public List<String> thisDay2(Integer communityId) throws Exception {
		String[] times = getTodayTime();
		
		Set<String> set = new HashSet<String>();
		set.addAll(getModulesClickUser(times[0], times[1], communityId));// 查询所有点击过模块的用户
		set.addAll(getInstallUsersActiveDay(times[0], times[1], communityId));// 查询安装用户今日活跃
		
		List<String> list = new ArrayList<String>(set.size());
		for (String string : set) {
			list.add(string);
		}
		
		return list;
	}

	@Override
	public void getClickMobule(Integer startday,Integer endDay,Integer communityId) throws Exception {
		getModulesClickModul(startday, endDay,communityId);
	}

	@Override
	public List<ClickMobule>  statisticsModuleNum(BeanUtil beanUtil) {
		return statisticsModuleService.statisticsModuleNum(beanUtil);
	}

	@Override
	public Map<String, Integer> getCities() {
		return communitiesDao.getCities();
	}

}
