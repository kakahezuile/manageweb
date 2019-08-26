package com.xj.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xj.bean.Complaints;
import com.xj.bean.Page;
import com.xj.dao.ComplaintsDao;
import com.xj.dao.ReturnKeyList;
import com.xj.httpclient.utils.DaoUtils;


@Repository("complaintsDao")
public class ComplaintsDaoImpl extends MyBaseDaoImpl implements ComplaintsDao {

	@Override
	public Integer addComplaint(Complaints complaints) throws Exception {
		// TODO Auto-generated method stub
		int id = 0;
		RetrunKey keyList = new RetrunKey();
		this.save(complaints,keyList);
		id = keyList.getId();
		return id;
	}
	class RetrunKey implements ReturnKeyList{
		
		private Integer id;
		
		public Integer getId(){
			return this.id;
		}

		@Override
		public void getKeyList(List<Map<String, Object>> keyList) {
			// TODO Auto-generated method stub
			this.id = ((Long) keyList.get(0).get("GENERATED_KEY")).intValue();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateComplaint(Complaints complaints) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE complaints SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(complaints);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE complaint_id = ? ";
	
		list.add(complaints.getComplaintId());
		int result = this.updateData(sql, list, null);
		return result > 0 ;
	}
	@Override
	public List<Complaints> getComplaintList(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM complaints WHERE community_id = ?";
		List<Complaints> list = this.getList(sql, new Integer[]{communityId}, Complaints.class);
		return list;
	}
	@Override
	public Complaints getComplaint(Integer complaintId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM complaints WHERE complaint_id = ?";
		List<Complaints> resultList = this.getList(sql, new Integer[]{complaintId}, Complaints.class);
		return resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
	}
	@Override
	public Page<Complaints> getComplaintWithPage(Integer communityId , Integer pageNum , Integer pageSize , Integer nvm)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM complaints WHERE community_id = ? ";
		Page<Complaints> page = this.getData4Page(sql, new Integer[]{communityId}, pageNum, pageSize, nvm, Complaints.class);
		return page;
	}
	
}
