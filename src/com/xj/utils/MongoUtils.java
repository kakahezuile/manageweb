package com.xj.utils;

import org.springframework.stereotype.Component;

/**
 *@author lence
 *@date  2015年7月9日下午5:19:47
 *调用mongo接口
 */
@Component
public class MongoUtils {

	public static String getEventsDetailsDailyInTime(int communityid,int modules,Integer start,Integer end){
		String url  = "http://115.28.73.37:9090/api/V1/communities/"+communityid+"/events_details_daily/statistics/"+modules+"/"+start+"/"+end;
		String httpUrl = HttpUtil.httpUrl(url);
		return httpUrl;
	}
	
	/**
	 * 各个模块的点击数据
	 * @param communityid
	 * @param modules
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getModulesInTime(int communityid,Integer start,Integer end){
		String url  = "http://115.28.73.37:9090/api/V1/communities/"+communityid+"/modules/statistics/"+start+"/"+end;
		String httpUrl = HttpUtil.httpUrl(url);
		System.out.println(url);
		return httpUrl;
	}
	
	
	/**
	 * 活跃用户的今日点击数据
	 * @param communityid
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getActiveUserCountInTime(int communityid,Integer start,Integer end){
		String url  = "http://115.28.73.37:9090/api/V1/communities/"+communityid+"/active_user_count/statistics/"+start+"/"+end;
		String httpUrl = HttpUtil.httpUrl(url);
		return httpUrl;
	}
	
}
