package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Clearing;
import com.xj.bean.DeductMoney;
import com.xj.bean.Deduction;
import com.xj.bean.Page;
import com.xj.bean.ReckoningHistory;
import com.xj.bean.ShopsDeduction;
import com.xj.dao.DeductionDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("deductionDao")
public class DeductionDaoImpl extends MyBaseDaoImpl implements DeductionDao {

	@Override
	public Integer addDeduction(Deduction deduction) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(deduction, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateDeduction(Deduction deduction) throws Exception {
		String sql = " UPDATE deduction SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(deduction);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}
		sql += " WHERE deduction_id = ? ";
		list.add(deduction.getDeductionId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	@Override
	public Page<Deduction> getDeductionList(String emobId, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT * FROM deduction WHERE emob_id = ?";
		List<Object> list = new ArrayList<Object>();
		list.add(emobId);
		Page<Deduction> page = this.getData4Page(sql, list.toArray(), pageNum,
				pageSize, nvm, Deduction.class);
		return page;
	}

	@Override
	public Page<DeductMoney> getDeductMoney(String emobId, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception {
		String sql = " SELECT c.emob_id_agent,c.emob_id_from,c.emob_id_to,a.nickname,a.phone,d.deduction_price,d.deduction_detail,d.status,d.complaint_id,d.deduction_id "
				+ " FROM deduction d LEFT JOIN admin a ON a.emob_id=d.emob_id_customer LEFT JOIN complaints c ON c.complaint_id=d.complaint_id  LEFT JOIN  orders o ON o.emob_id_shop=c.emob_id_to"
				+ " WHERE c.emob_id_to=?  AND  o.start_time >? AND o.start_time<?  GROUP BY d.deduction_id";
		Page<DeductMoney> page = this.getData4Page(sql, new Object[] { emobId,
				startTime, endTime }, pageNum, pageSize, 10, DeductMoney.class);
		return page;
	}

	public List<ShopsDeduction> getShopsDeduction(String emobId)
			throws Exception {
		String sql = "SELECT FROM_UNIXTIME(d.start_time,'%Y-%m') as months , count(d.deduction_id) as count FROM deduction d LEFT JOIN complaints c ON d.complaint_id = c.complaint_id WHERE c.emob_id_to = ? group by months order by months desc";
		List<ShopsDeduction> list = this.getList(sql, new Object[] { emobId },
				ShopsDeduction.class);
		return list;
	}

	@Override
	public List<Deduction> getDeductionList(String emobId, String months)
			throws Exception {
		String sql = " SELECT d.deduction_id , d.start_time , d.end_time , d.status , d.deduction_detail , d.emob_id_customer , d.emob_id_agent , d.complaint_id , d.deduction_price  FROM deduction d LEFT JOIN complaints c ON d.complaint_id = c.complaint_id WHERE c.emob_id_to = ? AND FROM_UNIXTIME(d.start_time,'%Y-%m') = ? ";
		List<Deduction> list = this.getList(sql,
				new Object[] { emobId, months }, Deduction.class);
		return list;
	}

	@Override
	public Page<ReckoningHistory> getReckoningHistory(Integer communityId,String sort,Integer pageNum,
			Integer pageSize) throws Exception {
		String sql="SELECT " +
				" c.start_time," +
				" c.end_time," +
				" c.clearing_time AS time_group," +
				" SUM(c.online_price) AS reckoning_price," +
				" COUNT(c.emob_id_shop) AS ended_num," +
				" SUM(c.deduction_price) AS deduction_price " +
				" FROM " +
				" clearing c LEFT JOIN shops s ON c.emob_id_shop=s.emob_id " +
				" WHERE " +
				" s.community_id = ? AND (s. STATUS != 'block') 	AND (s. STATUS != 'suspend')  " +
				" GROUP BY " +
				" DAY ( " +
				" from_unixtime(c.create_time) ) ";
		
		Page<ReckoningHistory> page = this.getData4Page(sql, new Object[] {communityId},
				pageNum, pageSize, 10, ReckoningHistory.class);
		return page;
	}
	public Page<ReckoningHistory> getReckoningHistory4(Integer communityId,String sort,Integer pageNum,
			Integer pageSize) throws Exception {
		String sql="SELECT" +
		" (	SELECT" +
		"	COUNT(s.emob_id) " +
		" FROM " +
		" shops s " +
		" WHERE " +
		" s.sort = 2 " +
		" AND s.community_id = ?" +
		" AND s. STATUS != 'suspend' " +
		" AND s. STATUS != 'block' " +
		" ) AS no_ended_num, " +
		" MONTH (from_unixtime(t1.end_time)) AS month_group, " +
		" t1.end_time AS time_group, " +
		" t1.price AS reckoning_price, " +
		" t2.end_shop AS ended_num, " +
		" t3.ded_num AS deduction_shop_num, " +
		" t3.ded_price AS deduction_price " +
		" FROM " +
		"( SELECT " +
		" o.end_time, " +
		" WEEK (from_unixtime(o.end_time)) AS week_end_time, " +
		" SUM(o.order_price) AS price " +
		" FROM " +
		" shops s " +
		" LEFT JOIN orders o ON s.emob_id = o.emob_id_shop " +
		" WHERE " +
		" s.sort = 2" +
		" AND s.community_id = ?  " +
		" AND s. STATUS != 'suspend' " +
		" AND s. STATUS != 'block' " +
		" GROUP BY " +
		" WEEK (from_unixtime(o.end_time)) " +
		" ) t1 " +
		" LEFT JOIN ( " +
		" SELECT " +
		" COUNT(c.emob_id_shop) AS end_shop, " +
		" WEEK ( " +
		" from_unixtime(c.start_time) " +
		" ) AS week_start_time " +
		" FROM " +
		" clearing c " +
		" LEFT JOIN shops s ON s.emob_id = c.emob_id_shop " +
		"	WHERE " +
		" s.sort = 2 " +
		" AND s.community_id = ? " +
		" AND s. STATUS != 'suspend' " +
		" AND s. STATUS != 'block' " +
		" " +
		" GROUP BY " +
		" WEEK ( " +
		" from_unixtime(c.start_time) " +
		" )" +
		" ) t2 ON t1.week_end_time = t2.week_start_time " +
		" LEFT JOIN ( " +
		" SELECT " +
		" WEEK (from_unixtime(d.end_time)) AS week_d_end_time, " +
		" COUNT(d.deduction_id) AS ded_num, " +
		" SUM(d.deduction_price) AS ded_price " +
		" FROM " +
		" deduction d " +
		" LEFT JOIN shops s ON s.emob_id = d.emob_id_customer " +
		" WHERE " +
		" s.sort = 2 " +
		" AND s.community_id = ? " +
		" AND s. STATUS != 'suspend' " +
		" AND s. STATUS != 'block' " +
		" AND d. STATUS = 'ended' " +
		" GROUP BY " +
		" WEEK (from_unixtime(d.end_time)) " +
		") t3 ON t3.week_d_end_time = t1.week_end_time";
		
		Page<ReckoningHistory> page = this.getData4Page(sql, new Object[] {communityId,communityId,communityId,communityId},
				pageNum, pageSize, 10, ReckoningHistory.class);
		return page;
	}

	public Page<ReckoningHistory> getReckoningHistory3(Integer communityId,
			String sort, Integer pageNum, Integer pageSize) throws Exception {
		String sql = "SELECT	( "
				+ " SELECT COUNT(s.emob_id)"
				+ " FROM "
				+ " shops s "
				+ " WHERE "
				+ " s.sort = 2 "
				+ "  "
				+ " AND s. STATUS != 'suspend' "
				+ " AND s. STATUS != 'block' "
				+ " ) AS no_ended_num,"
				+ " month(from_unixtime(t1.end_time)) AS month_group,t1.end_time AS time_group,"
				+ " t1.price as reckoning_price ,"
				+ " "
				+ " t2.end_shop as ended_num,"
				+ "  t3.ded_num as deduction_shop_num,"
				+ "  t3.ded_price as deduction_price"
				+ " FROM "
				+ "(SELECT	o.end_time,WEEK (from_unixtime(o.end_time)) AS week_end_time,"
				+ "	SUM(o.order_price) AS price " + "	FROM " + "	orders o "
				+ "	LEFT JOIN (" + "	SELECT " + "	s.emob_id," + "	s.shop_name"
				+ "	FROM " + "	shops s" + "	WHERE " + "	s.sort = 2 " + "	  "
				+ "	AND s. STATUS != 'suspend' " + "	AND s. STATUS != 'block' "
				+ "	) s ON o.emob_id_shop = s.emob_id " + "	GROUP BY "
				+ "	WEEK (from_unixtime(o.end_time)) " + "	) t1 "
				+ " LEFT JOIN (" + "	SELECT "
				+ "	COUNT(c.emob_id_shop) AS end_shop, " + "	WEEK ( "
				+ "	from_unixtime(c.start_time) " + "	) AS week_start_time "
				+ "	FROM " + "	clearing c " + "	GROUP BY " + "	WEEK ( "
				+ "	from_unixtime(c.start_time) " + "	) "
				+ " ) t2 ON t1.week_end_time = t2.week_start_time"
				+ " LEFT JOIN ( " + "	SELECT"
				+ "	WEEK (from_unixtime(d.end_time)) AS week_d_end_time, "
				+ "	COUNT(d.deduction_id) AS ded_num, "
				+ "	SUM(d.deduction_price) AS ded_price " + "	FROM"
				+ "	deduction d " + "	WHERE " + "  d. STATUS = 'ended' "
				+ "	GROUP BY " + "	WEEK (from_unixtime(d.end_time)) "
				+ " ) t3 ON t3.week_d_end_time = t1.week_end_time";

		Page<ReckoningHistory> page = this.getData4Page(sql, new Object[] {},
				pageNum, pageSize, 10, ReckoningHistory.class);
		return page;
	}

	public Page<ReckoningHistory> getReckoningHistory2(String sort,
			Integer pageNum, Integer pageSize) throws Exception {
		// String sql =
		// " SELECT  month(from_unixtime(o.start_time)) AS time_group,"
		// + " SUM(o.order_price) AS reckoning_price,"
		// +
		// "SUM(case when cl.status = 'ended' then 1 else 0 end) AS ended_num,"
		// +
		// "SUM(case when cl.status != 'ended' then 1 else 0 end) AS no_ended_num,"
		// +
		// "sum(case when d.status = 'ended' then 1 else 0 end) AS  deduction_shop_num,"
		// + "SUM(d.deduction_price) AS deduction_price "
		// + " FROM shops s  LEFT JOIN orders o ON o.emob_id_shop=s.emob_id "
		// + "LEFT JOIN complaints c ON c.emob_id_to=s.emob_id LEFT JOIN "
		// + "user_bonus ub ON ub.order_id=o.order_id LEFT JOIN deduction d"
		// +
		// " ON d.complaint_id=c.complaint_id LEFT JOIN clearing cl ON cl.emob_id_shop=s.emob_id GROUP BY month(from_unixtime(o.start_time))";

		// String sql =
		// " SELECT   month(from_unixtime(a.start_time)) AS month_group,a.start_time AS time_group,"
		// + "SUM(a.reckoning_price)  AS reckoning_price,"
		// + " SUM(a.ended_num) AS ended_num,"
		// + "SUM(a.no_ended_num)  AS no_ended_num,"
		// + "SUM(a.deduction_shop_num) AS  deduction_shop_num,"
		// + "SUM(a.deduction_price) AS deduction_price "
		// + " FROM ( SELECT o.start_time AS time_group, "
		// + " SUM(o.order_price) AS reckoning_price, "
		// + " (case when cl.status = 'ended' then 1 else 0 end) AS ended_num, "
		// +
		// " (case when cl.status IS NULL or cl.status != 'ended'  then 1 else 0 end) AS no_ended_num, "
		// +
		// " (case when d.status IS NOT NULL or d.status = 'ended' then 1 else 0 end) AS  deduction_shop_num, "
		// +
		// " (d.deduction_price) AS deduction_price ,c.complaint_id,d.deduction_id,o.start_time,o.order_id "
		// +
		// " FROM shops s  LEFT JOIN orders o ON o.emob_id_shop=s.emob_id LEFT JOIN "
		// + " complaints c ON c.emob_id_to=s.emob_id "
		// + " LEFT JOIN user_bonus ub ON ub.order_id=o.order_id  "
		// + " LEFT JOIN deduction d ON d.complaint_id=c.complaint_id "
		// +
		// " LEFT JOIN clearing cl ON cl.emob_id_shop=s.emob_id AND WEEK(from_unixtime(cl.create_time)) = WEEK(from_unixtime(o.start_time)) GROUP BY s.emob_id) a  GROUP BY  WEEK(from_unixtime(a.start_time))";

		String sql = " SELECT   month(from_unixtime(a.start_time)) AS month_group,a.start_time AS time_group,SUM(a.reckoning_price)"
				+ " AS reckoning_price, SUM(a.ended_num) AS ended_num,SUM(a.no_ended_num) "
				+ " AS no_ended_num,SUM(a.deduction_shop_num) AS  deduction_shop_num, "
				+ " SUM(a.deduction_price) AS deduction_price "
				+ " FROM ( SELECT o.start_time AS time_group,  "
				+ " SUM(o.order_price) AS reckoning_price, "
				+ " (case when cl.status = 'ended' then 1 else 0 end) AS ended_num, "
				+ " (case when cl.status IS NULL or cl.status != 'ended'  then 1 else 0 end) AS no_ended_num, "
				+ " (case when d.status IS NOT NULL or d.status = 'ended' then 1 else 0 end) AS  deduction_shop_num, "
				+ " (d.deduction_price) AS deduction_price ,c.complaint_id,d.deduction_id,o.start_time,o.order_id  "
				+ " FROM shops s  LEFT JOIN orders o ON o.emob_id_shop=s.emob_id LEFT JOIN  "
				+ " complaints c ON c.emob_id_to=s.emob_id  LEFT JOIN user_bonus ub ON ub.order_id=o.order_id "
				+ " LEFT JOIN deduction d ON d.complaint_id=c.complaint_id  LEFT JOIN clearing cl ON "
				+ " cl.emob_id_shop=s.emob_id AND WEEK(from_unixtime(cl.create_time)) = (WEEK(from_unixtime(o.start_time))+1) WHERE s.sort=?  AND s.status!='suspend' AND s.status!='block' GROUP BY WEEK(from_unixtime(o.start_time)),s.emob_id) a"
				+ " GROUP BY  WEEK(from_unixtime(a.start_time))  ";

		Page<ReckoningHistory> page = this.getData4Page(sql,
				new Object[] { sort }, pageNum, pageSize, 10,
				ReckoningHistory.class);
		return page;
	}

	@Override
	public Integer addClearing(Clearing clearing) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(clearing, key);
		return key.getId();
	}

	@Override
	public Deduction getDeduction(String emobId, Integer startTime,
			Integer endTime) throws Exception {
		String sql = "SELECT deduction_id , start_time , end_time , status "
				+

				" , deduction_detail , emob_id_customer , emob_id_agent "
				+

				" , complaint_id , sum(deduction_price) as deduction_price "
				+

				" FROM deduction d where emob_id_customer = ? and status = 'ended' and end_time >= ? and end_time <= ? ";
		List<Deduction> list = this.getList(sql, new Object[] { emobId,
				startTime, endTime }, Deduction.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
