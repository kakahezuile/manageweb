package com.xj.stat.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *@author lence
 *@date  2015年7月9日下午4:57:11
 *筛选工具
 */
public class StatScreening {

	/**
	 * @param enty1 set集合
	 * @param enty2 需要被筛选的map集
	 * @return 筛选后的集合
	 */
	public static Set<String> screening(Set<String> enty1,Map<String,String> map){
		Set<String> set = new  HashSet<String>();
		set.addAll(enty1);
		
		for (String str : enty1) {
			if(!"yes".equals(map.get(str))){
				set.remove(str);
			}
		}
		return set;
	}
	
}
