package com.xj.dao;

import java.util.List;

import com.xj.bean.DetailVO;
import com.xj.bean.LifeCircleDetail;
import com.xj.bean.LifeCircleNumer;

public interface LifeCircleDetailDao extends MyBaseDao{
	/**
	 * 添加生活圈评论内容
	 * @param lifeCircleDetail
	 * @return
	 * @throws Exception
	 */
	public Integer addLifeCircleDetail(LifeCircleDetail lifeCircleDetail) throws Exception;
	 /**
	  * 修改生活圈评论内容
	  * @param lifeCircleDetail
	  * @return
	  * @throws Exception
	  */
	public boolean updateLifeCircleDetail(LifeCircleDetail lifeCircleDetail) throws Exception;
	
	/**
	 * 获取生活圈评论列表 根据生活圈id
	 * @param lifeCircleId
	 * @return
	 * @throws Exception
	 */
	public List<LifeCircleDetail> getLifeCircleDetails(Integer lifeCircleId) throws Exception;
	
	/**
	 * 生活圈评论+1
	 * @param lifeCircleId
	 * @return
	 * @throws Exception
	 */
	public boolean updateLifeCircleDetail(Integer lifeCircleId) throws Exception;
	
	/**
	 * 根据生活圈id  来自A 对B的生活圈内容
	 * @param lifeCircleId
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public LifeCircleDetail getLifeCircleDetail(Integer lifeCircleId , String from , String to) throws Exception;
	
	public List<DetailVO> getDetailVO(String to , Integer createTime) throws Exception;
	
	public LifeCircleNumer getLifeCircleNumber(String to , Integer createTime) throws Exception;
	
	public List<Integer> getLifeCircleId(Integer communityId , String text ) throws Exception;
}
