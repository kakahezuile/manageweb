package com.xj.dao;

import java.util.List;

import com.xj.bean.BlackList;
import com.xj.bean.CommunityId;
import com.xj.bean.IdCount;
import com.xj.bean.LifeCircle;
import com.xj.bean.LifeCircleDetailList;
import com.xj.bean.LifeCircleGet;
import com.xj.bean.LifeCircleGetAll;
import com.xj.bean.LifeCircleNumer;
import com.xj.bean.LifeCircleSelect;
import com.xj.bean.LifeCircleTop;
import com.xj.bean.LifeCircleVO;
import com.xj.bean.LifePhoto;
import com.xj.bean.LifeSensitive;
import com.xj.bean.Page;
import com.xj.bean.ScanningTime;
import com.xj.bean.life.Favorite;

public interface LifeCircleDao extends MyBaseDao{
	/**
	 * 添加生活圈
	 * @param lifeCircle
	 * @return
	 * @throws Exception
	 */
	public Integer addLifeCircle(LifeCircle lifeCircle) throws Exception;
	
	/**
	 * 修改生活圈内容
	 * @param lifeCircle
	 * @return
	 * @throws Exception
	 */
	public boolean updateLifeCircle(LifeCircle lifeCircle) throws Exception;
	/**
	 * 分页查询 - 根据小区id 用户环信id获取生活圈列表
	 * @param communityId
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<LifeCircle> getLifeCircles(Integer communityId , String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	
	
	
	public Page<LifeCircle> getLifeCircles(Integer communityId , String emobId , Integer pageNum , Integer pageSize , Integer nvm,int appVersion) throws Exception;
	/**
	 * 根据生活圈id 修改生活圈 内容
	 * @param lifeCircleId
	 * @return
	 * @throws Exception
	 */
	public boolean updateLifeCircle(Integer lifeCircleId) throws Exception;
	
	/**
	 * 分页查询 - 根据环信id 获取生活圈列表
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<LifeCircleVO> getLifeCircleVos(String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	/**
	 * 修改
	 * @param lifeCircleId
	 * @return
	 * @throws Exception
	 */
	public boolean updateContentSum(Integer lifeCircleId) throws Exception;
	
	public LifeCircle getLifeCircle(Integer lifeCircleId) throws Exception;
	
//	public Integer getLifeCircleCount(Integer communityId , Integer createTime) throws Exception;
	
	public LifeCircleNumer getLifeCircleNumber(Integer communityId , Integer createTime) throws Exception;
	
	public LifeCircleNumer getLifeCircleNumber(String emobId) throws Exception;
	
	public LifeCircleTop getLifeCircleTop(Integer communityId,
			Integer startTimeInt, Integer endTimeInt)throws Exception;

	public List<LifeCircleTop> getLifeCircleList(Integer communityId,
			Integer startTimeInt, Integer endTimeInt,List<Integer> sqlTime)throws Exception;

	public List<LifeCircleSelect> selectLifeCircleList(Integer communityId,
			Integer startTimeInt, Integer endTimeInt,String type)throws Exception;
	
	public List<Integer> getLifeCircleId(Integer communityId , String text , List<BlackList> list) throws Exception;
	
	public Page<LifeCircle> getLifeCircleByText(String text , Integer pageNum , Integer pageSize , Integer
			 nvm) throws Exception;
	
	public List<LifeCircleDetailList> getLifeCircleDetail(String emob_id_to)throws Exception;

	public LifeCircleSelect getLifeCireDelit(String life_circle_id)throws Exception;

	public ScanningTime selectLifeCirrleDetailId()throws Exception;

	public List<LifeSensitive> selectLifeCircleListTime(Integer lifeCirrleDetailId)throws Exception;

	public Integer addLifeSensitive(LifeSensitive lifeSensitive)throws Exception;

	public Page<LifeSensitive> getSensitive(Integer communityId,Integer pageNum, Integer pageSize)throws Exception;

	public boolean pingbi(Integer facilities)throws Exception;

	public boolean upStatus(Integer id,String status)throws Exception;

	public Integer addLifePhoto(LifePhoto lifePhoto)throws Exception;

	public Page<LifeCircleGetAll> getLifeCircleList(Integer communityId,Integer pageNum, Integer pageSize)throws Exception;

	public Integer addFavorite(Favorite favorite)throws Exception;
	
	public Integer delFavorite(Integer favorite_id)throws Exception;

	public Page<LifeCircleGet> getFavoriteLifeCire(Integer pageNum,
			Integer pageSize)throws Exception;

	public Integer delectLifePhoto(Integer life_photo_id)throws Exception;
	
	public List<CommunityId> getExisting(Integer lifeCircleId)throws Exception;
	 
	//获取生活圈更新消息
	public List<IdCount> getLifeCircleUpdateCount(Integer communityId,Integer lastQuitTime) throws Exception;
}
