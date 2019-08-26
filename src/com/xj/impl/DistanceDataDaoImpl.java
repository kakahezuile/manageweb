package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.DistanceData;
import com.xj.dao.DistanceDataDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("distanceDataDao")
public class DistanceDataDaoImpl extends MyBaseDaoImpl implements DistanceDataDao{

	@Override
	public Integer addDistanceData(DistanceData distanceData) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(distanceData, key);
		return key.getId();
	}

	@Override
	public DistanceData getDistance(Integer level) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT id , create_time , update_time , level , distance , default_distance FROM distance_data WHERE level = ? ";
		List<DistanceData> list = this.getList(sql, new Object[]{level}, DistanceData.class);
		return list!= null && list.size() > 0 ? list.get(0) : null;
	}

}
