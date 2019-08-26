package com.xj.dao;

import java.util.List;

import com.xj.bean.CommentsLists;
import com.xj.bean.ComplaintsDetail;
import com.xj.bean.ComplaintsList;
import com.xj.bean.Page;
import com.xj.bean.RepairStatisticsCenter;
import com.xj.bean.RepairStatisticsComplaints;
import com.xj.bean.RepairStatisticsHistory;
import com.xj.bean.RepairStatisticsHistoryCenter;
import com.xj.bean.RepairStatisticsTop;
import com.xj.bean.Subpackage;

public interface RepairStatisticsDao extends MyBaseDao{
	public List<RepairStatisticsHistory> getHistoryMaintainList2(Integer communityId,long startTime,
			long endTime, Integer pageNum, Integer pageSize, String sort)
			throws Exception ;
	//public RepairStatisticsTop getRepairStatisticsTop(Integer startTime , Integer endTime) throws Exception; // 维修首页头部查询
	public RepairStatisticsTop getRepairStatisticsTop(Integer communityId,long startTime , long endTime,String sort) throws Exception; // 维修首页头部查询
	
	/**
	 * 历史维修记录
	 * @return
	 * @throws Exception
	 */
	public List<RepairStatisticsCenter> getReaiStatisticsCenterList(Integer communityId,String sort) throws Exception;
	
	public List<RepairStatisticsHistory> getHistoryList(Integer communityId,Integer startTime , Integer endTime) throws Exception;
	
	/**
	 * 被投诉师傅的头部
	 */
	public RepairStatisticsComplaints getComplaints(Integer communityId,long startTime , long endTime , Integer shopId,String sort) throws Exception;

	
	public Page<RepairStatisticsHistoryCenter> getHistoryCenterList(Integer communityId,Integer startTime , Integer endTime , Integer shopId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
   /**
    * 历史维修记录列表
    */
	public Page<RepairStatisticsHistory> getHistoryMaintainList(Integer communityId,long startTime , long endTime , Integer pageNum , Integer pageSize,String sort) throws Exception;
	
	/**
	 * 历史维修记录详情
	 */
	public Page<RepairStatisticsHistoryCenter> getHistoryMaintainListDetail(Integer communityId,long startTime , long endTime , Integer pageNum , Integer pageSize,Integer shopId,String sort)throws Exception;

	public Page<ComplaintsList> getComplaintsList(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum)throws Exception;

	public Page<CommentsLists> getCommentsList(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum)throws Exception;
	public Page<ComplaintsDetail> getCommentsListDetail(Integer communityId,Integer startTimeInt,
			Integer endTimeInt, Integer pageNum,Integer shopId)throws Exception;
	public Page<Subpackage> getSubpackage(Integer communityId,
			Integer startTimeInt, Integer endTimeInt, Integer pageNum)throws Exception;
}
