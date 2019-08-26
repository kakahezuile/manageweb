package com.xj.stat.service;

import java.util.List;

import com.xj.bean.Page;
import com.xj.bean.PushMessage;
import com.xj.bean.StatPushMessageBean;
import com.xj.stat.po.UserVo;

public interface StatPushMessageService {

	/**
	 * 统计发送消息
	 * @param communityId
	 * @param cmdCode
	 * @param page 第几页
	 * @return
	 * @throws Exception 
	 */
	List<StatPushMessageBean> notificationPushMessage(Integer communityId, Integer cmdCode, int page) throws Exception;

	/**
	 * 获取数据库表中的通知信息
	 * @param communityId
	 * @param page
	 * @param pageSzie
	 * @return
	 */
	Page<StatPushMessageBean> getAllBulletinByCommunityId(Integer communityId, int page, int pageSzie,Integer  cmdCode);
	
	public Page<UserVo> getBulletinsDetailsByMessageId(Integer communityId ,Long messageId,Integer page,Integer pageSize);

	/**
	 * 统计推送信息，用户
	 * @param communityId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Page<UserVo> getPushMessagesUsers(Integer communityId, int page, int pageSize,Integer  cmdCode);

	Page<PushMessage> getPushUserDetail(Integer communityId, String emobId, int page, int pageSize,Integer  cmdCode);
}