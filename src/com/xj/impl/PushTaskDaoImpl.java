package com.xj.impl;

import org.springframework.stereotype.Repository;

import com.xj.bean.PushTask;
import com.xj.dao.PushTaskDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("pushTaskDao")
public class PushTaskDaoImpl extends MyBaseDaoImpl implements PushTaskDao {

	@Override
	public Integer addPushTask(PushTask pushTask) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(pushTask, key);
		return key.getId();
	}

}
