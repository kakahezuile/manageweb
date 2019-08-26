package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.resource.WelfareStatics;

public interface WelfareStaticsDao {

	List<WelfareStatics> statWelfareOrders(CurrentTimeRange currentTimeRange);

	WelfareStatics statWelfareOrders(BeanUtil beanUtil);

	/**
	 * 分页获取所有福利详情
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<WelfareStatics> getAllWelfareStatics(Integer communityId, Integer pageNum, Integer pageSize);

	/**
	 * 获取福利购买详情
	 * @param welfareId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<WelfareStatics> getWelfareOrdersDetail(Integer welfareId, Integer pageNum, Integer pageSize);

	/**
	 * 获取福利详情
	 * @param welfareId
	 * @return
	 */
	WelfareStatics getWelfareInfo(Integer welfareId);

	/**
	 * 根据用户的emobID获取用户昵称 用户名 真实住址
	 * @param users
	 * @return
	 */
	List<WelfareStatics> getClickUserDetailDay(Object[] users);

	List<WelfareStatics> getwelfareBuys(Integer communityId, Integer start, Integer end, Integer pageNum, Integer pageSize);
}