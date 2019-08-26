package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.PublicizePhotos;
import com.xj.dao.PublicizePhotoDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("publicizePhotoDao")
public class PublicizePhotoDaoImpl extends MyBaseDaoImpl implements PublicizePhotoDao{

	@Override
	public Integer addPublicizePhoto(PublicizePhotos publicizePhotos)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(publicizePhotos, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updatePublicizePhoto(PublicizePhotos publicizePhotos)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE publicize_photos SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(publicizePhotos);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}	
		sql += " WHERE publicize_photo_id = ? ";
		
		list.add(publicizePhotos.getPublicizePhotoId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<PublicizePhotos> getPhotos(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT publicize_photo_id, service_id, role, photo_module, community_id, app_version, img_url , create_time , update_time FROM publicize_photos WHERE community_id = ? ";
		List<PublicizePhotos> list = this.getList(sql, new Object[]{communityId}, PublicizePhotos.class);
		return list;
	}

	@Override
	public List<PublicizePhotos> getPhotos(Integer communityId, Integer serviceId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT publicize_photo_id, service_id, role, photo_module, community_id, app_version, img_url , create_time , update_time FROM publicize_photos WHERE community_id = ? AND service_id = ? ";
		List<PublicizePhotos> list = this.getList(sql, new Object[]{communityId,serviceId}, PublicizePhotos.class);
		return list;
	}

	@Override
	public List<PublicizePhotos> getPhotos(Integer communityId, Integer serviceId,
			String photoModule) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT publicize_photo_id, service_id, role, photo_module, community_id, app_version, img_url , create_time , update_time FROM publicize_photos WHERE community_id = ? AND service_id = ? AND photo_module = ? ";
		List<PublicizePhotos> list = this.getList(sql, new Object[]{communityId,serviceId,photoModule}, PublicizePhotos.class);
		return list;
	}

}
