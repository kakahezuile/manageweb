package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.AppDownload;
import com.xj.bean.Apps;
import com.xj.dao.AppDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("appDaoImpl")
public class AppDaoImpl extends MyBaseDaoImpl implements AppDao {

	@Override
	public Apps getVersion(String appName) throws Exception {
		String sql = " SELECT version,update_time,detail,app_name,url FROM app  WHERE app_name=? ORDER BY id desc";
		List<Apps> list = this.getList(sql, new Object[] { appName }, Apps.class);

		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer addAppDownload(AppDownload appDownload) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(appDownload, key);
		return key.getId();
	}
}