package com.xj.resource;



import java.util.List;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.City;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.CityDao;

@Component
@Scope("prototype")
@Path("/cities")
public class CityResource { // 城市i列表
	
	private Gson gson = new Gson();
	
	@Autowired 
	private CityDao cityDao;
	
	@GET
	
	public String getCityList(@QueryParam("q") String cityName){
		List<City> list = null;
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		
		try {
			if(cityName != null){
				City city = null;
				city = cityDao.getCityByName(cityName);
				if(city == null){
					resultStatusBean.setMessage("没有找到指定城市");
				}
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(city);
			}else{
				list = cityDao.getListCity();
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			resultStatusBean.setMessage("获取城市列表时发生错误");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/getCityByName/{cityName}")
	public String getCityByName(@PathParam("cityName") String cityName){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		City city = null;
		try {
			city = cityDao.getCityByName(cityName);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(city);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			resultStatusBean.setMessage("查询城市信息时发生错误了");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
