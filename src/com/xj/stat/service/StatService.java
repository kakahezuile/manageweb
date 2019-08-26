package com.xj.stat.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.bean.resource.TopUser;

/**
 *@author lence
 *@date  2015年7月9日上午1:02:08
 *
 */
public interface StatService {
	
	
	/**
	 * 
	 * @param startday
	 * @param endDay
	 * @return
	 * @throws Exception
	 */
	public List<String> statUsers2(String startday,String endDay,Integer communityId) throws Exception;
	
	/**
	 * 
	 * @param startday
	 * @param endDay
	 * @return
	 * @throws Exception
	 */
	public List<Integer> statUsers(String startday,String endDay,Integer communityId) throws Exception;
	
	/**
	 * 统计指定社区，指定模块，指定时间段的点击数据
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 */
	public  Map<String, Integer> getData(int communityid, int modules,int start,int end);
	
	/**
	 * 获取指定社区指定时间的点击数据
	 * @param communityid
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Integer> getAllModulesCount(int communityid, int start,int end);
	
	
	
	public Integer getCount(Map<String, Integer> map,int communityid);

	/**
	 * 获取ip
	 * @return
	 */
	public List<String> getuserIP();

	public TopUser statisticsUserNum(Set<String> set,BeanUtil beanUtil)throws Exception;
	
	
	
	public Set<String> thisDay(Integer communityId)throws Exception;
	
	public List<String> thisDay2(Integer communityId)throws Exception;
	
	public void getClickMobule(Integer startday,Integer endDay,Integer communityId)throws Exception;

	public List<ClickMobule>  statisticsModuleNum(BeanUtil beanUtil);

	/**
	 * 获取城市
	 * @return
	 */
	public Map<String, Integer> getCities();

}
