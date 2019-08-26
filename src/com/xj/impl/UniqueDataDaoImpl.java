package com.xj.impl;

import org.springframework.stereotype.Repository;

import com.xj.bean.UniqueData;
import com.xj.dao.UniqueDataDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("uniqueDataDao")
public class UniqueDataDaoImpl extends MyBaseDaoImpl implements UniqueDataDao{

	@Override
	public Integer addUniqueData(UniqueData uniqueData) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(uniqueData, key);
		return key.getId();
	}

}
