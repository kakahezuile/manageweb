package com.xj.dao;

import java.util.List;

import com.xj.bean.Comments;
import com.xj.bean.CommentsWithUser;
import com.xj.bean.Page;
import com.xj.bean.ShopsComments;

/**
 * 评论dao
 * @author Administrator
 *
 */
public interface CommentsDao extends MyBaseDao {
	/**
	 * 添加评论信息
	 * @param commentsBean
	 * @return
	 */
    public int addComments(Comments commentsBean) throws Exception;
    
    /**
     * 获取评论集合
     * @return list
     * @throws Exception
     */
    public List<Comments> getCommentsList(String emobIdTo) throws Exception;
    /**
     * 分页查询 - 根据被评论者emobId获取评论内容集合
     * @param emobIdTo
     * @param pageNum
     * @param pageSize
     * @param nvm
     * @return
     * @throws Exception
     */
    public Page<Comments> getCommentsListWithPage(String emobIdTo , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public Page<CommentsWithUser> getCommentsListWithPageBySize(String emobIdTo , Integer pageNum , Integer pageSize , Integer nvm , Integer type) throws Exception;
    
    /**
     * 根据商家id获取商家
     */
    public ShopsComments getShopsCommentsByEmobId(String emobId) throws Exception;
}
