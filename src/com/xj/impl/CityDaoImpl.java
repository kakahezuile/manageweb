package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.City;

import com.xj.dao.CityDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("cityDao")
public class CityDaoImpl extends MyBaseDaoImpl implements CityDao {

	@Override
	public Integer insert(City city) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey myReturnKey = new MyReturnKey();
		this.save(city, myReturnKey);
		Integer resultId = myReturnKey.getId();
		return resultId;
	}

	@Override
	public City getCity(Integer cityId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM city WHERE city_id = ?";
		List<City> list = this.getList(sql, new Integer[]{cityId}, City.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<City> getListCity() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM city order by city_letter , city ";
		List<City> list = this.getList(sql , null , City.class);
		return list;
	}

	@Override
	public City getCityByName(String cityName) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM city WHERE city = ?";
		List<City> list = this.getList(sql, new String[]{cityName}, City.class);
		return list != null && list.size() > 0 ? list.get(0) : null ;
	}

}
