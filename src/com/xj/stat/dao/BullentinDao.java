package com.xj.stat.dao;

import java.util.List;

import com.xj.bean.Page;
import com.xj.bean.StatPushMessageBean;
import com.xj.stat.po.Bulletin;

public interface BullentinDao {

	/**
	 * 获取发送的全部的通知
	 * @param communityId
	 * @param page
	 * @param pageSzie
	 * @return
	 */
	List<Bulletin> getAllBulletinByCommunityId(Integer communityId,
			Page<StatPushMessageBean> pb);

	int getAllCountByCommunityId(Integer communityId);

	


}
