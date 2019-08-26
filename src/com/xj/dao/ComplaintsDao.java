package com.xj.dao;

import java.util.List;

import com.xj.bean.Complaints;
import com.xj.bean.Page;

/**
 * 投诉dao
 * 
 * @author Administrator
 *
 */
public interface ComplaintsDao extends MyBaseDao {
	/**
	 * 添加投诉信息
	 * @param complaints
	 * @return 投诉信息的唯一标示
	 * @throws Exception
	 */
	public Integer addComplaint(Complaints complaints) throws Exception;
	
	/**
	 * 修改投诉信息
	 * @param complaints
	 * @return 是或否
	 * @throws Exception
	 */
	public boolean updateComplaint(Complaints complaints) throws Exception;
	
	/**
	 * 获取投诉列表
	 * @param communityId
	 * @return 投诉信息集合
	 * @throws Exception
	 */
	public List<Complaints> getComplaintList(Integer communityId) throws Exception;
	
	/**
	 * 根据主键获取投诉信息
	 * @param complaintId
	 * @return 一个投诉信息
	 * @throws Exception
	 */
	public Complaints getComplaint(Integer complaintId) throws Exception;
	
	/**
	 * 分页查询 - 根据小区id 查询当前小区下所有投诉信息
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<Complaints> getComplaintWithPage(Integer communityId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
}
