package com.xj.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.Orders;
import com.xj.bean.TryOut;
import com.xj.bean.Users;
import com.xj.bean.XjIp;
import com.xj.dao.MyUserDao2;
import com.xj.stat.po.UserVo;
@Repository("myUserDao2")
public class MyUserDaoImpl2 extends MyUserDaoImpl implements MyUserDao2 {

	@Override
	public int updateUserBonusCoin(String emobIdUser, Integer bonusCount) {
		String sql = "UPDATE users u SET u.bonuscoin_count  = u.bonuscoin_count+? where u.emob_id = ?";
		List<Object> params = new  ArrayList<Object>();
		params.add(bonusCount);
		params.add(emobIdUser);
		int updateData = this.updateData(sql, params, null);
		return updateData;
	}
	
	@org.junit.Test
	public  void Test() throws Exception{
		ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
		MyUserDaoImpl2 dao = (MyUserDaoImpl2) ac.getBean("myUserDao2");
		List<Orders> orderss = dao.getAllEndedOrdersInTime(3, "2", 1435680000, 1437753599);
		
		for (Orders orders : orderss) {
			System.out.println(orders.getOrderId());
		}
	
		System.out.println(orderss);
		
	}




	@Override
	public List<TryOut> getAllTryOut() throws Exception {
		List<TryOut> listTryOut = this.getListTryOut();
		return listTryOut; 
	}




	@Override
	public List<Orders> getAllEndedOrdersInTime(Integer communityId,
			String shopType, Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT * FROM orders o LEFT JOIN shops s ON s.emob_id=o.emob_id_shop " +
				" WHERE o.community_id=?  AND o.`status`='ended' AND s.sort=? AND " +
				" o.end_time BETWEEN ? AND ? AND o.emob_id_user NOT in (SELECT t.emob_id from try_out t)";
		Object[] params = {communityId,shopType,startTime,endTime};
		List<Orders> list = this.getList(sql, params, Orders.class);
		return list;
	}




	@Override
	public List<Users> findUsersByCommunityIdWithNull(int communityid) throws Exception {
		String sql = "SELECT * from users u where u.community_id = ? and u.create_time>1433088000";
		Object[] params = {communityid};
		List<Users> list = this.getList(sql, params, Users.class);
		return list;
	}




	/**
	 * 获取针对小区的分成率
	 * @throws Exception 
	 */
	@Override
	public Integer getCommissionRate(Integer communityId) throws Exception {
		String sql = "SELECT p.imcome FROM `imcome_percent` p WHERE p.community_id=?";
		String value = this.getValue(sql, String.class, communityId);
		return new Integer(value);
	}

	@Override
	public List<XjIp> getuserIP() throws Exception {
		String sql = "SELECT  * FROM `xj_ip` p";
		List<XjIp> list = this.getList(sql, new Object[]{}, XjIp.class);
		return list;
	}
	
	@Override
	public UserVo getUserVoByEmobId(final String key) throws Exception {
		String sql = " SELECT u.nickname, u.username,u.equipment_version,u.emob_id FROM users u WHERE u.emob_id=? limit 1 ";
		
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<UserVo>(){

			@Override
			public UserVo extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				UserVo user = null;
				if(rs.next()){
					user= new UserVo();
					if(StringUtils.isBlank(rs.getString("username"))){
						user.setUsername(key);
					}else{
						user.setUsername(rs.getString("username"));
					}
					user.setEquipmentVersion(rs.getString("equipment_version"));
					user.setNickname(rs.getString("nickname"));
				}
				return user;
			}
		},key);
	}




	@Override
	public List<Users> findUsersByCommunityIdWithNull2(int communityid)
			throws Exception {
		return null;
	}

}
