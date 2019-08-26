package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.Messages;
import com.xj.bean.MoralStatistics;
import com.xj.bean.Page;
import com.xj.bean.TryOut;
import com.xj.bean.UserPartner;
import com.xj.bean.UserPercent;
import com.xj.bean.UserRegister;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.Liveness;
import com.xj.dao.MyUserDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;

@Repository("myUserDao")
public class MyUserDaoImpl extends MyBaseDaoImpl implements MyUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Users getNameByUser(String userName) throws Exception {
		List<Users> list = this.getList("SELECT * FROM users WHERE username = ?", new String[] { userName }, Users.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer insert(Users userBean) throws Exception {
		MyReturnKey key = new MyReturnKey();
		if (userBean.getCharacterValues() == null) {
			userBean.setCharacterValues(0);
		}
		this.save(userBean, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(Users userBean) throws Exception {
		Object[] resultObject = DaoUtils.reflect(userBean);
		if (resultObject == null || resultObject[1] == null || ((List<Object>) resultObject[1]).size() == 0) {
			return false;
		}
		
		String sql = "UPDATE users SET ";
		sql += (String) resultObject[0];
		sql += " WHERE user_id = ?";
		
		List<Object> params = (List<Object>) resultObject[1];
		params.add(userBean.getUserId());
		
		return this.updateData(sql, params, null) > 0;
	}

	@Override
	public List<Users> getUserList(Integer communityId) throws Exception {
		return this.getList("SELECT * FROM users WHERE community_id = ?", new Integer[] { communityId }, Users.class);
	}
	
	@Override
	public List<Users> getUserList(Integer communityId, String role) throws Exception {
		
		return this.jdbcTemplate.query("SELECT username,nickname,equipment,equipment_version,emob_id,device_token FROM users WHERE community_id =? AND role=? AND test =0", new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users u = new Users();
					u.setNickname(rs.getString("nickname"));
					u.setUsername(rs.getString("username"));
					u.setEquipment(rs.getString("equipment"));
					u.setEquipmentVersion(rs.getString("equipment_version"));
					u.setEmobId(rs.getString("emob_id"));
					u.setDeviceToken(rs.getString("device_token"));
					users.add(u);
				}
				return users;
			}
		},communityId, role);
	}
	
