package com.xj.dao;

import com.xj.bean.AppDownload;
import com.xj.bean.Apps;

public interface AppDao extends MyBaseDao {

	Apps getVersion(String appName) throws Exception;

	Integer addAppDownload(AppDownload appDownload) throws Exception;

}