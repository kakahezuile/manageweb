package com.xj.dao;

import java.util.List;

import com.xj.bean.City;


public interface CityDao extends MyBaseDao{
	/**
	 * 添加城市
	 * @param city
	 * @return
	 * @throws Exception
	 */
	public Integer insert(City city) throws Exception;
	
	/**
	 * 根据cityId 获取城市信息
	 * @param cityId
	 * @return
	 * @throws Exception
	 */
	public City getCity(Integer cityId) throws Exception;
	
	/**
	 * 获取城市列表
	 * @return
	 * @throws Exception
	 */
	public List<City> getListCity() throws Exception;
	
	/**
	 * 根据城市名称查询城市信息
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	public City getCityByName(String cityName) throws Exception;
}
