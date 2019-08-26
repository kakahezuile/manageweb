package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.ShopsCommentsHistory;
import com.xj.bean.UserComments;
import com.xj.dao.ShopsCommentsDao;

@Repository("shopsCommentsDao")
public class ShopsCommentsDaoImpl extends MyBaseDaoImpl implements ShopsCommentsDao {

	@Override
	public Page<UserComments> getUserComments(Integer communityId , String emobId , Integer pageNum , Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select u.nickname , u.avatar , u.emob_id , c.comment_id , ifnull(c.score,0) as score , c.create_time , c.content from comments c left join users u on c.emob_id_from = u.emob_id where c.emob_id_to = ? and u.community_id = ? group by c.comment_id order by c.create_time desc ";
		Page<UserComments> list = this.getData4Page(sql, new Object[]{emobId,communityId}, pageNum, pageSize, 10, UserComments.class);
		return list;
	}

	@Override
	public ShopsCommentsHistory getShopsCommentsHistory(Integer communityId , String emobId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT s.shop_name , s.emob_id , s.logo , ifnull(round(avg(c.score),0),0) as score FROM shops s left join comments c on s.emob_id = c.emob_id_to where s.emob_id = ? and s.community_id = ?";
		List<ShopsCommentsHistory> list = this.getList(sql, new Object[]{emobId,communityId}, ShopsCommentsHistory.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
