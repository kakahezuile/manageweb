package com.xj.dao;

import java.util.List;

import com.xj.bean.LifeCircle;
import com.xj.bean.Users;
import com.xj.stat.po.TryOut;

/**
 *@author lence
 *@date  2015年7月9日上午1:07:43
 *
 */
public interface TryOutDao {
	List<TryOut> getAllTryOut();

	List<String> getTryOutByEmobIds(Object[] array);
	
	public List<String> selectTryOut(Integer communityId);

	/**
	 * 获取水军列表
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Users> getTryOuts(Integer communityId, Integer pageNum,
			Integer pageSize);

	/**
	 * 获取水军发的生活圈
	 * @param communityId
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<LifeCircle> getLifeCircles( String emobId,
			Integer pageNum, Integer pageSize);

	/**
	 * 添加一条水军数据
	 * @param to
	 * @return 
	 */
	Integer addTryOut(TryOut to);

	/**
	 * 获取水军总数
	 * @param communityId
	 * @return
	 */
	Integer getTryOutNum(Integer communityId);

	/**
	 * 获取用户生活圈的数量
	 * @param emobId
	 * @return
	 */
	int getLifeCirclesSum(String emobId);

	/**
	 * 根据emobId获取用户
	 * @param emobId
	 * @return
	 */
	Users getNavyByEmobId(String emobId);

	/**
	 * 收索水军
	 * @param query
	 * @return
	 */
	List<Users> searchNavy(String query);
}
