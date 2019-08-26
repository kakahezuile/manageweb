package com.xj.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xj.bean.Comments;
import com.xj.bean.CommentsWithUser;
import com.xj.bean.Page;
import com.xj.bean.ShopsComments;
import com.xj.dao.CommentsDao;
import com.xj.httpclient.vo.MyReturnKey;

/**
 * 
 */
@Repository("commentsDao")
public class CommentsDaoImpl extends MyBaseDaoImpl implements CommentsDao {

    Logger logger = Logger.getLogger(CommentsDaoImpl.class);

    public int addComments(Comments commentsBean) throws Exception {
    	MyReturnKey myKey = new MyReturnKey();
    	this.save(commentsBean, myKey);
    	return myKey.getId();
    }

	@Override
	public List<Comments> getCommentsList(String emobIdTo) throws Exception {
		return this.getList("SELECT * FROM comments WHERE emob_id_to = ? ", new String[]{emobIdTo}, Comments.class);
	}

	@Override
	public Page<Comments> getCommentsListWithPage(String emobIdTo , Integer pageNum , Integer pageSize , Integer nvm) throws Exception {
		String sql = "SELECT * FROM comments WHERE emob_id_to = ? order by comment_id desc ";
		return this.getData4Page(sql, new String[]{emobIdTo}, pageNum, pageSize, nvm, Comments.class);
	}

	@Override
	public ShopsComments getShopsCommentsByEmobId(String emobId) throws Exception {
		String sql = " SELECT sum(c.score) / count(c.emob_id_to) as score , sum(case when c.score > 3 then 1 else 0 end) as positive_comment , " +
			 " sum(case when c.score = 3 then 1 else 0 end) as moderate_comment , " +
			 " sum(case when c.score < 3 then 1 else 0 end) as negtive_comment , s.shop_name , s.shop_id , s.logo as avatar " +
			 " FROM shops s left join comments c on s.emob_id = c.emob_id_to where s.emob_id = ?  ";
		List<ShopsComments> list = this.getList(sql,new Object[]{emobId},ShopsComments.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<CommentsWithUser> getCommentsListWithPageBySize(String emobIdTo,
			Integer pageNum, Integer pageSize, Integer nvm, Integer type)
			throws Exception {
		String sql = "SELECT cs.comment_id , cs.emob_id_from , cs.emob_id_to , cs.score , " +
					 " cs.content , cs.create_time , u.avatar , u.user_floor , u.user_unit , u.room , u.nickname "+
					 " FROM comments cs , users u WHERE cs.emob_id_from = u.emob_id AND cs.emob_id_to = ? ";
		if(type == 1){
			sql += " AND cs.score > 3 ";
		}else if(type == 2){
			sql += " AND cs.score = 3 ";
		}else{
			sql += " AND cs.score < 3 ";
		}
		sql += " order by cs.comment_id desc ";
		return this.getData4Page(sql, new String[]{emobIdTo}, pageNum, pageSize, nvm, CommentsWithUser.class);
	}
}
