package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import com.xj.bean.EquipmentDetail;
import com.xj.dao.EquipmentDetailDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

public class EquipmentDetailDaoImpl extends MyBaseDaoImpl implements EquipmentDetailDao {

	@Override
	public Integer addAppversionDetail(EquipmentDetail appversionDetail)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(appversionDetail, key);
		return key.getId();
	}

	@Override
	public List<EquipmentDetail> getAppversionDetail(Integer communityId,
			Integer versionId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT equipment_detail_id, detail_content, community_id, equipment_version_id, create_time FROM equipment_detail WHERE community_id = ? AND equipment_version_id = ?";
		List<EquipmentDetail> list = this.getList(sql, new Object[]{communityId , versionId}, EquipmentDetail.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateAppversionDetail(EquipmentDetail appversionDetail)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE equipment_detail SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(appversionDetail);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE equipment_detail_id = ? ";
		
		list.add(appversionDetail.getAppversionDetailId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

}
