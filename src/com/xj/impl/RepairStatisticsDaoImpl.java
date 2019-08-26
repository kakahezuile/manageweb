package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.CommentsLists;
import com.xj.bean.ComplaintsDetail;
import com.xj.bean.ComplaintsList;
import com.xj.bean.ItemCategory;
import com.xj.bean.Page;
import com.xj.bean.PriceSum;
import com.xj.bean.RepairStatisticsCenter;
import com.xj.bean.RepairStatisticsComplaints;
import com.xj.bean.RepairStatisticsHistory;
import com.xj.bean.RepairStatisticsHistoryCenter;
import com.xj.bean.RepairStatisticsTop;
import com.xj.bean.Subpackage;
import com.xj.dao.RepairStatisticsDao;

@Repository("repairStatisticsDao")
public class RepairStatisticsDaoImpl extends MyBaseDaoImpl implements
		RepairStatisticsDao {

	/**
	 * 查询物业管理的top
	 */
	@Override
	public RepairStatisticsTop getRepairStatisticsTop(Integer communityId,long startTime,
			long endTime, String sort) throws Exception {
		// TODO Auto-generated method stub

		String sql = "SELECT COUNT(DISTINCT o.order_id,o.order_id) AS repair_count ,"

				+

				" COUNT(DISTINCT case when o.order_type='other' then o.order_id else NULL end)  AS subpackage_num,"+
				
				" COUNT(DISTINCT case when o.status = 'refuse' then o.order_id else NULL end)  AS today_un_complaints_count,"
				+
				" COUNT(DISTINCT case when o.status = 'renounce' then o.order_id else NULL end)  AS abandon_count,"
				+

				" COUNT(DISTINCT case when o.status = 'ended' AND end_time>? AND end_time<? then o.order_id else NULL end)  as end_order,COUNT(DISTINCT c.complaint_id,c.complaint_id) " +
				
				" AS today_complaints_count "

				+

				"  FROM  shops s LEFT JOIN orders o ON  o.emob_id_shop=s.emob_id  AND o.start_time >=? AND o.start_time <=?  LEFT JOIN complaints c "

				+

				" ON s.emob_id = c.emob_id_to  AND c.occur_time>=? AND c.occur_time<=?  "
				+

				" WHERE s.community_id=? AND  s.sort = ?  AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend' ";

		List<RepairStatisticsTop> list = this.getList(sql, new Object[] {
				startTime, endTime, startTime, endTime,  startTime,
				endTime,communityId,sort }, RepairStatisticsTop.class);
		
		String sql2="SELECT SUM(o.order_price ) as repair_money FROM shops s LEFT JOIN orders o ON o.emob_id_shop=s.emob_id AND o.start_time >=? AND o.start_time <=? WHERE s.community_id=? AND s.sort = 5  AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend'  ";
		List<PriceSum> list2= this.getList(sql2, new Object[] {startTime, endTime,communityId
			 }, PriceSum.class);
		RepairStatisticsTop rt=list.get(0);
		rt.setRepairMoney(list2.get(0).getRepairMoney());
		return rt;
	}

	/**
	 * 历史维修记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<RepairStatisticsCenter> getReaiStatisticsCenterList(Integer communityId,String sort) throws Exception {
		String sql = "SELECT s.shop_id , s.shop_name , s.phone , s.logo , u.avatar , s.status,u.nickname FROM shops s LEFT JOIN users u "
				+ " ON s.emob_id = u.emob_id WHERE s.community_id=? AND  s.sort = ?  AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend' ";
		List<RepairStatisticsCenter> list = this.getList(sql,
				new Object[] { communityId,sort }, RepairStatisticsCenter.class);
		List<RepairStatisticsCenter> listRc = new ArrayList<RepairStatisticsCenter>();
		for (RepairStatisticsCenter rc : list) {
			String sql2 = "SELECT it.cat_id,it.cat_name,it.keywords,it.cat_desc,it.sort_order,it.create_time,it.shop_type,it.img_name "
					+ "FROM shops_category s RIGHT JOIN item_category it "
					+ " ON s.category_id = it.cat_id WHERE  s.shop_id =? AND s.status='true'";
			List<ItemCategory> itList = this.getList(sql2,
					new Object[] {  rc.getShopId() }, ItemCategory.class);
			rc.setList(itList);
			listRc.add(rc);
		}
		return listRc;
	}

	@Override
	public List<RepairStatisticsHistory> getHistoryList(Integer communityId,Integer startTime,
			Integer endTime) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT s.shop_id , s.shop_name , COUNT(o.order_id) AS repair_statistics_count , s.phone "
				+

				" , u.nickname , u.avatar , COUNT(c.complaint_id) AS complaints_count , ROUND(AVG(c.score),0) AS score "
				+

				" FROM shops s LEFT JOIN orders o ON s.emob_id = o.emob_id_shop LEFT JOIN users u "
				+

				" ON s.emob_id = u.emob_id LEFT JOIN complaints c ON s.emob_id = c.emob_id_to "
				+

				" LEFT JOIN s.emob_id = s.emob_id_to " +

				" WHERE s.community_id=? AND o.start_time >= ? AND o.start_time <= ?  GROUP BY s.emob_id ";
		List<RepairStatisticsHistory> list = this.getList(sql, new Object[] {communityId,
				startTime, endTime }, RepairStatisticsHistory.class);
		return list;
	}

	/**
	 * 被投诉师傅的头部
	 */
	@Override
	public RepairStatisticsComplaints getComplaints(Integer communityId,long startTime,
			long endTime, Integer shopId, String sort) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.logo as avatar,s.shop_id , s.shop_name , u.nickname , u.user_id ,  "
				+ " COUNT(DISTINCT c.complaint_id,c.complaint_id) AS complaints_count_history ,"
				+ " COUNT(DISTINCT case when o.status = 'refuse' then o.order_id else NULL end) AS un_complaints_count , "
				+ " COUNT(DISTINCT case when o.status = 'renounce' then o.order_id else NULL end) AS abandon_count  , "
				+ " COUNT(DISTINCT case when o.order_type = 'bangbang' then o.order_id else NULL end) AS repair_count_history ," +
						" COUNT(DISTINCT case when o.order_type = 'other' then o.order_id else NULL end) AS repair_count_history_part"
				+ " FROM shops s "
				+ " LEFT  JOIN orders o ON s.emob_id = o.emob_id_shop  AND o.start_time >=? AND o.start_time <= ? " +
						"LEFT JOIN complaints c ON c.emob_id_to = o.emob_id_shop AND c.occur_time >=? AND c.occur_time <=? "
				+ " LEFT  JOIN users u ON o.emob_id_shop = u.emob_id    WHERE  s.community_id=? AND s.shop_id =? " +
						" AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend'  ";

		List<RepairStatisticsComplaints> list = this.getList(sql, new Object[] {
				startTime, endTime, startTime, endTime,communityId, shopId },
				RepairStatisticsComplaints.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 历史维修记录详情
	 */
	public Page<RepairStatisticsHistoryCenter> getHistoryMaintainListDetail(Integer communityId,
			long startTime, long endTime, Integer pageNum, Integer pageSize,
			Integer shopId, String sort) throws Exception {

		String sql = "SELECT o.order_type,o.status,s.shop_id , s.shop_name ,u.username, u.nickname , u.user_id ,u.avatar,s.logo,u.phone," +
				"u.user_unit,u.user_floor,u.room,o.start_time,o.end_time"
				+

				" FROM orders o  "
				+

				" RIGHT JOIN users u ON o.emob_id_user = u.emob_id RIGHT JOIN shops s "
				+

				" ON s.emob_id = o.emob_id_shop  "
				+

				" WHERE  s.community_id=? AND s.sort = ? AND s.shop_id = ? AND o.start_time >=? AND o.start_time <= ?  AND   s.status !='leave' AND s.status!='suspend'  AND s.status!='block' GROUP BY o.order_id";

		Page<RepairStatisticsHistoryCenter> page = this.getData4Page(sql,
				new Object[] {communityId, sort, shopId, startTime, endTime }, pageNum,
				pageSize, 4, RepairStatisticsHistoryCenter.class);
		return page;
	}

	@Override
	public Page<RepairStatisticsHistoryCenter> getHistoryCenterList(Integer communityId,
			Integer startTime, Integer endTime, Integer shopId,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = "";
		Page<RepairStatisticsHistoryCenter> page = this.getData4Page(sql,
				new Object[] {}, pageNum, pageSize, nvm,
				RepairStatisticsHistoryCenter.class);
		return page;
	}

	/**
	 * 历史维修记录
	 */
	@Override
	public Page<RepairStatisticsHistory> getHistoryMaintainList(Integer communityId,long startTime,
			long endTime, Integer pageNum, Integer pageSize, String sort)
			throws Exception {
		// TODO Auto-generated method stub

		
		 String sql =
		 "SELECT s.logo,s.shop_id,s.shop_name,s.phone,COUNT(DISTINCT o.order_id,o.order_id) AS repair_statistics_count,u.avatar FROM    "
		 + "  orders o   LEFT JOIN shops s ON s.emob_id = o.emob_id_shop   LEFT"
		 +
		 " JOIN users u ON s.emob_id = u.emob_id   "
		 +
		 " WHERE s.community_id=?  AND  s.sort=?   AND   s.status !='leave' AND s.status!='suspend'  AND s.status!='block' AND o.start_time >=? AND o.start_time <= ?   GROUP BY s.emob_id";

//		String sql = "SELECT a.logo,a.shop_id,a.shop_name,a.phone,COUNT(DISTINCT a.order_id,a.order_id) AS repair_statistics_count,COUNT(DISTINCT a.complaint_id,a.complaint_id) AS "
//				+ " complaints_count ,a.score,a.avatar from (SELECT s.logo,s.shop_id,s.shop_name,s.phone,o.order_id, c.complaint_id  ,ROUND(AVG(cm.score),0) AS score,u.avatar "
//				+ " FROM      orders o   LEFT JOIN shops s ON s.emob_id = o.emob_id_shop   "
//				+ "  LEFT JOIN users u ON s.emob_id = u.emob_id "
//				+ "  "
//				+ " WHERE  s.sort=?  AND s.status='normal' AND o.start_time >=? AND o.start_time <= ?   GROUP BY s.shop_id,o.order_id,c.complaint_id) a  GROUP BY a.shop_id";

		Page<RepairStatisticsHistory> page = this.getData4Page(sql,
				new Object[] {  communityId,sort, startTime, endTime },
				pageNum, pageSize, 4, RepairStatisticsHistory.class);
		return page;
	}
	/**
	 * 历史维修记录
	 */
	public List<RepairStatisticsHistory> getHistoryMaintainList2(Integer communityId,long startTime,
			long endTime, Integer pageNum, Integer pageSize, String sort)
			throws Exception {
		
		 String sql =
			 "SELECT s.logo,s.shop_id,s.shop_name,s.phone,COUNT(DISTINCT o.order_id,o.order_id) AS repair_statistics_count," +
			 "u.avatar," +
			 "COUNT(DISTINCT case when o.status = 'refuse' then o.order_id else NULL end) AS unsolved_order," +
			 "COUNT(DISTINCT case when o.status = 'renounce' then o.order_id else NULL end) AS abandon_count," +
			 "COUNT(DISTINCT case when o.status = 'ended' AND end_time>? AND end_time<? then o.order_id else NULL end)  as end_order," +
			 "SUM(o.order_price) AS order_pric FROM    "
			 + "   shops s  LEFT JOIN orders o  ON s.emob_id = o.emob_id_shop  AND o.start_time >=? AND o.start_time <= ?  LEFT "
			 +
			 " JOIN users u ON s.emob_id = u.emob_id   "
			 +
			 " WHERE  s.community_id=?  AND  s.sort=5   AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend'   GROUP BY s.emob_id  ORDER BY s.shop_id DESC";
		 List<RepairStatisticsHistory> list1 = this.getList(sql, new Object[] {
				 startTime, endTime,startTime, endTime ,communityId},
					RepairStatisticsHistory.class);
		
			String sql2="SELECT s.logo,s.shop_id,s.shop_name,s.phone, u.avatar,COUNT(DISTINCT c.complaint_id,c.complaint_id) AS complaints_count FROM  shops s  " +
					" LEFT JOIN  complaints c ON c.emob_id_to = s.emob_id  AND c.occur_time>=? AND  c.occur_time<=? LEFT "+
                  " JOIN users u ON s.emob_id = u.emob_id  WHERE s.community_id=? AND s.sort=5   AND   s.status !='leave' AND s.status!='suspend'  AND s.status!='block'     GROUP BY s.emob_id ORDER BY s.shop_id DESC";

			List<ComplaintsList> list2 = this.getList(sql2, new Object[] {
			startTime, endTime,communityId },
			ComplaintsList.class);
		    List<RepairStatisticsHistory> list3=new ArrayList<RepairStatisticsHistory>();
			for (int i = 0; i < list1.size(); i++) {
				RepairStatisticsHistory rs= list1.get(i);
				
				rs.setTousu(list2.get(i).getComplaintsCount());
				list3.add(rs);
				
			}			
			
		return list3;
	}

	
	@Override
	public Page<ComplaintsList> getComplaintsList(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum) throws Exception {

//		String sql = "SELECT s.logo,s.shop_id,s.shop_name,s.phone, "
//				+ " COUNT(DISTINCT c.complaint_id,c.complaint_id)  AS complaints_count,u.avatar FROM    "
//				+ "  shops s "
//				+ "   LEFT "
//				+ " JOIN complaints c ON c.emob_id_to = s.emob_id   LEFT"
//				+ " JOIN users u ON s.emob_id = u.emob_id  "
//				+ " WHERE  s.sort=5  AND s.status='normal' AND c.occur_time>=? AND c.occur_time<=?    GROUP BY s.emob_id";
		
		
	String sql="SELECT a.logo,a.shop_id,a.shop_name,a.phone,a.avatar,COUNT(DISTINCT a.complaint_id,a.complaint_id) AS complaints_count FROM (SELECT s.logo,s.shop_id,s.shop_name,s.phone,"+
				 " u.avatar,c.complaint_id FROM  shops s "+
				" LEFT JOIN  complaints c ON c.emob_id_to = s.emob_id  LEFT "+
				" JOIN users u ON s.emob_id = u.emob_id "+
				" WHERE s.community_id=? AND s.sort=5   AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend'   AND c.occur_time>=? AND c.occur_time<=?    GROUP BY c.complaint_id) a  GROUP BY a.shop_id ";

		Page<ComplaintsList> page = this.getData4Page(sql, new Object[] {communityId,
				startTimeInt, endTimeInt }, pageNum, 20, 4,
				ComplaintsList.class);
		return page;
	}

	@Override
	public Page<CommentsLists> getCommentsList(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum) throws Exception {

		String sql = "SELECT s.logo,s.shop_id,s.shop_name,s.phone, "
				+ " u.avatar,ROUND(AVG(c.score),0) AS score FROM    "
				+ "  shops s "
				+ "   LEFT "
				+ " JOIN  comments c ON c.emob_id_to = s.emob_id  LEFT"
				+ " JOIN users u ON s.emob_id = u.emob_id  "
				+ " WHERE  s.community_id=? AND  s.sort=5   AND   s.status !='leave' AND s.status!='block' AND s.status!='suspend' AND c.create_time>=? AND c.create_time<=?    GROUP BY s.emob_id";

		Page<CommentsLists> page = this.getData4Page(sql, new Object[] {communityId,
				startTimeInt, endTimeInt }, pageNum, 20, 4,
				CommentsLists.class);
		return page;
	}

	@Override
	public Page<ComplaintsDetail> getCommentsListDetail(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum,Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = " SELECT u.username, u.nickname , u.user_id ,u.avatar,"+
		" u.phone,u.user_unit,u.user_floor,u.room,c.detail,d.deduction_detail "+
	   " FROM  shops s  LEFT JOIN "+
		" complaints c ON c.emob_id_to=s.emob_id LEFT JOIN users u ON c.emob_id_from=u.emob_id LEFT JOIN deduction d ON "+
		" c.complaint_id=d.complaint_id "+
		" WHERE  s.community_id=? AND  s.shop_id = ? AND c.occur_time >=? AND c.occur_time <= ?  AND   s.status !='leave' AND s.status!='suspend'  AND s.status!='block' GROUP BY c.complaint_id ";

		Page<ComplaintsDetail> page = this.getData4Page(sql,
		new Object[] { communityId,shopId, startTimeInt, endTimeInt }, pageNum,
		20, 4, ComplaintsDetail.class);
          return page;
	}

	@Override
	public Page<Subpackage> getSubpackage(Integer communityId,
			Integer startTimeInt, Integer endTimeInt, Integer pageNum) throws Exception {
		String sql =" SELECT  COUNT(o.order_id) AS part_num,o.order_detail AS part_project FROM orders o WHERE o.community_id=? AND " +
				" o.order_type='other' AND o.start_time >=? AND o.start_time <=? " +
				"  GROUP BY o.order_detail";
		Page<Subpackage> page = this.getData4Page(sql,
		new Object[] { communityId, startTimeInt, endTimeInt }, pageNum,
		20, 4, Subpackage.class);
          return page;
	}

}
