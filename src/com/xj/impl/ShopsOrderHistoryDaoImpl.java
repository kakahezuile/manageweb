package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BuyRanking;
import com.xj.bean.CommentsDetailList;
import com.xj.bean.CommentsStatistics;
import com.xj.bean.CommentsTop;
import com.xj.bean.ComplaintsDetailList;
import com.xj.bean.DeductMoney;
import com.xj.bean.OrderEmobId;
import com.xj.bean.Page;
import com.xj.bean.PayOff;
import com.xj.bean.ShopOrdersTime;
import com.xj.bean.ShopSupervision;
import com.xj.bean.ShopsClickDetail;
import com.xj.bean.ShopsOrderAmount;
import com.xj.bean.ShopsOrderDetailList;
import com.xj.bean.ShopsOrderHistory;
import com.xj.bean.ShopsStatisticsTop;
import com.xj.bean.TradingRecord;
import com.xj.bean.UserShops;
import com.xj.dao.ShopsOrderHistoryDao;

@Repository("shopsOrderHistoryDao")
public class ShopsOrderHistoryDaoImpl extends MyBaseDaoImpl implements
		ShopsOrderHistoryDao {

	@Override
	public Page<ShopsOrderHistory> getShopsOrdersHistoryList(String emobId, String sort, String action, Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT o.serial, o.end_time, s.shop_name, s.logo, (o.end_time - o.start_time) as service_time, ifnull(o.order_price,'0.00') - ifnull(bs.bonus_par,'0.00') as order_price, round(avg(c.score),0) as score, s.emob_id as emob_id_shop "
				+ " FROM orders o left join shops s on s.emob_id = o.emob_id_shop left join user_bonus ub on o.order_id = ub.order_id left join bonus bs on bs.bonus_id = ub.bonus_id LEFT JOIN comments c ON s.emob_id=c.emob_id_to "
				+ " where 1 = 1 ";
		List<Object> list = new ArrayList<Object>();
		if (action != null) {
			sql += " and o.action = ? and o.emob_id_user = ? ";
			list.add(action);
		} else {
			sql += " and s.sort = ? and o.emob_id_user = ? ";
			list.add(sort);
		}
		list.add(emobId);
		sql += " and o.status = 'ended' group by o.order_id order by o.end_time desc";
		
		return this.getData4Page(sql, list.toArray(), pageNum, pageSize, nvm, ShopsOrderHistory.class);
	}

	/**
	 * 统计下单量 列表
	 */
	@Override
	public Page<ShopsOrderAmount> getQuickShopList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {
		// String sql =
		// "SELECT sum(case when o.online='yes' AND o.status='ended' then o.order_price else 0 end) AS"
		// +
		// " online_price,o.start_time,s.shop_id,s.shop_name,COUNT(o.order_id) AS "
		// +
		// "order_quantity,SUM(case when o.status='ended' then o.order_price else 0 end) AS order_price FROM shops s"
		// +
		// " LEFT JOIN orders o ON o.emob_id_shop=s.emob_id  WHERE  s.community_id=? AND s.sort=? AND o.start_time >=? AND o.start_time <= ? GROUP BY s.emob_id";

		String sql = "SELECT "
				+ " a.start_time,"
				+ " a.shop_name, "
				+ " a.shop_id,"
				+ " a.emob_id,"
				+ " SUM(case when  t.id IS NOT NULL   then a.end_order_quantity else NULL end)  AS test_end_order_quantity,"
				+ " SUM(case when  t.id IS   NULL   then a.end_order_quantity else NULL end)  AS end_order_quantity,"
				+ " COUNT(case when  t.id IS  NOT NULL   then a.emob_id_user else NULL end)  AS test_user_num,"
				+ " COUNT(case when  t.id IS   NULL   then a.emob_id_user else NULL end)  AS user_num,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.order_quantity else NULL end ) as test_order_quantity,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.online_price else NULL end ) as test_online_price,"
				+ " SUM( case when  t.id IS NOT NULL   then a.order_price else NULL end ) AS test_order_price,"
				+ " SUM(case when  t.id IS  NULL   then a.online_price else NULL end ) as online_price,"
				+ " SUM(case when  t.id IS  NULL   then a.order_quantity else NULL end ) AS order_quantity,"
				+ " SUM( case when  t.id IS  NULL   then a.order_price else NULL end ) AS order_price "
				+ " FROM "
				+ " ( "
				+ " SELECT "
				+ " s.emob_id, "
				+ " o.emob_id_user ,"
				+ " sum( "
				+ " CASE "
				+ " WHEN o. online = 'yes' "
				+ " AND o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS online_price, "
				+ " o.start_time, "
				+ " s.shop_id, "
				+ " s.shop_name, "
				+ " COUNT(o.order_id) AS order_quantity, "
				+ " COUNT(CASE WHEN o.STATUS = 'ended' THEN o.order_id ELSE NULL END) AS end_order_quantity,"
				+ " SUM( "
				+ " CASE "
				+ " WHEN o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS order_price "
				+ " FROM "
				+ " shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " WHERE "
				+ " s.community_id = ? "
				+ " AND s.sort = ? "
				+ " AND o.start_time >= ? "
				+ " AND o.start_time <= ? "
				+ " GROUP BY "
				+ " s.emob_id,o.emob_id_user "
				+ " ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user=t.emob_id GROUP BY a.emob_id order by a.start_time desc";
		Page<ShopsOrderAmount> page = this.getData4Page(sql, new Object[] {
				communityId, sort, startTime, endTime }, pageNum, pageSize, 10,
				ShopsOrderAmount.class);
		return page;
	}

	/**
	 * 统计下单头部
	 */
	@Override
	public ShopsStatisticsTop getQuickShopTop(Integer communityId, String sort,
			Integer startTime, Integer endTime) throws Exception {
		// String sql =
		// "SELECT SUM(case when o.online='yes' AND o.status='ended' then o.order_price else 0 end) AS online_price,COUNT(o.order_id) AS order_quantity,"
		// +
		// "SUM(case when o.status='ended' then o.order_price else 0 end) AS order_price FROM shops s "
		// +
		// "LEFT JOIN orders o ON o.emob_id_shop=s.emob_id  WHERE s.community_id=? AND  s.sort=? AND o.start_time >=? AND o.start_time <= ?";

		String sql = "SELECT "
				+ " SUM(case when  t.id IS NOT NULL   then a.bonus_par else NULL end)  AS test_bonus_par,"
				+ " SUM(case when  t.id IS   NULL   then a.bonus_par else NULL end)  AS bonus_par,"
				+ " SUM(case when  t.id IS NOT NULL   then a.end_order_quantity else NULL end)  AS test_end_order_quantity,"
				+ " SUM(case when  t.id IS   NULL   then a.end_order_quantity else NULL end)  AS end_order_quantity,"
				+ " COUNT(case when  t.id IS  NOT NULL   then a.emob_id_user else NULL end)  AS test_user_num,"
				+ " COUNT(case when  t.id IS   NULL   then a.emob_id_user else NULL end)  AS user_num,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.online_price else NULL end ) as test_online_price,"
				+ " SUM(case when  t.id IS NOT NULL   then a.order_quantity else NULL end ) AS test_order_quantity,"
				+ " SUM( case when  t.id IS NOT NULL   then a.order_price else NULL end ) AS test_order_price,"
				+ " SUM(case when  t.id IS  NULL   then a.online_price else NULL end ) as online_price, "
				+ " SUM(case when  t.id IS  NULL   then a.order_quantity else NULL end ) AS order_quantity,"
				+ " SUM( case when  t.id IS  NULL   then a.order_price else NULL end ) AS order_price"
				+ " FROM "
				+ " ("
				+ " SELECT SUM(b.bonus_par) as bonus_par,"
				+ " o.emob_id_user,"
				+ " SUM("
				+ " CASE "
				+ " WHEN o. ONLINE = 'yes' "
				+ " AND o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS online_price, "
				+ " COUNT(o.order_id) AS order_quantity, "
				+ " COUNT(CASE WHEN o.STATUS = 'ended' THEN o.order_id ELSE NULL END) AS end_order_quantity,"
				+ " SUM(  CASE WHEN o. STATUS = 'ended' THEN "
				+ " o.order_price  ELSE  0  END "
				+ " ) AS order_price  FROM  shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " LEFT JOIN user_bonus ub ON ub.serial=o.serial LEFT JOIN bonus b ON ub.bonus_id=b.bonus_id "
				+ " WHERE s.community_id = ?  AND s.sort = ? "
				+ " AND o.start_time >=?  AND o.start_time <= ? "
				+ " GROUP BY  o.emob_id_user  ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user=t.emob_id";

		List<ShopsStatisticsTop> list = this.getList(sql, new Object[] {
				communityId, sort, startTime, endTime },
				ShopsStatisticsTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;

	}

	/**
	 * 使用量详情
	 */
	@Override
	public Page<ShopsClickDetail> getQuickShopDetailList(String sort,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {
		// String sql = " SELECT o.start_time,s.shop_name,COUNT(o.order_id) "
		// +
		// " AS order_quantity,day(from_unixtime(start_time)) AS use_time FROM shops s "
		// +
		// " LEFT JOIN orders o ON o.emob_id_shop=s.emob_id WHERE s.shop_id=? AND o.start_time >=? AND o.start_time <= ? GROUP BY day(from_unixtime(o.start_time)) ";

		String sql = "SELECT "
				+ " a.start_time, "
				+ " a.shop_name,"
				+ " SUM(case when  t.id IS NOT NULL   then a.end_order_quantity else NULL end)  AS test_end_order_quantity,"
				+ " SUM(case when  t.id IS   NULL   then a.end_order_quantity else NULL end)  AS end_order_quantity,"
				+ " COUNT(case when  t.id IS  NOT NULL   then a.emob_id_user else NULL end)  AS test_user_num,"
				+ " COUNT(case when  t.id IS   NULL   then a.emob_id_user else NULL end)  AS user_num,"
				+ " SUM(case when  t.id IS   NULL   then a.order_quantity else NULL end ) as order_quantity,"
				+ " SUM(case when  t.id IS NOT NULL   then a.order_quantity else NULL end ) as test_order_quantity,"
				+ " a.use_time  FROM  (  SELECT  DAY ( "
				+ " from_unixtime(o.start_time)  ) AS time,"
				+ " o.emob_id_user, o.start_time,  s.shop_name,"
				+ " COUNT(o.order_id) AS order_quantity,"
				+ " COUNT(CASE WHEN o.STATUS = 'ended' THEN o.order_id ELSE NULL END) AS end_order_quantity,"
				+ " DAY (from_unixtime(start_time)) AS use_time  FROM "
				+ " shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " WHERE "
				+ " s.shop_id=? AND o.start_time >=? AND o.start_time <= ? "
				+ " GROUP BY  DAY (  from_unixtime(o.start_time) "
				+ " ),  o.emob_id_user  ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user = t.emob_id "
				+ " GROUP BY 	a.time";

		Page<ShopsClickDetail> page = this.getData4Page(sql, new Object[] {
				sort, startTime, endTime }, pageNum, pageSize, 10,
				ShopsClickDetail.class);
		return page;
	}

	/**
	 * 
	 * 投诉 头部
	 */
	@Override
	public CommentsTop getQuickShopCommentsTop(Integer communityId,
			String sort, Integer startTime, Integer endTime) throws Exception {
		//
		// String sql =
		// "SELECT SUM(a.orderNum) AS orders_num,SUM(case when a.judge='good_comments' then a.comments_num else 0 end)  "
		// +
		// "AS good_comments,SUM(a.comments_num) AS comments_num,ROUND(AVG(a.score),1)  AS score, "
		// +
		// " SUM(a.complaints_num) AS complaints_num FROM (SELECT  COUNT(DISTINCT o.order_id,o.order_id) AS orderNum,"
		// +
		// " (case when c.score>3 then 'good_comments' else 'bad_comments' end)  AS judge,ROUND(AVG(c.score),1) "
		// +
		// " AS score ,COUNT(DISTINCT c.comment_id,c.comment_id)  AS comments_num, "
		// +
		// " COUNT(DISTINCT cs.community_id,cs.community_id) AS complaints_num FROM shops s LEFT JOIN orders o "
		// +
		// " ON o.emob_id_shop=s.emob_id  LEFT JOIN  comments c  ON c.emob_id_to=s.emob_id LEFT JOIN complaints cs ON cs.emob_id_to=s.emob_id "
		// + " WHERE  s.sort=? AND o.start_time >=? AND o.start_time <= ? "
		// +
		// " GROUP BY   case when c.score>3  then 'good_comments' else 'bad_comments' end,s.emob_id) a ";

		String sql2 = "SELECT COUNT(c.comment_id) AS comments_num,SUM(case when c.score>3 then 1 else 0 end)  AS good_comments,ROUND(AVG(c.score),1)  AS score,"
				+ " (SELECT	COUNT(c.complaint_id) FROM	shops s"
				+ " LEFT JOIN complaints c ON c.emob_id_to = s.emob_id AND c.occur_time>? AND  c.occur_time<? AND c.community_id =?  WHERE 	s.sort = ? "
				+ " AND s. STATUS != 'suspend' AND s. STATUS != 'block') AS complaints_num ,"
				+ " (SELECT	COUNT(o.order_id) FROM 	"
				+ " shops s LEFT JOIN orders o ON o.emob_id_shop = s.emob_id AND o.start_time >=? AND o.start_time <= ? "
				+ " WHERE 	s.sort = ? AND s. STATUS != 'suspend' AND s. STATUS != 'block') AS orders_num"
				+ " FROM shops s "
				+ " LEFT JOIN  comments  c ON c.emob_id_to = s.emob_id  "
				+ " AND c.create_time>? AND c.create_time<? WHERE s.community_id=? AND	s.sort = ? AND s. STATUS != 'suspend' AND s. STATUS != 'block' ";

		List<CommentsTop> list = this.getList(sql2, new Object[] { startTime,
				endTime, communityId, sort, startTime, endTime, sort,
				startTime, endTime, communityId, sort }, CommentsTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 获取评价 列表
	 */
	@Override
	public Page<CommentsStatistics> getQuickShopCommentsList(
			Integer communityId, String sort, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception {

		// String sql =
		// " SELECT SUM(case when a.judge='bad_comments' then a.comments_num else 0 end)  AS bad_comments,SUM(a.comments_num) AS comments_num,"
		// +
		// " a.shop_id,a.shop_name,a.score, SUM(a.complaints_num) AS complaints_num"
		// +
		// " FROM (SELECT s.emob_id,s.shop_id,s.shop_name,(case when c.score>3 then 'good_comments' else 'bad_comments' end) "
		// +
		// " as judge,ROUND(AVG(c.score),1) AS score ,COUNT(DISTINCT c.comment_id,c.comment_id)  AS comments_num,COUNT(DISTINCT cs.community_id,cs.community_id) "
		// + " AS complaints_num FROM shops s LEFT JOIN orders o "
		// + " ON o.emob_id_shop=s.emob_id LEFT JOIN  comments c "
		// +
		// " ON c.emob_id_to=s.emob_id LEFT JOIN complaints cs ON cs.emob_id_to=s.emob_id  WHERE  s.sort=? AND o.start_time >=? AND o.start_time <= ? group by   case when c.score>3  then 'good_comments' else 'bad_comments' end,s.emob_id) a  "
		// + " GROUP BY a.emob_id ";

		String sql = "SELECT t1.shop_id,t1.shop_name,"
				+ " t1.judge AS bad_comments,t1.comments_num,"
				+ " t1.score,t2.complaints_num FROM  ("
				+ "	SELECT s.emob_id, s.shop_name, s.shop_id,"
				+ " COUNT(c.comment_id) AS comments_num, SUM( CASE"
				+ " WHEN c.score < 4 THEN 1 ELSE 0 END ) AS judge,"
				+ " ROUND(AVG(c.score), 1) AS score FROM shops s"
				+ " LEFT JOIN comments c ON c.emob_id_to = s.emob_id"
				+ " WHERE s.sort = ? AND s.community_id =?"
				+ " AND s. STATUS != 'suspend' AND s. STATUS != 'block'"
				+ " AND c.create_time>? AND c.create_time<? GROUP BY"
				+ " s.emob_id ) t1 LEFT JOIN ( SELECT"
				+ " s.emob_id, COUNT(c.complaint_id) AS complaints_num"
				+ " FROM shops s"
				+ " LEFT JOIN complaints c ON c.emob_id_to = s.emob_id"
				+ " WHERE s.sort = ? AND s.community_id =? "
				+ " AND s. STATUS != 'suspend' AND s. STATUS != 'block'"
				+ " AND c.occur_time>? AND  c.occur_time<? GROUP BY"
				+ "	s.emob_id ) t2 ON t1.emob_id = t2.emob_id"
				+ " GROUP BY t1.emob_id";

		Page<CommentsStatistics> page = this.getData4Page(sql, new Object[] {
				sort, communityId, startTime, endTime, sort, communityId,
				startTime, endTime }, pageNum, pageSize, 10,
				CommentsStatistics.class);
		return page;
	}

	/**
	 * 投诉详情页面
	 */
	@Override
	public Page<CommentsDetailList> getQuickShopCommentsDetail(String shopId,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {
		String sql = "SELECT u.nickname,u.user_floor,u.user_unit,c.create_time,c.content ,ROUND(AVG(c.score),1) "
				+ " AS score FROM shops s LEFT JOIN comments c ON "
				+ " c.emob_id_to=s.emob_id LEFT JOIN users u ON c.emob_id_from=u.emob_id WHERE s.shop_id=?"
				+ " AND c.create_time >=? AND c.create_time <= ?  GROUP BY c.comment_id";

		Page<CommentsDetailList> page = this.getData4Page(sql, new Object[] {
				shopId, startTime, endTime }, pageNum, pageSize, 10,
				CommentsDetailList.class);
		return page;
	}

	/**
	 * 订单详情
	 */
	@Override
	public Page<ShopsOrderDetailList> getQuickShopOrderDetail(String shopId,
			Integer pageNum, Integer pageSize, Integer startTimeInt,
			Integer endTimeInt) throws Exception {
		// String sql =
		// "SELECT o.start_time AS start_time ,s.shop_name,SUM(case when  o.status='ended' then o.order_price else 0 end) "
		// +
		// " AS order_price,sum(case when o.online='yes' AND o.status='ended' then o.order_price else 0 end) AS online_price "
		// +
		// " FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id WHERE s.shop_id=? AND o.start_time >=? AND o.start_time <= ? "
		// + " GROUP BY day(from_unixtime(o.start_time))";

		String sql = "SELECT "
				+ " a.start_time,"
				+ " a.shop_name,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.online_price else NULL end ) as test_online_price,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.order_price else NULL end ) AS test_order_price,"
				+ " SUM(case when  t.id IS  NULL   then a.online_price else NULL end ) as online_price,"
				+ " SUM(case when  t.id IS  NULL   then a.order_price else NULL end ) AS order_price"
				+ " FROM "
				+ " ( "
				+ " SELECT "
				+ " DAY ( "
				+ " from_unixtime(o.start_time) "
				+ " ) AS time, "
				+ " o.emob_id_user, "
				+ " o.start_time AS start_time, "
				+ " s.shop_name, "
				+ " SUM( "
				+ " CASE "
				+ " WHEN o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS order_price, "
				+ " sum( "
				+ " CASE "
				+ " WHEN o. ONLINE = 'yes' "
				+ " AND o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS online_price "
				+ " FROM "
				+ " shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " WHERE  "
				+ "	s.shop_id=? AND o.start_time >=? AND o.start_time <= ?"
				+ " GROUP BY "
				+ " DAY ( "
				+ " from_unixtime(o.start_time) "
				+ " ),o.emob_id_user "
				+ " ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user=t.emob_id GROUP BY a.time";

		Page<ShopsOrderDetailList> page = this.getData4Page(sql, new Object[] {
				shopId, startTimeInt, endTimeInt }, pageNum, pageSize, 10,
				ShopsOrderDetailList.class);
		return page;
	}

	/**
	 * 投诉详情
	 */
	@Override
	public Page<ComplaintsDetailList> getComplaintsDetailList(String shopId,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {

		String sql = "SELECT u.nickname,u.user_floor,u.user_unit,c.detail,c.occur_time,c.status "
				+ " FROM shops s  RIGHT  JOIN complaints c "
				+ " ON c.emob_id_to=s.emob_id LEFT JOIN users u  ON c.emob_id_from=u.emob_id "
				+ " WHERE s.shop_id=? AND c.occur_time >=? AND c.occur_time <= ? group by c.complaint_id ";
		Page<ComplaintsDetailList> page = this.getData4Page(sql, new Object[] {
				shopId, startTime, endTime }, pageNum, pageSize, 10,
				ComplaintsDetailList.class);
		return page;
	}

	/**
	 * 使用量 分时间段统计
	 */
	@Override
	public Page<ShopsClickDetail> getUseAmountList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {
		// String sql = " SELECT o.start_time,s.shop_name,COUNT(o.order_id) "
		// +
		// " AS order_quantity, day(from_unixtime(start_time)) AS use_time FROM shops s "
		// +
		// " LEFT JOIN orders o ON o.emob_id_shop=s.emob_id WHERE s.community_id =? AND s.sort = ?"
		// +
		// " AND o.start_time >=? AND o.start_time <= ? GROUP BY day(from_unixtime(o.start_time)) ";

		String sql = "SELECT "
				+ " a.start_time, "
				+ " a.emob_id, "
				+ " a.shop_name,"
				+ " SUM(case when  t.id IS NOT NULL   then a.end_order_quantity else NULL end)  AS test_end_order_quantity,"
				+ " SUM(case when  t.id IS   NULL   then a.end_order_quantity else NULL end)  AS end_order_quantity,"
				+ " COUNT(case when  t.id IS  NOT NULL   then a.emob_id_user else NULL end)  AS test_user_num,"
				+ " COUNT(case when  t.id IS   NULL   then a.emob_id_user else NULL end)  AS user_num,"
				+ " SUM(case when  t.id IS   NULL   then a.order_quantity else NULL end ) as order_quantity,"
				+ " SUM(case when  t.id IS NOT NULL   then a.order_quantity else NULL end ) as test_order_quantity,"
				+ " a.use_time  FROM  (  SELECT  DAY ( "
				+ " from_unixtime(o.start_time)  ) AS time,"
				+ " o.emob_id_user, o.start_time,  s.shop_name,s.emob_id,"
				+ " COUNT(o.order_id) AS order_quantity,"
				+ " COUNT(CASE WHEN o.STATUS = 'ended' THEN o.order_id ELSE NULL END) AS end_order_quantity,"
				+ " DAY (from_unixtime(start_time)) AS use_time  FROM "
				+ " shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " WHERE s.community_id = ? AND s.sort = ? "
				+ " AND o.start_time >= ?  AND o.start_time <= ? "
				+ " GROUP BY  DAY (  from_unixtime(o.start_time) "
				+ " ),  o.emob_id_user  ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user = t.emob_id "
				+ " GROUP BY 	a.time";

		Page<ShopsClickDetail> page = this.getData4Page(sql, new Object[] {
				communityId, sort, startTime, endTime }, pageNum, pageSize, 10,
				ShopsClickDetail.class);
		return page;
	}

	/**
	 * 使用金额 分时间段统计
	 */
	@Override
	public Page<ShopsOrderDetailList> getTurnoverList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		// String sql =
		// "SELECT o.start_time AS start_time ,s.shop_name,SUM(case when o.status='ended' then o.order_price else 0 end) "
		// +
		// " AS order_price,sum(case when o.online='yes' AND o.status='ended' then o.order_price else 0 end) AS online_price "
		// +
		// " FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id WHERE  s.community_id =? AND s.sort=? AND o.start_time >=? AND o.start_time <= ? "
		// + " GROUP BY day(from_unixtime(o.start_time))";

		String sql = "SELECT "
				+ " a.start_time,"
				+ " a.shop_name,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.online_price else NULL end ) as test_online_price,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.order_price else NULL end ) AS test_order_price,"
				+ " SUM(case when  t.id IS  NULL   then a.online_price else NULL end ) as online_price,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.bonus_par else NULL end ) AS test_bonus_par,"
				+ " SUM(case when  t.id IS  NULL   then a.bonus_par else NULL end ) as bonus_par,"
				+ " SUM(case when  t.id IS  NULL   then a.order_price else NULL end ) AS order_price"
				+ " FROM "
				+ " ( "
				+ " SELECT "
				+ " SUM(b.bonus_par) AS bonus_par,"
				+ " DAY ( "
				+ " from_unixtime(o.start_time) "
				+ " ) AS time, "
				+ " o.emob_id_user, "
				+ " o.start_time AS start_time, "
				+ " s.shop_name, "
				+ " SUM( "
				+ " CASE "
				+ " WHEN o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS order_price, "
				+ " sum( "
				+ " CASE "
				+ " WHEN o. ONLINE = 'yes' "
				+ " AND o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS online_price "
				+ " FROM "
				+ " shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " LEFT JOIN user_bonus ub ON ub.serial=o.serial LEFT JOIN bonus b ON ub.bonus_id=b.bonus_id   "
				+ " WHERE  "
				+ "	s.community_id = ? "
				+ " AND s.sort = ? "
				+ " AND o.start_time >= ? "
				+ " AND o.start_time <= ? "
				+ " GROUP BY "
				+ " DAY ( "
				+ " from_unixtime(o.start_time) "
				+ " ),o.emob_id_user "
				+ " ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user=t.emob_id GROUP BY a.time";

		Page<ShopsOrderDetailList> page = this.getData4Page(sql, new Object[] {
				communityId, sort, startTimeInt, endTimeInt }, pageNum,
				pageSize, 10, ShopsOrderDetailList.class);
		return page;
	}

	/**
	 * 服务时长
	 */
	@Override
	public Page<ShopOrdersTime> getTimeQuantumList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "SELECT  s.shop_name,sum(case when (o.end_time-o.start_time)<900 and (o.end_time-o.start_time)>0 then 1 else 0 end) AS serve_time15, "
				+" sum(case when (o.end_time-o.start_time)>900 and (o.end_time-o.start_time)<1800 then 1 else 0 end) AS serve_time30, "
				+" sum(case when (o.end_time-o.start_time)>1800 and (o.end_time-o.start_time)<2700 then 1 else 0 end) AS serve_time45, "
				+" sum(case when (o.end_time-o.start_time)>2700 then 1 else 0 end) AS serve_time  "
				+",AVG(case when (o.end_time-o.start_time)>0 then 1 else 0 end) AS avg_serve_time "
				+" FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id "
				+" WHERE s.community_id=? AND s.sort=? AND o.start_time >=? AND o.start_time <= ?  GROUP BY s.emob_id";
		Page<ShopOrdersTime> page = this.getData4Page(sql, new Object[] {
				communityId, sort, startTimeInt, endTimeInt }, pageNum,
				pageSize, 10, ShopOrdersTime.class);
		return page;
	}

	/**
	 * 服务时间 头部
	 */
	@Override
	public ShopOrdersTime getTimeQuantumTop(Integer communityId, String sort,
			Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT  s.shop_name,sum(case when (o.end_time-o.start_time)<900 and (o.end_time-o.start_time)>0 then 1 else 0 end) AS serve_time15, "
				+" sum(case when (o.end_time-o.start_time)>900 and (o.end_time-o.start_time)<1800 then 1 else 0 end) AS serve_time30, "
				+" sum(case when (o.end_time-o.start_time)>1800 and (o.end_time-o.start_time)<2700 then 1 else 0 end) AS serve_time45, "
				+" sum(case when (o.end_time-o.start_time)>2700 then 1 else 0 end) AS serve_time "
				+",AVG(case when (o.end_time-o.start_time)>0 then (o.end_time-o.start_time) else 0 end) AS avg_serve_time "
				+" FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id "
				+" WHERE s.community_id=? AND s.sort=? AND o.start_time >=? AND o.start_time <= ? ";
		List<ShopOrdersTime> list = this.getList(sql, new Object[] {
				communityId, sort, startTime, endTime }, ShopOrdersTime.class);
		return list != null && list.size() > 0 ? list.get(0) : null;

	}

	/**
	 * 结款
	 */
	@Override
	public Page<PayOff> getPayOff(String sort, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime, Integer myDate)
			throws Exception {
		String sql = "SELECT  a.emob_id,a.shop_id,a.shop_name,a.phone,SUM(a.price_sum) AS price_sum,"
				+ " a.status,sum(a.charge)AS charge,a.card_no,SUM(a.deduction_price) AS deduction_price,SUM(a.bonus_sum) AS bonus_sum ,a.clearing_id "
				+ " FROM (SELECT s.emob_id,s.shop_id,s.shop_name,s.phone,SUM(o.order_price) AS price_sum,cl.status,(case when d.status IS NOT NULL OR d.status = 'ended' then 1 else 0 end) AS "
				+ " charge,x.card_no, (d.deduction_price) AS deduction_price,SUM(b.bonus_par) AS bonus_sum ,cl.clearing_id FROM shops s   LEFT JOIN orders o ON o.emob_id_shop=s.emob_id LEFT JOIN "
				+ " complaints c ON c.emob_id_to=s.emob_id  LEFT JOIN xj_bank_card x ON x.emob_id=s.emob_id  LEFT JOIN user_bonus ub  "
				+ " ON ub.order_id=o.order_id LEFT JOIN  bonus b ON b.bonus_id=ub.bonus_id  LEFT JOIN deduction d ON  d.complaint_id=c.complaint_id LEFT JOIN clearing cl "
				+ " ON cl.emob_id_shop=s.emob_id AND WEEK(from_unixtime(cl.create_time))=WEEK(from_unixtime(?)) AND WEEK(from_unixtime(c.occur_time))=WEEK(from_unixtime(?)) "
				+ " WHERE s.sort=? AND WEEK(from_unixtime(o.start_time))=WEEK(from_unixtime(?))   GROUP BY c.complaint_id) a GROUP BY a.emob_id  ";

		Page<PayOff> page = this.getData4Page(sql, new Object[] { myDate,
				myDate, sort, myDate }, pageNum, pageSize, 10, PayOff.class);
		return page;
	}

	/**
	 * 扣款申请
	 */
	@Override
	public Page<DeductMoney> getDeductMoney(String emobId, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception {
		String sql = "SELECT c.complaint_id,a.nickname,a.phone,c.process_detail,c.deduction_price,c.status,d.deduction_id "
				+ " FROM complaints c LEFT JOIN admin a ON a.emob_id=c.emob_id_agent "
				+ " LEFT JOIN deduction d ON d.compaints_id=c.community_id LEFT JOIN  orders o ON o.emob_id_shop=c.emob_id_to"
				+ " WHERE c.emob_id_to=?  AND o.start_time >? AND o.start_time<? ";
		Page<DeductMoney> page = this.getData4Page(sql, new Object[] { emobId,
				startTime, endTime }, pageNum, pageSize, 10, DeductMoney.class);
		return page;
	}

	/**
	 * 交易详情
	 */
	@Override
	public Page<TradingRecord> getTradingRecord(String shopId, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception {
		// String sql =
		// "SELECT b.bonus_par,u.nickname,u.username AS user_name,u.user_floor,u.user_unit,u.room,cm.community_address, "
		// +
		// " (case when o.online= 'yes' AND o.status='ended' then o.order_price else 0 end) AS onlie_price,(case when c.complaint_id IS NOT NULL  then 'yes' else 'no' end) AS is_complaint"
		// +
		// " FROM shops s LEFT JOIN  orders o ON s.emob_id=o.emob_id_shop LEFT JOIN users u ON o.emob_id_user=u.emob_id LEFT JOIN communities cm "
		// +
		// " ON u.community_id=cm.community_id  LEFT JOIN complaints c ON c.emob_id_to=s.emob_id LEFT JOIN user_bonus ub ON ub.order_id=o.order_id  LEFT JOIN bonus b ON b.bonus_id=ub.bonus_id WHERE o.order_price >0 AND s.shop_id=?  AND c.occur_time >=? AND c.occur_time <= ? ";

		String sql = "SELECT " + " b.bonus_par," + " u.nickname, "
				+ " u.username AS user_name, " + " u.user_floor,"
				+ " cm.community_name," + " u.user_unit, " + " u.room, "
				+ " (CASE " + " WHEN " + " o. STATUS = 'ended' THEN "
				+ " o.order_price " + " ELSE " + " 0 " + " END "
				+ " ) AS order_price, " + " ( " + " CASE "
				+ " WHEN o. ONLINE = 'yes' " + " AND o. STATUS = 'ended' THEN "
				+ " o.order_price " + " ELSE " + " 0 " + " END "
				+ " ) AS onlie_price, " + " ( " + " CASE "
				+ " WHEN c.complaint_id IS NOT NULL THEN " + " 'yes' "
				+ " ELSE " + " 'no' " + " END " + " ) AS is_complaint "
				+ " FROM " + " shops s "
				+ " LEFT JOIN orders o ON s.emob_id = o.emob_id_shop "
				+ " LEFT JOIN users u ON o.emob_id_user = u.emob_id "
				+ " LEFT JOIN communities cm ON u.community_id=cm.community_id"
				+ " LEFT JOIN complaints c ON c.emob_id_to = s.emob_id "
				+ " LEFT JOIN user_bonus ub ON ub.order_id = o.order_id "
				+ " LEFT JOIN bonus b ON b.bonus_id = ub.bonus_id " + " WHERE "
				+ " s.shop_id =? " + " AND o.status='ended' "
				+ " AND o.start_time >=? " + " AND o.start_time <=?";

		Page<TradingRecord> page = this.getData4Page(sql, new Object[] {
				shopId, startTime, endTime }, pageNum, pageSize, 10,
				TradingRecord.class);
		return page;
	}

	/**
	 * 结款历史
	 */
	@Override
	public Page<TradingRecord> getReckoningHistory(Integer pageNum,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	public Page<PayOff> getPayOff2(Integer communityId, String sort,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime, Integer myDate) throws Exception {

		String sql = "SELECT " + " t2.time_num, " + " t1.emob_id, "
				+ " t1.shop_id, " + " t1.shop_name," + " t1.card_no,"
				+ " t1.phone," + " t3. STATUS," + " t2.bonus_par AS bonus_sum,"
				+ " t2.order_price AS price_sum,"
				+ " t4.deduction_id AS charge,"
				+ " t4.deduction_price AS deduction_price " + " FROM "
				+ "(( (( " + " SELECT " + " s.shop_name AS shop_name, "
				+ " s.emob_id AS emob_id," + " s.shop_id AS shop_id,"
				+ " s.community_id AS community_id," + " s.phone AS phone, "
				+ " x.card_no AS card_no " + " FROM " + " ( shops s "
				+ " LEFT JOIN xj_bank_card x ON ((s.emob_id = x.emob_id)) "
				+ " ) " + " WHERE " + " ( (s.sort = 2) "
				+ "	AND (s.community_id = ?) " + "	AND (s. STATUS != 'block') "
				+ "	AND (s. STATUS != 'suspend') " + "	) " + " GROUP BY "
				+ " s.emob_id " + " ) t1 " + " LEFT JOIN ( " + " SELECT "
				+ " c.emob_id_shop AS emob_id_shop, "
				+ " c.end_time AS end_time," + " c.start_time AS start_time,"
				+ " c. STATUS AS STATUS " + " FROM " + " clearing c "
				+ " WHERE " + " c.start_time >= ? " + " AND c.start_time <= ? "
				+ " GROUP BY " + " c.emob_id_shop " + " ) t3 ON ( "
				+ " (t1.emob_id = t3.emob_id_shop) " + " ) " + " ) "
				+ " LEFT JOIN ( " + " SELECT " + " WEEK ( "
				+ " from_unixtime(o.start_time) " + " ) AS time_num, "
				+ " o.emob_id_shop AS emob_id_shop," + " SUM( " + " CASE "
				+ " WHEN o. STATUS = 'ended' THEN " + " o.order_price "
				+ " ELSE 0 END ) AS order_price,"
				+ " SUM(b.bonus_par) AS bonus_par " + " FROM "
				+ " (  ( 	orders o "
				+ " LEFT JOIN user_bonus ub ON ((o.serial = ub.serial)) "
				+ " ) LEFT JOIN bonus b ON ((ub.bonus_id = b.bonus_id)) "
				+ " ) WHERE " + " o.start_time >? " + " AND o.start_time < ? "
				+ " GROUP BY " + " o.emob_id_shop " + " ) t2 ON ( "
				+ " (t1.emob_id = t2.emob_id_shop) " + " )	) "
				+ " LEFT JOIN ( " + " SELECT "
				+ " count(d.deduction_id) AS deduction_id, "
				+ " sum(d.deduction_price) AS deduction_price, "
				+ " c.emob_id_to AS emob_id_to " + " FROM " + " ( "
				+ " deduction d " + " LEFT JOIN complaints c ON ( " + " ( "
				+ " d.complaint_id = c.complaint_id " + " ) "
				+ " ) ) GROUP BY 	c.emob_id_to " + " ) t4 ON ( " + " ( "
				+ " t3.emob_id_shop = t4.emob_id_to ))) " + " GROUP BY "
				+ " t1.shop_id";
		Page<PayOff> page = this.getData4Page(sql, new Object[] { communityId,
				startTime, endTime, startTime, endTime }, pageNum, pageSize,
				10, PayOff.class);
		return page;
	}

	public Page<PayOff> getPayOff3(Integer communityId, String sort,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime, Integer myDate) throws Exception {

		String sql = "SELECT	t2.time_num,t1.emob_id,t1.shop_id,t1.shop_name ,	"
				+ " t1.card_no ,	t1.phone ,t3.status,	"
				+ " t2.bonus_par AS bonus_sum,"
				+ "	t2.order_price AS price_sum,"
				+ "	t4.deduction_id AS charge,"
				+ "	t4.deduction_price AS deduction_price ,"
				+ " '' as clearing_id"
				+ " FROM "
				+ " ( (((SELECT	s.shop_name AS shop_name,"
				+ " s.emob_id AS emob_id,"
				+ "	s.shop_id AS shop_id,"
				+ " s.community_id AS community_id,"
				+ " s.phone AS phone,"
				+ " x.card_no AS card_no"
				+ "	FROM (shops s LEFT JOIN xj_bank_card x ON (	( "
				+ " s.emob_id = x.emob_id"
				+ "	)	)	)"
				+ "	WHERE (	(s.sort = 2)"
				+ "	AND (s.community_id =?)"
				+ "	AND (s.status != 'block')"
				+ " AND (s.status != 'suspend')	)"
				+ "	GROUP BY"
				+ " s.emob_id"
				+ " ) t1"
				+ "	LEFT JOIN ("
				+ "	SELECT "
				+ "	c.emob_id_shop AS emob_id_shop,"
				+ "	c.end_time AS end_time,"
				+ "	c.start_time AS start_time,"
				+ "	c.status AS status "
				+ "	FROM "
				+ "	clearing c WHERE WEEK(from_unixtime(c.start_time))=(WEEK(from_unixtime(?)))"
				+ "	GROUP BY "
				+ "	c.emob_id_shop "
				+ " ) t3 ON ("
				+ "	(	t1.emob_id = t3.emob_id_shop"
				+ " ))	)"
				+ "	LEFT JOIN ("
				+ " SELECT "
				+ " WEEK(from_unixtime(o.start_time)) AS time_num,"
				+ " o.emob_id_shop AS emob_id_shop,"
				+ "	SUM(CASE WHEN o. STATUS = 'ended' THEN  o.order_price  ELSE 0 END ) AS order_price,"
				+ "	SUM(b.bonus_par) AS bonus_par"
				+ "	FROM (	(	orders o "
				+ "	LEFT JOIN user_bonus ub ON ("
				+ "	(o.serial = ub.serial)"
				+ "	)	)			LEFT JOIN bonus b ON ("
				+ "	(	ub.bonus_id = b.bonus_id))) WHERE WEEK(from_unixtime(o.start_time))=(WEEK(from_unixtime(?)))"
				+ "	GROUP BY   o.emob_id_shop	) t2 ON (	("
				+ "	t1.emob_id = t2.emob_id_shop	))		)"
				+ " LEFT JOIN (	SELECT "
				+ "	count(d.deduction_id) AS deduction_id, "
				+ " sum(d.deduction_price) AS deduction_price, "
				+ " c.emob_id_to AS emob_id_to  FROM 	( deduction d "
				+ " LEFT JOIN complaints c ON ("
				+ " (	d.complaint_id = c.complaint_id	))) GROUP BY"
				+ " c.emob_id_to ) t4 ON ( ("
				+ " t3.emob_id_shop = t4.emob_id_to"
				+ " )	))GROUP BY	t1.shop_id";

		Page<PayOff> page = this.getData4Page(sql, new Object[] { communityId,
				endTime - (2 * 24 * 60 * 60), endTime - (2 * 24 * 60 * 60) },
				pageNum, pageSize, 10, PayOff.class);
		return page;
	}

	@Override
	public List<UserShops> getUserShop(String communityId, String sort,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		System.out.println(communityId +"   ：   "+startTimeInt+"   :   "+endTimeInt+"  ： "+sort);
		String sql = "  SELECT o.emob_id_user,o.emob_id_shop,o.status,o.online,u.user_id,u.user_floor,u.user_unit,u.room,u.username,u.nickname,o.start_time,o.end_time,o.order_price ,b.trade_no,b.trade_type,o.serial "
				+ " FROM orders o INNER JOIN users u ON u.emob_id=o.emob_id_user LEFT JOIN xj_bill b ON b.bill_channel = o.serial  WHERE o.community_id=? AND  o.start_time>? "
				+ " AND o.start_time<? and o.emob_id_shop in(SELECT emob_id FROM shops WHERE sort=? )  ";
		List<UserShops> page = this.getList(sql, new Object[] { communityId,
				startTimeInt, endTimeInt, sort },
				UserShops.class);

		return page;
	}

	@Override
	public List<BuyRanking> getBuyRanking(Integer communityId, String type)
			throws Exception {
		String sql = " SELECT u.emob_id,u.nickname as name,SUM(o.order_price) as price_num,COUNT(o.order_id) as trade_num "
				+ " FROM orders o LEFT JOIN users u ON o.emob_id_user=u.emob_id"
				+ " WHERE o.community_id=? AND o.status='ended' GROUP BY o.emob_id_user ";
		if (type.equals("price_num")) {
			sql += " order by price_num desc ";
		} else {
			sql += "  order by trade_num desc ";
		}
		List<BuyRanking> page = this.getList(sql, new Object[] { communityId },
				BuyRanking.class);
		return page;

	}

	@Override
	public List<UserShops> getUserShopWU(String communityId,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "  SELECT o.emob_id_user,o.emob_id_shop,o.status,o.online,u.user_id,u.user_floor,u.user_unit,u.room,u.username,u.nickname,o.start_time,o.end_time,o.order_price,'trade_no' trade_no,'trade_type' trade_type ,o.serial "
				+ " FROM orders o LEFT JOIN shops s ON s.emob_id=o.emob_id_shop LEFT JOIN users u ON u.emob_id=o.emob_id_user LEFT JOIN try_out t "
				+ " ON o.emob_id_user!=t.emob_id WHERE o.community_id=? AND "
				+ " o.end_time>? AND o.end_time<? AND o.status='ended' AND s.sort=2 AND o.online='yes' GROUP BY	o.order_id  ";
		List<UserShops> page = this.getList(sql, new Object[] { communityId,
				startTimeInt, endTimeInt }, UserShops.class);

		return page;
	}

	@Override
	public ShopsStatisticsTop getQuickShopTopH(Integer communityId,
			String sort, Integer startTimeInt, Integer endTimeInt)
			throws Exception {

		String sql = "SELECT "
				+ " SUM(case when  t.id IS NOT NULL   then a.bonus_par else NULL end)  AS test_bonus_par,"
				+ " SUM(case when  t.id IS   NULL   then a.bonus_par else NULL end)  AS bonus_par,"
				+ " SUM(case when  t.id IS NOT NULL   then a.end_order_quantity else NULL end)  AS test_end_order_quantity,"
				+ " SUM(case when  t.id IS   NULL   then a.end_order_quantity else NULL end)  AS end_order_quantity,"
				+ " COUNT(case when  t.id IS  NOT NULL   then a.emob_id_user else NULL end)  AS test_user_num,"
				+ " COUNT(case when  t.id IS   NULL   then a.emob_id_user else NULL end)  AS user_num,"
				+ " SUM(case when  t.id IS  NOT NULL   then a.online_price else NULL end ) as test_online_price,"
				+ " SUM(case when  t.id IS NOT NULL   then a.order_quantity else NULL end ) AS test_order_quantity,"
				+ " SUM( case when  t.id IS NOT NULL   then a.order_price else NULL end ) AS test_order_price,"
				+ " SUM(case when  t.id IS  NULL   then a.online_price else NULL end ) as online_price, "
				+ " SUM(case when  t.id IS  NULL   then a.order_quantity else NULL end ) AS order_quantity,"
				+ " SUM( case when  t.id IS  NULL   then a.order_price else NULL end ) AS order_price"
				+ " FROM "
				+ " ("
				+ " SELECT SUM(b.bonus_par) as bonus_par,"
				+ " o.emob_id_user,"
				+ " SUM("
				+ " CASE "
				+ " WHEN o. ONLINE = 'yes' "
				+ " AND o. STATUS = 'ended' THEN "
				+ " o.order_price "
				+ " ELSE "
				+ " 0 "
				+ " END "
				+ " ) AS online_price, "
				+ " COUNT(o.order_id) AS order_quantity, "
				+ " COUNT(CASE WHEN o.STATUS = 'ended' THEN o.order_id ELSE NULL END) AS end_order_quantity,"
				+ " SUM(  CASE WHEN o. STATUS = 'ended' THEN "
				+ " o.order_price  ELSE  0  END "
				+ " ) AS order_price  FROM  shops s "
				+ " LEFT JOIN orders o ON o.emob_id_shop = s.emob_id "
				+ " LEFT JOIN user_bonus ub ON ub.serial=o.serial LEFT JOIN bonus b ON ub.bonus_id=b.bonus_id "
				+ " WHERE s.community_id = ?  AND s.sort = ? "
				+ " AND o.start_time >=?  AND o.start_time <= ? AND o.online='yes'"
				+ " GROUP BY  o.emob_id_user  ) a "
				+ " LEFT JOIN try_out t ON a.emob_id_user=t.emob_id";

		List<ShopsStatisticsTop> list = this.getList(sql, new Object[] {
				communityId, sort, startTimeInt, endTimeInt },
				ShopsStatisticsTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<ShopSupervision> getShopDelie(Integer communityId, String sort)
			throws Exception {

		String sql = "SELECT  COUNT(o.order_id) as order_num, o.emob_id_user,"
				+ " o.emob_id_shop,"
				+ " u.nickname,"
				+ " u.user_floor,"
				+ " u.user_unit,"
				+ " u.room,"
				+ " u.username "
				+ " FROM "
				+ " orders o "
				+ " LEFT JOIN users u ON o.emob_id_user = u.emob_id "
				+ " WHERE "
				+ " o.community_id = ? and  o.emob_id_shop IN(SELECT s.emob_id FROM shops s WHERE s.sort=?) GROUP BY emob_id_user ORDER BY o.start_time DESC ";

		List<ShopSupervision> list = this.getList(sql, new Object[] {
				communityId, sort }, ShopSupervision.class);
		return list;
	}

	@Override
	public List<OrderEmobId> getOrderEmobId(Integer communityId, String sort,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "  SELECT FROM_UNIXTIME(o.start_time,'%Y-%m-%d') as start_date_y, FROM_UNIXTIME(o.start_time,'%H:%i:%s')"
				+ " as start_date_h,o.emob_id_user,o.emob_id_shop,o.status,o.online,u.user_id,u.user_floor,u.user_unit,u.room,u.username,u.nickname,"
				+ " o.start_time,o.end_time,o.order_price, FROM_UNIXTIME(o.start_time,'%H:%i:%s') as end_time_h "
				+ " FROM orders o LEFT JOIN users u ON u.emob_id=o.emob_id_user  WHERE o.community_id=? AND  o.start_time>? "
				+ " AND o.start_time<? and o.emob_id_shop in(SELECT emob_id FROM shops WHERE sort=? )  ";
		List<OrderEmobId> page = this.getList(sql, new Object[] { communityId,
				startTimeInt, endTimeInt, sort }, OrderEmobId.class);

		return page;
	}

	/**
	 * 送水服务时间段统计
	 */
	// @Override
	// public Page<ShopWaterTime> getWaterTime(String sort, Integer pageNum,
	// Integer pageSize, Integer startTime, Integer endTime)
	// throws Exception {
	// String sql =
	// "SELECT  s.shop_name,sum(case when (o.end_time-o.start_time)<900 and (o.end_time-o.start_time)>0 then 1 else 0 end) AS serve_time15, "
	// +
	//
	// " sum(case when (o.end_time-o.start_time)>900 and (o.end_time-o.start_time)<1800 then 1 else 0 end) AS serve_time30, "
	// +
	// " sum(case when (o.end_time-o.start_time)>1800 and (o.end_time-o.start_time)<2700 then 1 else 0 end) AS serve_time45, "
	// +" sum(case when (o.end_time-o.start_time)>2700 then 1 else 0 end) AS serve_time  "
	// +
	// ",AVG(case when (o.end_time-o.start_time)>0 then 1 else 0 end) AS avg_serve_time "
	// + " FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id "
	// +
	// " WHERE s.sort=? AND o.start_time >=? AND o.start_time <= ?  GROUP BY s.emob_id";
	//
	// Page<ShopWaterTime> page = this.getData4Page(sql, new Object[] { sort,
	// startTime, endTime}, pageNum, pageSize, 10,
	// ShopWaterTime.class);
	// return page;
	// }

}
