package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.ComplaintTop;
import com.xj.bean.ComplaintsUserMsg;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.ShopUserMsg;
import com.xj.dao.OrderComplainsDao;

@Repository("orderComplaintsDao")
public class OrderComplaintsDaoImpl extends MyBaseDaoImpl implements OrderComplainsDao {

	@Override
	public ShopUserMsg getShopUserMsg(String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select cs.occur_time,s.logo, u.user_id as sum_order , s.shop_name as nickname , s.phone  , u.avatar,COUNT(DISTINCT cs.complaint_id,cs.complaint_id) as complaint_num " +
					 " , sum(case when c.score > 3 then 1 else 0 end) as haoping " +
					 " , sum(case when c.score < 3 then 1 else 0 end) as chaping " +
					 " , sum(case when c.score = 3 then 1 else 0 end) as zhongping " +
					 " from users u left join shops s on s.emob_id=u.emob_id left join comments c on u.emob_id = c.emob_id_to " +
					 "left join complaints cs on u.emob_id =cs.emob_id_to  where u.emob_id = ?";
		List<ShopUserMsg> list = this.getList(sql, new String[]{emobId}, ShopUserMsg.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<ComplaintsUserMsg> getComplaintsUserMsg(Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT c.complaint_id , c.emob_id_from , c.order_id ,c.emob_id_to , c.title , c.detail ," +
				" u.nickname ,u.user_floor,u.user_unit, u.room , u.avatar , c.occur_time,us.nickname AS shop_name" +
				" FROM complaints c LEFT JOIN users u ON c.emob_id_from = u.emob_id LEFT JOIN users us ON us.emob_id=c.emob_id_to ORDER BY c.complaint_id DESC ";
		Page<ComplaintsUserMsg> page = this.getData4Page(sql,new Object[]{}, pageNum, pageSize, nvm, ComplaintsUserMsg.class);
		return page;
	}

	@Override
	public Integer getUserToShopCount(String emobId, String shopEmobId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) as count_sum FROM orders o WHERE emob_id_user = ? and emob_id_shop = ? ";
		Number result = this.getValue(sql, Number.class, new Object[]{emobId,shopEmobId});
		return result != null ? result.intValue() : 0;
	}

	@Override
	public Orders getThisOrderLastOrder(Integer orderId , String emobIdUser ,String emobIdShop) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from orders where order_id = (SELECT max(order_id) FROM orders o where emob_id_user = ? and emob_id_shop = ? and order_id < ?) ";
		List<Orders> list = this.getList(sql, new Object[]{emobIdUser , emobIdShop , orderId}, Orders.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public ComplaintTop getComplaints(Integer complaint_id) throws Exception {
		String sql = "SELECT u.nickname AS user_name,u.phone,s.shop_name,c.detail,a.nickname,c.emob_id_from,c.emob_id_to,c.occur_time  FROM complaints c LEFT JOIN users u ON u.emob_id =c.emob_id_from " +
				"LEFT JOIN shops s ON c.emob_id_to=s.emob_id LEFT JOIN admin a ON c.emob_id_agent=a.emob_id WHERE c.complaint_id=? ";
		List<ComplaintTop> list = this.getList(sql, new Object[]{complaint_id}, ComplaintTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<ComplaintsUserMsg> getComplaintsUser(String userName,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String name="%"+userName+"%";
		String sql = "SELECT c.complaint_id , c.emob_id_from , c.order_id ,c.emob_id_to , c.title , c.detail , u.nickname , u.room , u.avatar , c.occur_time FROM complaints c left join users u on c.emob_id_from = u.emob_id WHERE u.nickname  like ? order by c.complaint_id DESC ";
		Page<ComplaintsUserMsg> page = this.getData4Page(sql,new Object[]{name}, pageNum, pageSize, nvm, ComplaintsUserMsg.class);
		return page;
	}

	@Override
	public Page<ComplaintsUserMsg> getComplaintsUserMsg(Integer communityId,String sort,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT c.complaint_id , c.emob_id_from , c.order_id ,c.emob_id_to , c.title , c.detail , u.nickname " +
				
					 " ,u.user_floor,u.user_unit, u.room , u.avatar , c.occur_time,s.shop_name AS shop_name FROM complaints c left join users u " +
					 
					 " on c.emob_id_from = u.emob_id left join shops s on c.emob_id_to = s.emob_id where c.community_id=? AND s.sort = ? order by c.complaint_id DESC ";
		Page<ComplaintsUserMsg> page = this.getData4Page(sql,new Object[]{communityId,sort}, pageNum, pageSize, nvm, ComplaintsUserMsg.class);
		return page;
	}

}
