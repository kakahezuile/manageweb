package com.xj.stat.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.dao.TryOutDao;
import com.xj.stat.dao.UserDao;
import com.xj.stat.po.TryOut;
import com.xj.stat.po.Users;
import com.xj.stat.service.StatService;
import com.xj.utils.MongoUtils;

/**
 * @author lence
 * @date 2015年7月9日下午5:44:50
 */
@Component
public class MongoService {

	@Autowired
	private StatService statService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TryOutDao tryOutDao;

	/**
	 * 按社区 模块 时间段 统计
	 * 
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getCircumDetails(int communityid, int modules, Integer start, Integer end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String detailsDaily = MongoUtils.getEventsDetailsDailyInTime(communityid, modules, start, end);
		DBObject dbobject = (DBObject) JSON.parse(detailsDaily);
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> info = (ArrayList<DBObject>) dbobject.get("info");
			for (DBObject dbObject : info) {
				if (!"".equals(dbObject.get("userClick"))) {
					ArrayList<DBObject> userclick = (ArrayList<DBObject>) dbObject.get("userClick");
					if (userclick != null && userclick.size() > 0) {
						for (DBObject dbObject2 : userclick) {
							String emobId = (String) dbObject2.get("emobId");
							Integer clickNum = (Integer) dbObject2.get("click");
							if (emobId != null && !"".equals(emobId) && emobId.length() == 32) {
								if (map.containsKey(emobId)) {
									map.put(emobId, clickNum + map.get(emobId));
								} else {
									map.put(emobId, clickNum);
								}
							}
						}
					}
				}
			}
		}
		
		/**
		 * 去小区
		 */
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		List<Users> users = userDao.findUsersByCommunityIdWithNull(communityid);
		for (Users user : users) {
			String emobId = user.getEmobId();
			if (map.containsKey(emobId)) {
				map2.put(emobId, map.get(emobId));
			}
		}
		
		/**
		 * 去水军
		 */
		List<TryOut> allTryOut = tryOutDao.getAllTryOut();
		Map<String, Integer> map3 = new HashMap<String, Integer>();
		for (TryOut tryOut : allTryOut) {
			String emobId = tryOut.getEmobId();
			if (map.containsKey(tryOut.getEmobId())) {
				map3.put(emobId, map.get(emobId));
			}
		}
		
		Set<String> keySet2 = map2.keySet();
		Map<String, Integer> map4 = new HashMap<String, Integer>();
		for (String key : keySet2) {
			if (!map3.containsKey(key)) {
				map4.put(key, map2.get(key));
			}
		}
		
		int sumClick = 0;
		if (map3 != null && map3.size() > 0) {
			Set<String> keySet = map3.keySet();
			for (String key : keySet) {
				Integer num = map3.get(key);
				sumClick = sumClick + num;
			}
		}
		
		int click = 0;
		if (map4 != null && map4.size() > 0) {
			Set<String> keySet = map4.keySet();
			for (String key : keySet) {
				Integer num = map4.get(key);
				click = click + num;
			}
		}
		
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("commonUser", map4.size());
		data.put("common", click);
		data.put("test", sumClick);
		data.put("testUser", map3.size());
		data.put("total", statService.getCount(statService.getAllModulesCount(3, start, end), 3));// 计算模块占比，统计所有模块点击人数
		
		return data;
	}
	/**
	 * 按社区 模块 时间段 统计
	 * 
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getCommunityModuleStatics(int communityid, int modules, Integer start, Integer end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String detailsDaily = MongoUtils.getEventsDetailsDailyInTime(communityid, modules, start, end);
		DBObject dbobject = (DBObject) JSON.parse(detailsDaily);
		if (!"".equals(dbobject.get("info"))) {
			ArrayList<DBObject> info = (ArrayList<DBObject>) dbobject.get("info");
			for (DBObject dbObject : info) {
				if (!"".equals(dbObject.get("userClick"))) {
					ArrayList<DBObject> userclick = (ArrayList<DBObject>) dbObject.get("userClick");
					if (userclick != null && userclick.size() > 0) {
						for (DBObject dbObject2 : userclick) {
							String emobId = (String) dbObject2.get("emobId");
							Integer clickNum = (Integer) dbObject2.get("click");
							if (emobId != null && !"".equals(emobId) && emobId.length() == 32) {
								if (map.containsKey(emobId)) {
									map.put(emobId, clickNum + map.get(emobId));
								} else {
									map.put(emobId, clickNum);
								}
							}
						}
					}
				}
			}
		}
		
		/**
		 * 去小区
		 */
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		List<Users> users = userDao.findUsersByCommunityIdWithNull(communityid);
		for (Users user : users) {
			String emobId = user.getEmobId();
			if (map.containsKey(emobId)) {
				map2.put(emobId, map.get(emobId));
			}
		}
		
		/**
		 * 去水军
		 */
		List<TryOut> allTryOut = tryOutDao.getAllTryOut();
		Map<String, Integer> map3 = new HashMap<String, Integer>();
		for (TryOut tryOut : allTryOut) {
			String emobId = tryOut.getEmobId();
			if (map.containsKey(tryOut.getEmobId())) {
				map3.put(emobId, map.get(emobId));
			}
		}
		
		Set<String> keySet2 = map2.keySet();
		Map<String, Integer> map4 = new HashMap<String, Integer>();
		for (String key : keySet2) {
			if (!map3.containsKey(key)) {
				map4.put(key, map2.get(key));
			}
		}
		
		int sumClick = 0;
		if (map3 != null && map3.size() > 0) {
			Set<String> keySet = map3.keySet();
			for (String key : keySet) {
				Integer num = map3.get(key);
				sumClick = sumClick + num;
			}
		}
		
		int click = 0;
		if (map4 != null && map4.size() > 0) {
			Set<String> keySet = map4.keySet();
			for (String key : keySet) {
				Integer num = map4.get(key);
				click = click + num;
			}
		}
		
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("commonUser", map4.size());
		data.put("common", click);
		data.put("test", sumClick);
		data.put("testUser", map3.size());
		
		return data;
	}
	
	
}

