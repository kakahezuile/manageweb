package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BonusAndService;
import com.xj.bean.BonusStatistics;
import com.xj.bean.BonusUser;
import com.xj.bean.BonusUserOrder;
import com.xj.bean.Page;
import com.xj.bean.StatisticsBonusUser;

import com.xj.bean.UserBonus;
import com.xj.dao.UserBonusDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("userBonusDao")
public class UserBonusDaoImpl extends MyBaseDaoImpl implements UserBonusDao {

	@Override
	public Integer addUserBonus(UserBonus userBonus) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(userBonus, key);
		return key.getId();
	}

	@Override
	public List<BonusAndService> getBonusAndService(Integer userId) throws Exception {
		String sql = "SELECT us.user_bonus_id , us.bonus_id , us.start_time , us.expire_time , b.bonus_par , "
				+ " us.use_time , us.order_id , us.bonus_status , b.bonus_url , b.bonus_detail , b.bonus_name , b.bonus_r , b.bonus_g , b.bonus_b"
				+ " FROM user_bonus us left join bonus b "
				+ " on us.bonus_id = b.bonus_id where us.user_id = ? order by us.create_time desc ";
		return this.getList(sql, new Object[] { userId }, BonusAndService.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateUserBonus(UserBonus userBonus) throws Exception {
		String sql = " UPDATE user_bonus SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(userBonus);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE user_bonus_id = ? ";
		System.out.println(sql);
		list.add(userBonus.getUserBonusId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<BonusAndService> getBonusAndService(String emobId, String sort) throws Exception {
		String sql = "SELECT us.user_bonus_id , us.bonus_id , us.start_time , us.expire_time , b.bonus_par , us.bonus_type , "
				+ " us.use_time , us.order_id , us.bonus_status , b.bonus_url , b.bonus_detail , b.bonus_name , b.bonus_r , b.bonus_g , b.bonus_b"
				+ " FROM user_bonus us left join bonus b "
				+ " on us.bonus_id = b.bonus_id left join users u on us.user_id = u.user_id where u.emob_id = ? and (us.bonus_type = ? or us.bonus_type = -1)";
		return this.getList(sql, new Object[] { emobId, sort }, BonusAndService.class);
	}

	@Override
	public List<UserBonus> getUserBonus(Integer userBonusId) throws Exception {
		String sql = "SELECT * FROM user_bonus u WHERE user_bonus_id = ?";
		return this.getList(sql, new Object[] { userBonusId }, UserBonus.class);
	}

	/**
	 * 按时间统计帮帮卷
	 */
	@Override
	public Page<BonusStatistics> statisticsTimeBonus(Integer pageNum,
			Integer pageSize, String bonusName, String bonusPar,
			Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT ub.create_time, b.bonus_id,b.bonus_name,"
				+ "b.bonus_detail,COUNT(ub.bonus_id) AS bonus_num,SUM(b.bonus_par) AS bonus_par_sum,b.bonus_type,b.bonus_par FROM bonus b LEFT JOIN"
				+ " user_bonus ub ON ub.bonus_id=b.bonus_id WHERE 1=1 ";
		if (!bonusName.equals("全部")) {

			sql += " AND b.bonus_name='" + bonusName + "'";
		}

		if (!bonusPar.equals("")) {
			sql += " AND b.bonus_par=" + bonusPar;
		}

		if (startTime != 0) {
			sql += " AND ub.create_time>" + startTime + " AND ub.create_time<"
					+ endTime;
		}
		sql += " GROUP BY  ub.create_time  order by ub.create_time desc";
		Page<BonusStatistics> page = this.getData4Page(sql, new Object[] {},
				pageNum, pageSize, 4, BonusStatistics.class);

		return page;
	}

	@Override
	public int addThingUserBonus(List<UserBonus> userBonus) throws Exception {

		return this.excuteTrac(userBonus);
	}

	@Override
	public List<BonusAndService> getBonusAndService(String emobId) throws Exception {
		String sql = "SELECT us.user_bonus_id , us.bonus_id , us.start_time , us.expire_time , b.bonus_par , b.bonus_type , "
				+ " us.use_time , us.order_id , us.bonus_status , b.bonus_url , b.bonus_detail , b.bonus_name , b.bonus_r , b.bonus_g , b.bonus_b"
				+ " FROM  user_bonus us left join bonus b "
				+ " on us.bonus_id = b.bonus_id left join users u on us.user_id = u.user_id  where u.emob_id = ? order by us.create_time desc ";
		return this.getList(sql, new Object[] { emobId }, BonusAndService.class);
	}

	@Override
	public List<BonusUserOrder> getBonusUser(Integer createTime) throws Exception {
		String sql = "SELECT u.user_floor,u.user_unit,u.room,ub.serial,ub.bonus_id,u.nickname,u.username,ub.start_time,ub.expire_time,ub.order_id,ub.create_time,"
				+ "ub.bonus_status FROM user_bonus ub LEFT JOIN users u ON u.user_id=ub.user_id WHERE ub.create_time=?";
		return this.getList(sql, new Object[] { createTime }, BonusUserOrder.class);
	}

	@Override
	public List<BonusUser> getOrdersBonus(String serial) throws Exception {
		String sql = "SELECT s.shop_name,o.start_time,o.end_time,o.order_price "
				+ " FROM  orders o LEFT JOIN shops s ON o.emob_id_shop=s.emob_id WHERE o.serial=?";
		List<BonusUser> list = this.getList(sql, new Object[] { serial },
				BonusUser.class);
		return list;
	}

	@Override
	public StatisticsBonusUser statisticsBonusUser(Integer createTime)
			throws Exception {
		String sql = "SELECT " +
				"COUNT(ub.user_bonus_id) AS ub_num," +
				"COUNT(CASE WHEN ub.bonus_status = 'used' THEN  ub.user_bonus_id  ELSE  NULL  END) AS used_num" +
				" FROM " +
				" user_bonus ub WHERE ub.create_time=?";
		List<StatisticsBonusUser> list = this.getList(sql,
				new Object[] { createTime }, StatisticsBonusUser.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
