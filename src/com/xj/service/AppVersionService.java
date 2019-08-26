package com.xj.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.AppUpdate;
import com.xj.bean.AppVersion;
import com.xj.bean.Apps;
import com.xj.bean.Communities;
import com.xj.bean.Page;
import com.xj.dao.AppVersionDao;

@Service
public class AppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;
	
	public List<AppVersion> getAppVersion() throws Exception{ 
		return appVersionDao.getAppVersions();
	}

	/**
	 * 根据更新配置，配置更新
	 * @param update
	 * @return
	 */
	public Boolean configAppUpdate(AppUpdate update) {
		Integer config  = 0;
		String updateUser = update.getUpdateUser();
		if(("all".equals(update.getType()) && "all".equals(updateUser)) || ("community".equals(update.getType()) && updateUser.length()<32) || ("person".equals(update.getType()) && updateUser.length()==32) ){
				appVersionDao.updateAppUpdateConfig(update.getType(),update.getUpdateUser(),"off");//更新原来的配置为off
				config = appVersionDao.insertAppUpdateConfig(update);
		}
		if(config==0){
			throw new RuntimeException("添加APP升级配置失败!");
		}
		return true;
	}

	/**
	 * 获取小区及小区版本
	 * @param pageSize 
	 * @param pageNum 
	 * @param query 
	 * @return
	 * @throws Exception 
	 */
	public Page<Communities> getListCommunityAppVersion(Integer pageNum, Integer pageSize, String query) throws Exception {
		if(StringUtils.isNotBlank(query)){
			return appVersionDao.getListCommunityAppVersionByName(query,pageNum,pageSize);
		}else{
			return appVersionDao.getListCommunityAppVersion(pageNum,pageSize);
		}
	}
	


	/**
	 * 获取所有的app版本
	 * @return
	 */
	public List<Apps> getAppversions() {
		return appVersionDao.getAppversions();
	}
}
