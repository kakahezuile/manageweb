package com.xj.dao;

import java.util.List;

import com.xj.bean.Bulletin;
import com.xj.bean.BulletinUser;
import com.xj.bean.Page;

public interface BullentinDao extends MyBaseDao {
	/**
	 * 添加已发通知
	 * @param bulletin
	 * @return
	 * @throws Exception
	 */
	public Integer insert(Bulletin bulletin) throws Exception;
	
	public  BulletinUser getBulletin(Integer bulletinId) throws Exception;
	public List<Bulletin> getBullentinList(Integer communityId) throws Exception;
	
	public Page<BulletinUser> getBullentinListWithPage(Integer communityId , Integer pageNum ,Integer pageSize , Integer nvm) throws Exception;
	
	public Page<BulletinUser> getTimeBullentinListWithPage(Integer communityId , Integer pageNum ,Integer pageSize , Integer nvm,Integer startTime,Integer endTimeInt) throws Exception;

	/**
	 * 根据messageId获取通知
	 * @param key
	 * @return
	 */
	public Bulletin getBullentinByMessageId(String key);
}
