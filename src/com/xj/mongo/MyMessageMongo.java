package com.xj.mongo;

import java.util.List;
import java.util.Map;

import com.xj.bean.Page;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;

public interface MyMessageMongo {

	List<PushStatistics> statMessageByCommunityAndCode(Integer communityId, Integer cmdCode);

	List<PushStatistics> getConsumeTimeByMessageId(Long messageId,Integer  cmdCode);
	
	/**
	 * 通过messageid分页查询数据
	 * @param messageId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	Map<String,Object> getBulletinsDetailsByMessageId(Integer communityId,  Long messageId, int start, Integer pageSize);

	/**
	 * 获取小区，被发送过通知的用户
	 * @param communityId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<PushStatistics> getUsersByCommunityId(Integer communityId, int page, int pageSize,Integer  cmdCode);

	/**
	 * 获取用户被发送的通知
	 * @param communityId
	 * @param emobId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Page<PushMessage> getBulletinsDetailsEmobId(Integer communityId, String emobId, int page, int pageSize,Integer  cmdCode);
}
