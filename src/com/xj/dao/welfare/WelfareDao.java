package com.xj.dao.welfare;

import java.util.List;

import com.xj.bean.welfare.Welfare;
import com.xj.dao.MyBaseDao;

/**
 * 福利数据库访问接口
 * @author 王利东
 * 2015-09-16
 */
public interface WelfareDao extends MyBaseDao {

	/**
	 * 福利状态：下架
	 */
	static final String STATUS_OFFLINE = "offline";
	/**
	 * 福利状态：上线（进行中）
	 */
	static final String STATUS_ONGOING = "ongoing";
	/**
	 * 福利状态：成功
	 */
	static final String STATUS_SUCCESS = "success";
	/**
	 * 福利状态：失败
	 */
	static final String STATUS_FAILURE = "failure";
	/**
	 * 福利状态：删除
	 */
	static final String STATUS_DELETE = "delete";

	/**
	 * 添加福利
	 * @param welfare
	 * @return
	 * @throws Exception
	 */
	Integer addWelfare(Welfare welfare) throws Exception;

	/**
	 * 更新福利信息
	 * @param welfare
	 * @return
	 * @throws Exception
	 */
	boolean updateWelfare(Welfare welfare) throws Exception;

	/**
	 * 获取小区的福利信息，结果排序:进行中的在前，结束时间晚的在前
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	List<Welfare> getWelfares(Integer communityId) throws Exception;

	/**
	 * 获取福利
	 * @param welfareId
	 * @return
	 */
	Welfare getWelfare(Integer welfareId) throws Exception;

	/**
	 * 删除福利
	 * @param welfareId
	 * @return
	 */
	boolean deleteWelfare(Integer welfareId);

	/**
	 * 上架福利
	 * 如果小区内已经有上架的福利了，或者福利的结束时间小于当前，将返回false
	 * @param communityId
	 * @param welfareId
	 * @return
	 */
	String online(Integer communityId, Integer welfareId);

	/**
	 * 下架福利
	 * @param welfareId
	 * @return
	 */
	boolean offline(Integer welfareId);

	/**
	 * 失败福利
	 * @param welfareId
	 * @param reason
	 * @return
	 */
	boolean fail(Integer welfareId, String reason);

	/**
	 * 成功福利
	 * @param welfareId
	 * @param reason
	 * @return
	 */
	boolean succeed(Integer welfareId);
}