	@Override
	public List<String> getEmobIdList(Integer communityId,String role) throws Exception{
		String sql = "SELECT emob_id FROM users WHERE community_id = ? AND role=?";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<String>>(){

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				 List<String> resultList = new ArrayList<String>();
				while(rs.next()){
					resultList.add(rs.getString("emob_id"));
				}
				return resultList;
			}
		},communityId,role);
	}

	@Override
	public UsersApp getUserByEmobId(String emobId) throws Exception {
		String sql = " SELECT u.user_id , u.bonuscoin_count , u.equipment , u.username , u.emob_id , u.password , u.nickname , u.phone , u.gender , u.age , u.avatar , u.client_id , u.character_values "
				+ " , u.idcard , u.idnumber , u.status , u.room , u.create_time , u.salt , u.role , u.community_id , u.signature , u.user_floor , u.user_unit , c.community_name"
				+ " FROM users u LEFT JOIN communities c ON u.community_id = c.community_id WHERE emob_id = ?";
		
		List<UsersApp> list = this.getList(sql, new String[] { emobId }, UsersApp.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}


	@Override
	public List<Users> getShopList() throws Exception {
		return this.getList("SELECT * FROM users WHERE role = 'shop'", null, Users.class);
	}

	@Override
	public Users getUserByPhone(String phone) throws Exception {
		List<Users> list = this.getList("SELECT * FROM users WHERE username = ? or phone = ? limit 1", new Object[] { phone, phone }, Users.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer getUserCount() throws Exception {
		return this.getValue("SELECT count(*) FROM users WHERE role = 'owner'", Number.class, new Object[] {}).intValue();
	}

	@Override
	public List<Users> getUserList() throws Exception {
		return this.getList("SELECT * FROM users WHERE role = 'owner'", new Object[] {}, Users.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateUser(Users users) throws Exception {
		Object[] resultObject = DaoUtils.reflect(users);
		if (resultObject == null || resultObject[1] == null || ((List<Object>) resultObject[1]).size() == 0) {
			return false;
		}
		
		String sql = "UPDATE users SET ";
		sql += (String) resultObject[0];
		sql += " WHERE user_id = ?";
		
		List<Object> params = (List<Object>) resultObject[1];
		params.add(users.getUserId());
		
		return this.updateData(sql, params, null) > 0;
	}

	@Override
	public List<Users> getUsers(Integer communityId, String phone, String room, String userFloor) throws Exception {
		String sql = "SELECT * FROM users WHERE role = 'owner' AND community_id=? AND (phone=? or username=? or room=? )";
		return this.getList(sql, new Object[] { communityId, phone, phone, phone }, Users.class);
	}

	@Override
	public List<String> selectUserFloor(Integer communityId) throws Exception {
		List<String> list = new ArrayList<String>();

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement("SELECT u.user_floor FROM users u WHERE u.role = 'owner' AND u.community_id=? group by u.user_floor");
			preparedStatement.setInt(1, communityId);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("user_floor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> selectUserNuit(Integer communityId, String userFloor) throws Exception {
		List<String> list = new ArrayList<String>();

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement("SELECT u.user_unit FROM users u WHERE u.role = 'owner' AND u.community_id=? AND u.user_floor=? group by u.user_unit");
			preparedStatement.setInt(1, communityId);
			preparedStatement.setString(2, userFloor);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("user_unit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> selectUserRoom(Integer communityId, String userFloor, String userNuit) throws Exception {
		List<String> list = new ArrayList<String>();

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement("SELECT u.room FROM users u WHERE u.role = 'owner' AND u.community_id=? AND u.user_floor=? AND u.user_unit=? group by u.room");
			preparedStatement.setInt(1, communityId);
			preparedStatement.setString(2, userFloor);
			preparedStatement.setString(3, userNuit);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("room"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public List<Users> selectFloorUnitUser(Integer communityId, String userFloor, String userUnit, String room) throws Exception {
		String sql = "SELECT * FROM users WHERE role = 'owner' AND community_id=? ";
		if (userFloor != null && !userFloor.equals("") && !userFloor.equals("undefined")) {
			sql += " AND user_floor='" + userFloor + "'";
		}
		if (userUnit != null && !userUnit.equals("") && !userUnit.equals("undefined")) {
			sql += " AND user_unit='" + userUnit + "'";
		}
		if (room != null && !room.equals("") && !userUnit.equals("undefined")) {
			sql += " AND room=" + room;
		}
		
		return this.getList(sql, new Object[] { communityId }, Users.class);
	}

	@Override
	public UserRegister getUserRegister(Integer communityId) throws Exception {
		String sql = "SELECT u.create_time,COUNT(u.user_id) AS installs_num,"
				+ "COUNT(case when  t.id IS  NULL  AND u.username IS NOT NULL then u.user_id else NULL end) AS register_num,"
				+ "COUNT(CASE WHEN t.id IS NOT NULL AND u.username IS NOT NULL THEN u.user_id ELSE NULL END) AS test_num "
				+ " FROM users u LEFT JOIN try_out t ON u.user_id = t.users_id WHERE u.community_id=? AND u.test =0 AND u.role='owner' ";

		List<UserRegister> list = this.getList(sql, new Object[] { communityId }, UserRegister.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public UserRegister getUserRegisterTime(Integer communityId, Integer sta, Integer end) throws Exception {
		String sql = "SELECT u.create_time,COUNT(u.user_id) AS installs_num,"
				+ "COUNT(CASE WHEN t.id IS NULL AND u.username IS NOT NULL then u.user_id else NULL end) AS register_num,"
				+ "COUNT(CASE WHEN t.id IS NOT NULL AND u.username IS NOT NULL THEN u.user_id ELSE NULL END ) AS test_num"
				+ " FROM users u LEFT JOIN try_out t ON u.emob_id = t.emob_id  WHERE "
				+ " u.community_id=? AND u.create_time>? AND u.create_time<? AND u.role='owner' AND u.test =0";

		List<UserRegister> list = this.getList(sql, new Object[] { communityId, sta, end }, UserRegister.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<UserRegister> getUserRegisterTimeList(Integer communityId, Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "SELECT u.create_time,COUNT(u.user_id) AS installs_num,COUNT(case when  t.id IS  NULL  AND u.username IS NOT NULL then u.user_id else NULL end) AS register_num ,"
				+ " COUNT(CASE WHEN t.id IS NOT NULL AND u.username IS NOT NULL THEN 	u.user_id ELSE NULL END) AS test_num  "
				+ " FROM users u LEFT JOIN try_out t ON u.user_id = t.users_id WHERE u.community_id=? AND u.test =0 "
				+ "  AND u.create_time>=? AND u.create_time<=? AND u.role='owner' GROUP BY day(from_unixtime(u.create_time)) ";

		return this.getList(sql, new Object[] { communityId, startTimeInt, endTimeInt }, UserRegister.class);
	}

	@Override
	public UserRegister getUserRegisterEndTime(Integer communityId, Integer endTimeInt) throws Exception {
		String sql = "SELECT u.create_time,COUNT(u.user_id) AS installs_num,COUNT(case when  t.id IS  NULL  AND u.username IS NOT NULL then u.user_id else NULL end) AS register_num,"
				+ "COUNT(CASE WHEN t.id IS NOT NULL AND u.username IS NOT NULL THEN 	u.user_id ELSE NULL END) AS test_num  FROM users u LEFT JOIN try_out t ON u.user_id = t.users_id  WHERE "
				+ " u.community_id=?  AND  u.create_time<? AND u.role='owner' AND u.test =0 ";

		List<UserRegister> list = this.getList(sql, new Object[] { communityId, endTimeInt }, UserRegister.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<UserRegister> getUserRegisterTimeGroup(Integer communityId, Integer startTimeInt, Integer endTimeInt, List<Integer> endList) throws Exception {
		List<UserRegister> listUr = new ArrayList<UserRegister>();
		for (int i = 0; i < endList.size(); i++) {
			String sql = "SELECT ? as create_time,COUNT(u.user_id) AS installs_num,COUNT(case when t.id IS  NULL  AND u.username IS NOT NULL then u.user_id else NULL end) AS register_num,"
					+ "COUNT(CASE WHEN t.id IS NOT NULL AND u.username IS NOT NULL THEN u.user_id ELSE NULL END) AS test_num  FROM users u LEFT JOIN try_out t ON u.user_id = t.users_id  WHERE "
					+ " u.community_id=?  AND  u.create_time<? AND u.role='owner' AND u.test =0 ";

			List<UserRegister> list = this.getList(sql, new Object[] { endList.get(i), communityId, endList.get(i) }, UserRegister.class);
			UserRegister ur = list != null && list.size() > 0 ? list.get(0) : null;
			listUr.add(ur);
		}
		return listUr;
	}

	@Override
	public List<TryOut> getListTryOut() throws Exception {
		return this.getList("SELECT * FROM try_out ", new Object[] {}, TryOut.class);
	}

	@Override
	public boolean updateUserByEmobId(Users users) throws Exception {
		List<Object> params = new ArrayList<Object>(1);
		params.add(users.getEmobId());
		return this.updateData("UPDATE users SET character_values = character_values + 1  WHERE emob_id = ? ", params, null) > 0;
	}

	@Override
	public UserPercent getUserPercent(Integer communityId, String emobId) throws Exception {
		String sql = " SELECT sum(case when ifnull(character_values,0) < (select character_values from users where emob_id = ?) then 1 else 0 end) as character_count  "
				+ ", count(*) - 1 as user_count FROM users u WHERE community_id = ? ";

		List<UserPercent> list = this.getList(sql, new Object[] { emobId, communityId }, UserPercent.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public MoralStatistics getMoralStatistics(Integer communityId) throws Exception {
		String sql = "SELECT "
				+ " AVG(u.character_values) AS avg_character_values,"
				+ " COUNT(case when u.character_values>=0 AND u.character_values<=50 then u.user_id else NULL end) AS character_values50,"
				+ " COUNT(case when u.character_values>=51 AND u.character_values<=100 then u.user_id else NULL end) AS character_values100,"
				+ " COUNT(case when u.character_values>=101 AND u.character_values<=200 then u.user_id else NULL end) AS character_values200,"
				+ " COUNT(case when u.character_values>201 AND u.character_values<500 then u.user_id else  NULL end) AS character_values500,"
				+ " COUNT(case when u.character_values>500  then u.user_id else  NULL end) AS character_values"
				+ "  FROM  users u  WHERE   u.community_id = ?  AND u.role='owner' AND u.test =0   AND u.username IS NOT NULL";
		List<MoralStatistics> list = this.getList(sql, new Object[] { communityId }, MoralStatistics.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Users> getMoralList(Integer communityId) throws Exception {
		return this.getList("SELECT * FROM users u WHERE u.community_id=? AND u.role='owner' AND u.username IS NOT NULL ORDER BY u.character_values DESC", new Object[] { communityId }, Users.class);
	}

	@Override
	public List<Users> getUsersByEquipmentVersion(String version) throws Exception {
		return this.getList("SELECT * FROM users WHERE equipment_version >= ?", new Object[] { version }, Users.class);
	}

	@Override
	public List<Users> getListUsers(Integer communityId, Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT * FROM users u WHERE u.community_id=? AND u.role='owner' AND u.create_time>? AND u.create_time<?  AND u.username IS NULL ";
		return this.getList(sql, new Object[] { communityId, startTime, endTime }, Users.class);
	}

	@Override
	public List<Users> getListReg(Integer communityId) throws Exception {
		String sql = "SELECT * FROM users u WHERE u.community_id=? AND u.role='owner'   AND u.username IS NOT NULL ";
		return this.getList(sql, new Object[] { communityId }, Users.class);
	}

	@Override
	public Users getUserByCommunityId(Integer communityId, String username) throws Exception {
		String sql = "SELECT * FROM users WHERE username = ? AND community_id = ?";
		List<Users> list = this.getList(sql, new Object[] { username, communityId }, Users.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Users> registerList(Integer communityId) throws Exception {
		return this.getList("SELECT * FROM users WHERE community_id = ? AND username IS NOT NULL", new Object[] { communityId }, Users.class);
	}

	@Override
	public List<UserPartner> getUserRegisterCommunity(String emobId) throws Exception {
		String sql = "SELECT " +
				" c.community_id,"
				+ "	c.community_name,"
				+ "	COUNT(CASE "
				+ " WHEN t.id IS NULL "
				+ "  THEN "
				+ " u.user_id "
				+ " ELSE "
				+ " NULL "
				+ " END "
				+ " ) AS installs_num,"
				+ "	COUNT( "
				+ " CASE "
				+ " WHEN t.id IS NULL "
				+ " AND u.username IS NOT NULL THEN "
				+ " u.user_id "
				+ " ELSE "
				+ " NULL "
				+ " END "
				+ " ) AS register_num "
				+ " FROM "
				+ " users u "
				+ " LEFT JOIN try_out t ON u.user_id = t.users_id "
				+ " LEFT JOIN communities c ON c.community_id = u.community_id "
				+ " WHERE " + " u.community_id IN ( " + " SELECT "
				+ " p.community_id " + " FROM "
				+ " partner_permission p where   p.emob_id=?" + " )"
				+ " AND  u.role='owner'  GROUP BY " + " u.community_id ";

		return this.getList(sql, new Object[] { emobId }, UserPartner.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<com.xj.stat.po.Users> getRegisterActiveUsersDay(Set set, Integer communityId) throws Exception {
		if (set.size() > 0) {
			Iterator<String> it = set.iterator();

			String sql = "SELECT * from users u WHERE 1=1 and u.emob_id in (";
			int e = 0;
			while (it.hasNext()) {
				if (e == 0) {
					sql += "'" + it.next() + "'";
				} else {
					sql += ",'" + it.next() + "'";
				}
				e++;
			}
			sql += ") and u.community_id =? and u.create_time>1433088000 and u.role = 'owner' AND u.username IS NOT NULL";
			return this.getList(sql, new Object[] { communityId }, com.xj.stat.po.Users.class);
		}
		
		return new ArrayList<com.xj.stat.po.Users>(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<com.xj.stat.po.Users> getRegisterActiveUsersDay2(Set set, Integer communityId) throws Exception {
		if (set.size() > 0) {
			Iterator<String> it = set.iterator();

			String sql = "SELECT * from users u WHERE 1=1 and u.emob_id in (";
			int e = 0;
			while (it.hasNext()) {
				if (e == 0) {
					sql += "'" + it.next() + "'";
				} else {
					sql += ",'" + it.next() + "'";
				}
				e++;
			}
			sql += ") and u.community_id =? and  u.role = 'owner' ";
			return this.getList(sql, new Object[] { communityId }, com.xj.stat.po.Users.class);
		}
		
		return new ArrayList<com.xj.stat.po.Users>(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean upLiveness(Liveness liveness) throws Exception {
		List<Liveness> list = this.getList("SELECT * FROM liveness WHERE community_id = ?", new Object[] {liveness.getCommunityId()}, Liveness.class);
		if (list.size() == 0) {
			MyReturnKey key = new MyReturnKey();
			this.save(liveness, key);
			return key.getId() > 0;
		}
		
		Object[] resultObject = DaoUtils.reflect(liveness);
		if (resultObject == null || resultObject[1] == null || ((List<Object>) resultObject[1]).size() == 0) {
			return false;
		}
		
		String sql = "UPDATE liveness SET ";
		sql += (String) resultObject[0];
		sql += " WHERE community_id = ?";
		
		List<Object> params = (List<Object>) resultObject[1];
		params.add(liveness.getCommunityId());
		
		return this.updateData(sql, params, null) > 0;
	}

	@Override
	public Liveness getLiveness(Integer communityId) throws Exception {
		List<Liveness> list = this.getList("SELECT * FROM liveness WHERE  community_id = ?", new Object[] {communityId}, Liveness.class);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales) throws Exception {
		String sql = "   SELECT c.crazy_sales_id,c.create_time,c.end_time,c.title,c.descr,s.shop_name,c.status FROM crazy_sales c LEFT JOIN shops s ON c.emob_id=s.emob_id " +
				" LEFT JOIN crazy_sales_community cs ON cs.crazy_sales_id=c.crazy_sales_id " +
				" WHERE c.status!='delete'  AND " +
				" cs.community_id=? " +
				" AND ( c.title LIKE '"+crazySales.getTitle()+"'"+
				" OR s.shop_name LIKE '"+crazySales.getDescr()+"' ) ORDER BY c.create_time DESC";
		return this.getData4Page(sql, new Object[]{crazySales.getCommunityId()}, crazySales.getPageNum(), crazySales.getPageSize(), 10, CrazySalesShop.class);
	}

	@Override
	public void insertMessage(Messages ms) throws Exception {
		this.save(ms, new MyReturnKey());
	}

	@Override
	public List<Users> getListTryOutUser(Integer communityId) throws Exception {
		String sql="SELECT * FROM users u WHERE u.community_id=?  AND u.test =21 ";
		return this.getList(sql, new Object[] { communityId}, Users.class);
	}

	@Override
	public List<Users> getSimpleUser(Integer communityId) throws Exception {
		return this.getList("SELECT * FROM users WHERE community_id = ? AND role = 'owner' AND username IS NOT NULL", new Integer[] { communityId }, Users.class);
	}
	
	@Override
	public int updateUsersBonusCoin(List<String> emobIds, int i) {
		String sql = "  UPDATE users SET bonuscoin_count = bonuscoin_count + ? WHERE emob_id in (  ";
		StringBuffer sb = new StringBuffer();
		for (String string : emobIds) {
			sb.append("'"+string+"'").append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		sql +=sb.toString();
		return this.getJdbcTemplate().update(sql, i);
	}
	
	@Override
	public List<Users> getCommunityCompeteBangzhuUser(Integer integer,Integer invitCount,List<Users> users,int limit) {
		String sql = "   SELECT u.nickname,u.emob_id,t.c,u.character_values  FROM users u  INNER JOIN ( "
				+ "  SELECT emob_id, count(i.regist_emob_id) AS c  FROM  invited_user i "
				+ "  WHERE  i.regist_emob_id IS NOT NULL GROUP BY emob_id  ) t  ON u.emob_id = t.emob_id "
				+ "  WHERE t.c>=? AND  u.community_id =? AND u.username IS NOT NULL AND u.grade!='normal' " ;
		if(CollectionUtils.isNotEmpty(users) ){
			StringBuffer sb = new StringBuffer();
			sb.append("  AND u.emob_id NOT in ( " );
			for (Users user : users) {
				sb.append("'"+user.getEmobId()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(") ");
			sql +=sb.toString();
		}
			sql += " ORDER BY u.character_values DESC ,t.c  DESC LIMIT ?  ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){
			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Users> users = new ArrayList<Users>() ;
				while(rs.next()){
					Users u = new Users();
					u.setEmobId(rs.getString("emob_id"));
					u.setInvitCount(rs.getInt("c"));
					u.setCharacterValues(rs.getInt("character_values"));
					u.setNickname(rs.getString("nickname"));
					users.add(u);
				}
				return users;
			}
		},invitCount,integer,limit);
	}
	
	@Override
	public List<String> getCommunityBangzhuUser(Integer communityId,
			String string) {
		String sql = " SELECT emob_id FROM users WHERE grade = ? AND community_id = ? ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<String> >(){
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<String> users = new ArrayList<String>() ;
				while(rs.next()){
					users.add(rs.getString("emob_id"));
				}
				return users;
			}
		},string,communityId);
	}
	
	@Override
	public void updateAllUserGrade(Integer communityId,String grade) {
		String sql = " UPDATE users SET grade = ? WHERE community_id = ? AND grade!='normal'";
		this.getJdbcTemplate().update(sql, grade,communityId);
	}
	
	@Override
	public int updateUsersGrade(String string, List<Users> bangzhu) {
		if(bangzhu!=null && bangzhu.size()>0){
			String sql = " UPDATE users SET grade = ? WHERE  emob_id  in  (";
			StringBuffer sb = new StringBuffer();
			for (Users users : bangzhu) {
				sb.append("'"+users.getEmobId()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(") ");
			sql +=sb.toString();
			return this.getJdbcTemplate().update(sql, string);
		}
		return 0;
	}

	@Override
	public List<Users> getCommunityCompeteBangzhuUserByEmobId(Integer integer,Integer invitCount,List<String> users,int limit) {
		String sql = "   SELECT u.nickname,u.emob_id,t.c,u.character_values  FROM users u  INNER JOIN ( "
				+ "  SELECT emob_id, count(i.regist_emob_id) AS c  FROM  invited_user i "
				+ "  WHERE  i.regist_emob_id IS NOT NULL GROUP BY emob_id  ) t  ON u.emob_id = t.emob_id "
				+ "  WHERE t.c>=? AND  u.community_id =? AND u.username IS NOT NULL AND u.grade!='normal' " ;
		if(CollectionUtils.isNotEmpty(users)){
			StringBuffer sb = new StringBuffer();
			sb.append("  AND u.emob_id NOT in ( " );
			for (String user : users) {
				sb.append("'"+user+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(") ");
			sql +=sb.toString();
		}
		sql += " ORDER BY u.character_values DESC LIMIT ?  ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){
			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				List<Users> users = new ArrayList<Users>() ;
				while(rs.next()){
					Users u = new Users();
					u.setEmobId(rs.getString("emob_id"));
					u.setInvitCount(rs.getInt("c"));
					u.setCharacterValues(rs.getInt("character_values"));
					u.setNickname(rs.getString("nickname"));
					users.add(u);
				}
				return users;
			}
		},invitCount,integer,limit);
	}
	
	@Override
	public List<Users> getCommunityCompeteZhanglaoUser(Integer communityId, List<Users> users, int limit) {
		String sql = " SELECT u.nickname,u.emob_id,u.character_values  FROM users u WHERE  u.community_id =? AND u.username IS NOT NULL AND u.grade!='normal' ";
		if(CollectionUtils.isNotEmpty(users) ){
			StringBuffer sb = new StringBuffer();
			sb.append(" AND u.emob_id NOT in ( " );
			for (Users user : users) {
				sb.append("'"+user.getEmobId()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(") ");
			sql +=sb.toString();
		}
		sql += " ORDER BY u.character_values DESC  LIMIT ? ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){
			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Users> users = new ArrayList<Users>() ;
				while(rs.next()){
					Users u = new Users();
					u.setEmobId(rs.getString("emob_id"));
					u.setCharacterValues(rs.getInt("character_values"));
					u.setNickname(rs.getString("nickname"));
					users.add(u);
				}
				return users;
			}
		},communityId,limit);
	}
	
	@Override
	public List<Users> getCommunityCompetezhanglaoUserByEmobId(
			Integer communityId, List<String> users, int limit) {
		String sql = "   SELECT u.nickname,u.emob_id,u.character_values  FROM users u   "
				+ "  WHERE   u.community_id =? AND u.username IS NOT NULL AND u.grade!='normal' " ;
		if(CollectionUtils.isNotEmpty(users)){
			StringBuffer sb = new StringBuffer();
			sb.append("  AND u.emob_id NOT in ( " );
			for (String user : users) {
				sb.append("'"+user+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(") ");
			sql +=sb.toString();
		}
		sql += " ORDER BY u.character_values DESC LIMIT ?  ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){
			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				List<Users> users = new ArrayList<Users>() ;
				
				while(rs.next()){
					Users u = new Users();
					u.setEmobId(rs.getString("emob_id"));
					u.setCharacterValues(rs.getInt("character_values"));
					u.setNickname(rs.getString("nickname"));
					users.add(u);
				}
				return users;
			}
		},communityId,limit);
	}

	@Override
	public UsersApp getUserAddress(String emobId) {
		String sql = " SELECT c.community_name,u.username,u.room,u.user_floor,u.user_unit FROM users u INNER JOIN communities c ON c.community_id=u.community_id WHERE u.emob_id=?";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<UsersApp>(){
			@Override
			public UsersApp extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next()) {
					return null;
				}
				
				UsersApp usersApp = new UsersApp();
				usersApp.setUsername(rs.getString("username"));
				usersApp.setCommunityName(rs.getString("community_name"));
				usersApp.setRoom(rs.getString("room"));
				usersApp.setUserFloor(rs.getString("user_floor"));
				usersApp.setUserUnit(rs.getString("user_unit"));
				
				return usersApp;
			}
		}, emobId);
	}
	
	@Override
	public List<Users> getSetupUsersWithoutTest(Integer communityId, Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT u.user_id,u.emob_id,u.username,u.nickname,u.create_time FROM users u  WHERE u.community_id=? AND u.test =0 AND u.role='owner'  AND u.create_time>=? AND u.create_time<?";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>() {
			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Users> users = new ArrayList<Users>();
				while (rs.next()) {
					Users user = new Users();
					user.setUserId(rs.getInt("user_id"));
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setNickname(rs.getString("nickname"));
					user.setCreateTime(rs.getInt("create_time"));
					
					users.add(user);
				}
				
				return users;
			}
		}, communityId, startTime, endTime);
	}

	@Override
	public Page<Users> getUserByCommunityIdWithPage(Integer communityId,
			Integer pageNum, Integer pageSize) {
		String sql = " SELECT username,nickname,emob_id FROM users WHERE community_id =? AND username IS NOT NULL  ORDER BY create_time DESC LIMIT ?,? ";
		List<Users> users = this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				 List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setNickname(rs.getString("nickname"));
					users.add(user);
				}
				return users;
			}
		},communityId,(pageNum-1)*pageSize,pageSize);
		
		int count = this.getJdbcTemplate().queryForInt(" SELECT count(1) FROM users WHERE community_id =? AND username IS NOT NULL ", communityId);
		Page<Users> page = new Page<Users>(pageNum, count, pageSize, 10);
		page.setPageData(users);
		return page;
	}

	@Override
	public Page<Users> getUserByCommunityIdWithPageAndCondition(
			Integer communityId, Integer pageNum, Integer pageSize, String query) {
		String sql = " SELECT username,nickname,emob_id FROM users WHERE community_id =? AND (username like '%"+query+"%' OR nickname LIKE '%"+query+"%' )   ORDER BY create_time DESC LIMIT ?,? ";
		List<Users> users = this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				 List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setNickname(rs.getString("nickname"));
					users.add(user);
				}
				return users;
			}
		},communityId,(pageNum-1)*pageSize,pageSize);
		
		int count = this.getJdbcTemplate().queryForInt(" SELECT count(1) FROM users WHERE community_id =? AND username IS NOT NULL ", communityId);
		Page<Users> page = new Page<Users>(pageNum, count, pageSize, 10);
		page.setPageData(users);
		return page;
	}
	
	@Override
	public List<Users> getElectionWinners(Integer communityId,String month,Integer limitStart,Integer limitEnd)throws Exception {
		String sql = "SELECT u.nickname,u.emob_id FROM users u LEFT JOIN election_rank er ON u.emob_id = er.emob_id WHERE u.community_id =? AND u.role = 'owner' AND u.nickname IS NOT NULL AND er.time_month =? ORDER BY score DESC, er.update_time, u.user_id LIMIT ?, ?  ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				 List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setNickname(rs.getString("nickname"));
					users.add(user);
				}
				return users;
			}
		},communityId,month,limitStart,limitEnd);
	}

	@Override
	public Users getUserByUserId(Integer userId) throws Exception {
		List<Users> list = this.getList("SELECT * FROM users WHERE user_id = ?", new Object[] { userId }, Users.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	@Override
	public List<Users> getIosUserEquipmentTokenByCommunity(Integer communityId) {
		String sql = " SELECT username,emob_id,equipment,nickname,equipment_version,device_token FROM users WHERE community_id = ? AND equipment = 'ios'  AND test=0 ";
		return  this.jdbcTemplate.query(sql, new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setEquipment(rs.getString("equipment"));
					user.setNickname(rs.getString("nickname"));
					user.setEquipmentVersion(rs.getString("equipment_version"));
					user.setDeviceToken(rs.getString("device_token"));
					users.add(user);
				}
				return users;
			}
		},communityId);
	}

	@Override
	public List<Users> getUserEquipmentAliasByCommunity(Integer communityId, String string) {
		String sql = " SELECT username,emob_id,equipment,nickname,equipment_version,device_token FROM users WHERE community_id = ? AND equipment = ? AND test=0 ";
		return  this.jdbcTemplate.query(sql, new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setEquipment(rs.getString("equipment"));
					user.setNickname(rs.getString("nickname"));
					user.setEquipmentVersion(rs.getString("equipment_version"));
					user.setDeviceToken(rs.getString("device_token"));
					users.add(user);
				}
				return users;
			}
		},communityId,string);
	}

	/**
	 * 将卸载了app的用户deviceToken置空
	 */
	@Override
	public void updateUserDeviceTokenToNull(List<String> tokens) {
		if(CollectionUtils.isEmpty(tokens)){
			return ;
		}
		StringBuilder sb = new StringBuilder("UPDATE users SET device_token = NULL WHERE test=0 AND device_token  in (");
		Object[] params = new Object[tokens.size()];
		for (int i = 0; i < tokens.size(); i++) {
			sb.append("?,");
			params[i]=tokens.get(i);
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		
		this.jdbcTemplate.update(sb.toString(),params);
	}

	@Override
	public List<Users> getUsersByIds(List<Integer> userId) {
		StringBuilder sb = new StringBuilder("SELECT username,emob_id,equipment,nickname,equipment_version,device_token FROM users WHERE user_id in (");
		Object[] params = new Object[userId.size()];
		for (int i = 0; i < userId.size(); i++) {
			sb.append("?,");
			params[i] = userId.get(i);
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		
		return  this.jdbcTemplate.query(sb.toString(), new ResultSetExtractor<List<Users>>(){

			@Override
			public List<Users> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Users> users = new ArrayList<Users>();
				while(rs.next()){
					Users user = new Users();
					user.setEmobId(rs.getString("emob_id"));
					user.setUsername(rs.getString("username"));
					user.setEquipment(rs.getString("equipment"));
					user.setNickname(rs.getString("nickname"));
					user.setEquipmentVersion(rs.getString("equipment_version"));
					user.setDeviceToken(rs.getString("device_token"));
					users.add(user);
				}
				return users;
			}
		},params);
	}
}