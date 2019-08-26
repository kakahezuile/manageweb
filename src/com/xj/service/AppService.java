package com.xj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.AppDownload;
import com.xj.bean.Apps;
import com.xj.dao.AppDao;

@Service("appService")
public class AppService {

	@Autowired
	private AppDao appDao;

	public Apps getVersion(String appName) throws Exception {
		return appDao.getVersion(appName);
	}

	public Integer addAppDownload(AppDownload appDownload) throws Exception {
		return appDao.addAppDownload(appDownload);
	}
}