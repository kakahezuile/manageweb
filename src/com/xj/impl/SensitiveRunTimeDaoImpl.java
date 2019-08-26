package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.SensitiveRunTime;
import com.xj.dao.SensitiveRunTimeDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("sensitiveRunTimeDao")
public class SensitiveRunTimeDaoImpl extends MyBaseDaoImpl implements SensitiveRunTimeDao{

	@Override
	public Integer addSensitiveRunTime(SensitiveRunTime sensitiveRunTime)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(sensitiveRunTime, key);
		return key.getId();
	}

	@Override
	public SensitiveRunTime getMaxSensitiveRuntime() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT id , timestamp FROM sensitive_run_time s where timestamp = (select max(timestamp) from sensitive_run_time)";
		List<SensitiveRunTime> list = this.getList(sql, new Object[]{}, SensitiveRunTime.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
