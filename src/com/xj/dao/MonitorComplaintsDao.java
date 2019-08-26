package com.xj.dao;

import com.xj.bean.MonitorComplaints;
import com.xj.bean.MonitorComplaintsTop;
import com.xj.bean.Page;

public interface MonitorComplaintsDao extends MyBaseDao{
	
	public MonitorComplaintsTop getComplaintsTop() throws Exception;
	
	public Page<MonitorComplaints> getMonitorComplaintsList(Integer communityId , Integer startTime , Integer endTime , String sort , String name , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
}
