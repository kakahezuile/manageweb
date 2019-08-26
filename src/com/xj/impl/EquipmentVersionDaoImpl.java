package com.xj.impl;

import java.util.List;

import com.xj.bean.EquipmentVersion;
import com.xj.dao.EquipmentVersionDao;
import com.xj.httpclient.vo.MyReturnKey;

public class EquipmentVersionDaoImpl extends MyBaseDaoImpl implements EquipmentVersionDao{

	@Override
	public Integer addEquipmentVersion(EquipmentVersion equipmentVersion) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(equipmentVersion, key);
		return key.getId();
	}

	@Override
	public EquipmentVersion getEquimentVersionMax(String equiment)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT equipment_version_id, equipment, create_time, equipment_version FROM equipment_version WHERE equipment = ? AND create_time = (SELECT MAX(create_time) FROM equipment_version)";
		List<EquipmentVersion> list = this.getList(sql, new Object[]{equiment}, EquipmentVersion.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
