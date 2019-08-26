package com.xj.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.bean.LifeCircle;
import com.xj.bean.Shops;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.MyUserDao;
import com.xj.dao.ShopsDao;
import com.xj.dao.TryOutDao;
import com.xj.httpclient.apidemo.EasemobIMUsers;
import com.xj.httpclient.vo.MyReturnKey;
import com.xj.stat.po.TryOut;
import com.xj.stat.utils.DateUtils;
import com.xj.utils.MD5Util;

@Service
public class TryOutService {
	
	@Autowired
	private MyUserDao myUserDao;

	@Autowired
	private TryOutDao tryOutDao; 
	
	@Autowired
	private CommunitiesDao communitiesDao;
	
	@Autowired
	private ShopsDao shopsDao;
	/**
	 * 添加水军
	 * @param users
	 * @param type 
	 * @param type 
	 * @return
	 * @throws Exception 
	 */
	public String addTryOut(Users users) throws Exception {
		
		String password = users.getPassword();
		String emobId = MD5Util.getMD5Str(users.getUsername());
		ObjectNode objectNode = EasemobIMUsers.addUser(emobId, MD5Util.getMD5Str("&bang#bang@" + password));
		if (objectNode == null || objectNode.get("statusCode") == null || !"200".equals(objectNode.get("statusCode").asText())) {
			return "添加出现错误此账号已在环信存在但由于不明原因在服务器端没能注册成功";
		}
		
		String salt = MD5Util.getStr();
		users.setClientId(MD5Util.getMD5Str(System.currentTimeMillis()+""));
		users.setCreateTime(DateUtils.getCurrentUnixTime());
		users.setSalt(salt);
		users.setPassword(MD5Util.getMD5Str(password + salt));
		users.setEmobId(emobId);
		users.setRole("owner");
		MyReturnKey key = new MyReturnKey();
		TryOut to = new TryOut();
		to.setEmobId(users.getEmobId());
		to.setCommunityId(users.getCommunityId());
		
		try {
			myUserDao.save(users,key );
			to.setUsersId(key.getId());
			to.setRemarks(users.getRemarks());
			tryOutDao.addTryOut(to); //添加一条水军数据
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加用户或水军失败！");
		}
		return  null;
	}

	
	
	/**
	 * 根据小区id获取水军列表
	 * @param communityId
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	public List<Users> getTryOuts(Integer communityId, Integer pageNum, Integer pageSize) {
		List<Users> users = tryOutDao.getTryOuts(communityId,pageNum,pageSize);
		return users;
	}

	/**
	 * 获取指定水军发的生活圈
	 * @param communityId
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<LifeCircle> getLifeCircles(String emobId,
			Integer pageNum, Integer pageSize) {
		List<LifeCircle> LifeCircles = tryOutDao.getLifeCircles(emobId, pageNum, pageSize);
		return LifeCircles;
	}



	/**
	 * 创建水军店铺
	 * @param shops
	 * @return
	 * @throws Exception 
	 */
	public String addTryOutShop(Shops shops){
		Users users = shops.getUser();
		String password = users.getPassword();
		String emobId = MD5Util.getMD5Str(users.getUsername());
		ObjectNode objectNode = EasemobIMUsers.addUser(emobId, MD5Util.getMD5Str("&bang#bang@" + password));
		if (objectNode == null || objectNode.get("statusCode") == null || !"200".equals(objectNode.get("statusCode").asText())) {
			return "添加出现错误此账号已在环信存在但由于不明原因在服务器端没能注册成功";
		}
		
		String salt = MD5Util.getStr();
		users.setClientId(MD5Util.getMD5Str(System.currentTimeMillis()+""));
		users.setCreateTime(DateUtils.getCurrentUnixTime());
		users.setRegisterTime(DateUtils.getCurrentUnixTime());
		users.setSalt(salt);
		users.setPassword(MD5Util.getMD5Str(password + salt));
		users.setEmobId(emobId);
		users.setRole("shop");
		users.setCommunityId(shops.getCommunityId());
		MyReturnKey key = new MyReturnKey();
		TryOut to = new TryOut();
		to.setEmobId(users.getEmobId());
		to.setCommunityId(users.getCommunityId());
		try {
			myUserDao.save(users, key);
			to.setUsersId(key.getId());
			to.setRemarks(users.getRemarks());
			tryOutDao.addTryOut(to); //添加一条水军数据
			
			shops.setAuthCode( MD5Util.getFcoude());
			Map<String, Float> map = communitiesDao.getCommunityLocation(shops.getCommunityId());
			shops.setEmobId(emobId);
			shops.setLongitude(map.get("longitude"));
			shops.setLatitude(map.get("latitude"));
			shops.setCreateTime(System.currentTimeMillis()/1000l);
			shopsDao.save(shops,new MyReturnKey());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加用户和店铺时出现了错误！");
		}
		return null;
	}



	/**
	 * 根据小区id获取水军总数
	 * @param communityId
	 * @return
	 */
	public Integer getTryOutNum(Integer communityId) {
		return tryOutDao.getTryOutNum(communityId);
	}


	/**
	 * 获取用户生活圈的数量
	 * @param emobId
	 * @return
	 */
	public int getLifeCirclesSum(String emobId) {
		return tryOutDao.getLifeCirclesSum(emobId);
	}



	public Users getNavyByEmobId(String emobId) {
		return tryOutDao.getNavyByEmobId(emobId);
	}

	/**
	 * 跟新用户信息
	 * @param users
	 * @return
	 * @throws Exception 
	 */
	public Boolean editNavy(Users users){
		try {
			UsersApp usersApp = myUserDao.getUserByEmobId(users.getEmobId());
			EasemobIMUsers.updateUserPass(users.getEmobId(), MD5Util.getMD5Str("&bang#bang@"+users.getPassword()));
			users.setPassword(MD5Util.getMD5Str(users.getPassword()+usersApp.getSalt()));
			users.setEmobId(null);
			return myUserDao.update(users);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	/**
	 * 通过手机号和用户名查找用户
	 * @param query
	 * @return
	 */
	public List<Users> searchNavy(String query) {
		
		return tryOutDao.searchNavy(query);
	}
	
}
