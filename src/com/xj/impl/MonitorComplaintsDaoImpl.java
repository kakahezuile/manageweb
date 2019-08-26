package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.MonitorComplaints;
import com.xj.bean.MonitorComplaintsTop;
import com.xj.bean.Page;
import com.xj.dao.MonitorComplaintsDao;

@Repository("monitorComplaintsDao")
public class MonitorComplaintsDaoImpl extends MyBaseDaoImpl implements MonitorComplaintsDao{

	@Override
	public MonitorComplaintsTop getComplaintsTop() throws Exception {
		String sql = "SELECT sum(case when s.sort = 5 then 1 else 0 end) as weixiu , " +
			 " sum(case when s.sort = 6 then 1 else 0 end) as baojie , " +
			 " sum(case when s.sort = 1 or s.sort = 2 then 1 else 0 end) as dianjia " +
			 " FROM complaints c left join shops s on c.emob_id_to = s.emob_id ";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			MonitorComplaintsTop monitorComplaintsTop = new MonitorComplaintsTop();
			if(resultSet != null && resultSet.next()){
				monitorComplaintsTop.setServiceCount(resultSet.getInt(1));
				monitorComplaintsTop.setCleanCount(resultSet.getInt(2));
				monitorComplaintsTop.setShopCount(resultSet.getInt(3));
			}
			
			return monitorComplaintsTop;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Page<MonitorComplaints> getMonitorComplaintsList(Integer communityId , Integer startTime , Integer endTime , String sort, String name ,
			Integer pageNum, Integer pageSize , Integer nvm) throws Exception {
		String sql = " SELECT c.emob_id_from as emob_id_user, c.emob_id_to as emob_id_shop , s.shop_name , u.nickname as nick_name , c.occur_time as complaints_time ," +
			 " c.status as complaints_status , u.phone , c.detail as complaints_detail , s.sort , st.shop_type_name as sort_name , c.emob_id_agent "+
			 " FROM complaints c LEFT JOIN shops s ON c.emob_id_to = s.emob_id "+
			 " LEFT JOIN users u ON c.emob_id_from = u.emob_id LEFT JOIN shop_type st ON s.sort = st.shop_type_id WHERE c.community_id = ? ";
		
		List<Object> list = new ArrayList<Object>();
		list.add(communityId);
		if(sort != null && !"".equals(sort)){
			String sorts[] = sort.split(",");
			int len = sorts.length;
			for(int i = 0 ; i < len ; i++){
				if(len == 1){
					sql += " AND s.sort = ? ";
				}else{
					if(i == 0){
						sql += " AND ( s.sort = ? ";
					}else if(i == len - 1){
						sql += " OR s.sort = ? ) ";
					}else{
						sql += " OR s.sort = ? ";
					}
				}
				
				list.add(sorts[i]);
			}
			
			
		}
		if(startTime != null && endTime != null){
			sql += " AND c.occur_time >= ? AND c.occur_time <= ? ";
			list.add(startTime);
			list.add(endTime);
		}
		if(name != null && !"".equals(name)){
			sql += " AND ( s.shop_name like '%"+name+"%' OR u.nickname like '%"+name+"%' ) ";
		}
		return this.getData4Page(sql, list.toArray() , pageNum, pageSize, nvm, MonitorComplaints.class);
	}
}