package com.xj.dao;

import java.util.List;

import com.xj.bean.Admin;
import com.xj.bean.TryOut;
import com.xj.bean.Users;

public interface AdminDao extends MyBaseDao {
	
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	Integer insert(Admin admin) throws Exception;
	/**
	 * 根据用户名获取管理员信息
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	Admin getAdmin(String userName) throws Exception;
	
	/**
	 * 修改管理员信息
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	Integer update(Admin admin) throws Exception;
	
	/**
	 * 获取全部管理员
	 * @return
	 * @throws Exception
	 */
	List<Admin> getAdminList() throws Exception;
	
	/**
	 * 根据小区id 以及管理员类型 获取管理员列表
	 * @param communityId
	 * @param adminStatus
	 * @return
	 * @throws Exception
	 */
	List<Admin> getAdminList(Integer communityId , String adminStatus) throws Exception;
	
	/**
	 * 根据环信id 获取管理员信息
	 * @param emobId
	 * @return
	 * @throws Exception
	 */
	Admin getAdminByEmobId(String emobId) throws Exception;
	
	/**
	 * 根据管理员id  获取管理信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	Admin getAdminById(Integer adminId) throws Exception;
	
	/**
	 * 根据类型获取 管理员列表
	 * @param role
	 * @return
	 * @throws Exception
	 */
	List<Admin> getAdminListByRole(String role) throws Exception;
	
	Users getUser(String username)throws Exception;
	
	Integer insertUser(Users user)throws Exception;
	
	Integer insertTryOut(TryOut to)throws Exception;
	
	/**
	 * 检查admin是否是小区内唯一的客服
	 * @param communityId
	 * @param adminId
	 * @param adminStatus
	 * @return
	 * @throws Exception
	 */
	boolean isUniqueKefu(Integer communityId, Integer adminId, String adminStatus) throws Exception;
	
	/**
	 * 将admin设置为客服
	 * @param admin
	 * @return
	 */
	boolean updateAdminAsKefu(Admin admin) throws Exception;
	
	boolean updateAdmin(Admin admin) throws Exception;
}