package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.LifePhoto;
import com.xj.dao.LifePhotoDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("lifePhotoDao")
public class LifePhotoDaoImpl extends MyBaseDaoImpl implements LifePhotoDao {

	@Override
	public Integer addLifePhoto(LifePhoto lifePhoto) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(lifePhoto, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateLifePhoto(LifePhoto lifePhoto) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE life_photo SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(lifePhoto);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE life_photo_id = ? ";
		list.add(lifePhoto.getLifePhotoId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<LifePhoto> getLifephotos(Integer lifeCircleId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT life_photo_id , photo_url , life_circle_id , create_time FROM life_photo WHERE life_circle_id = ? ";
		List<LifePhoto> list = this.getList(sql, new Object[]{lifeCircleId}, LifePhoto.class);
		return list;
	}

}
