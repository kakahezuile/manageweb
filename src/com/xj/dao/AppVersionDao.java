package com.xj.dao;

import java.util.List;

import com.xj.bean.AppUpdate;
import com.xj.bean.AppVersion;
import com.xj.bean.Apps;
import com.xj.bean.Communities;
import com.xj.bean.Page;

public interface AppVersionDao extends MyBaseDao{
	
	public Integer addAppVersion(AppVersion appVersion) throws Exception;
	
	public List<AppVersion> getAppVersions() throws Exception;

	/**
	 *  保存APP更新配置
	 * @param update
	 * @return 
	 */
	public Integer insertAppUpdateConfig(AppUpdate update);

	/**
	 * 更新指定类型下的指定数据
	 * @param type
	 * @param updateUser
	 * @param string
	 * @return 
	 */
	public Integer updateAppUpdateConfig(String type, String updateUser,
			String string);

	/**
	 * 获取全部小区及app版本
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Communities> getListCommunityAppVersion(Integer pageNum,
			Integer pageSize);

	/**
	 * 获取所有的版本
	 * @return
	 */
	public List<Apps> getAppversions();

	/**
	 * 搜索小区
	 * @param query
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	Page<Communities> getListCommunityAppVersionByName(String query, Integer pageNum, Integer pageSize);


}
