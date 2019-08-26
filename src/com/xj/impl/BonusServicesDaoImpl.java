package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BonusServiceAndName;
import com.xj.bean.BonusServices;
import com.xj.dao.BonusServicesDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bonusServicesDao")
public class BonusServicesDaoImpl extends MyBaseDaoImpl implements BonusServicesDao{

	@Override
	public Integer addBonusServices(BonusServices bonusServices) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(bonusServices, key);
		return key.getId();
	}

	@Override
	public List<BonusServiceAndName> getBonusServiceNameList(Integer bonusId) throws Exception {
		String sql = "SELECT bs.bonus_id, bs.services_id, s.service_name FROM bonus_services bs left join services s on bs.services_id = s.service_id WHERE bs.bonus_id = ?";
		return this.getList(sql, new Object[]{bonusId}, BonusServiceAndName.class);
	}
}