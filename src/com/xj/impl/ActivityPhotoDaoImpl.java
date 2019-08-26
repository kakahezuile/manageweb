package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.ActivityPhoto;
import com.xj.dao.ActivityPhotoDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("activityPhotoDao")
public class ActivityPhotoDaoImpl extends MyBaseDaoImpl implements ActivityPhotoDao {

	@Override
	public Integer addPhoto(ActivityPhoto activityPhoto) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(activityPhoto, key);
		return key.getId();
	}

	@Override
	public List<ActivityPhoto> getPhotoList(Integer activityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM activity_photo WHERE activity_id = ? ";
		List<ActivityPhoto> list = this.getList(sql, new Object[]{activityId}, ActivityPhoto.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updatePhoto(ActivityPhoto activityPhoto) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE shops SET ";
		List<Object> list = new ArrayList<Object>();
		Object objResult[] = DaoUtils.reflect(activityPhoto);
		if(objResult[1] != null && ((List<Object>)objResult[1]).size() > 0){
			sql += (String) objResult[0];
			list = (List<Object>)objResult[1];
		}else{
			return false;
		}
		sql+= " WHERE photo_id = ? ";
		list.add(activityPhoto.getPhotoId());
		int result = this.updateData(sql, list,null);
		return result > 0;
		
	}

}
