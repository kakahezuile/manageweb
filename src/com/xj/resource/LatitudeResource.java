package com.xj.resource;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Communities;
import com.xj.bean.DistanceData;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.DistanceDataDao;
import com.xj.httpclient.utils.LatitudeUtils;

@Path("/communities/latitude")
@Component
@Scope("prototype")
public class LatitudeResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private CommunitiesDao communitiesDao;
	
	@Autowired
	private DistanceDataDao distanceDataDao;
	
	@GET
	public String getCommunities(@QueryParam("longitude") Double longitude , @QueryParam("latitude") Double latitude){  // 获取当前范围内的所有小区
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Communities> list = communitiesDao.getCommunityList();
			Iterator<Communities> iterator = list.iterator();
			List<Communities> listResult = new ArrayList<Communities>();
			DistanceData data = distanceDataDao.getDistance(1);
			int distanceSum = 0;
			while(iterator.hasNext()){
				Communities communities = iterator.next();
				Float communityLongitude = communities.getLongitude();
				Float communityLatitude = communities.getLatitude();
				Double distance = LatitudeUtils.LantitudeLongitudeDist(longitude, latitude, communityLongitude, communityLatitude);
			
				Integer distanceValue = Integer.parseInt(data.getDistance());
				Double defaultDistance = Double.parseDouble(data.getDefaultDistance());
				if(distance <= distanceValue){
					listResult.add(communities);
				}
				if(distance > defaultDistance){
					distanceSum++;
				}
			}
			if(list != null && list.size() > 0 && list.size() == distanceSum){
				resultStatusBean.setStatus("yes");
				listResult.add(list.get(0));
				resultStatusBean.setInfo(listResult);
			}else{
				if(listResult != null && listResult.size() > 0){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setInfo(listResult);
				}
			}
			
//			else{
//				Communities communities = communitiesDao.getCommunities(1);
//				listResult.add(communities);
//				resultStatusBean.setStatus("yes");
//				resultStatusBean.setInfo(listResult);
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
