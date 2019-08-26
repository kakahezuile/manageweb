package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Express;
import com.xj.bean.ExpressTop;
import com.xj.bean.Page;
import com.xj.dao.ExpressDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("expressDao")
public class ExpressDaoImpl extends MyBaseDaoImpl implements ExpressDao{

	@Override
	public Integer addExpress(Express express) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(express , key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateExpress(Express express) throws Exception {
		String sql = " UPDATE express SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(express);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE express_id = ? ";
		list.add(express.getExpressId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	/**
	 * 查询登记的快递列表
	 */
	@Override
	public Page<Express> findAllByType(Integer communityId, Integer type,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT * FROM express WHERE community_id = ? ";
		if(type != -1){
			if(type == 1){ // 已取件
				sql += " AND express_status = 1 ";
			}else if(type == 2){ // 未取件
				sql += " AND express_status = 0 ";
			}
		}
		sql+=" ORDER BY express_id desc";
		return this.getData4Page(sql, new Object[]{communityId}, pageNum, pageSize, nvm, Express.class);
	}

	@Override
	public Express isEmpty(String expressNo) throws Exception {
		String sql = " SELECT * FROM express WHERE express_no = ? ";
		List<Express> list = this.getList(sql, new Object[]{expressNo}, Express.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<Express> findAllByText(Integer communityId, Integer type,
			String text, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		String sql = " SELECT * FROM express WHERE community_id = ? AND express_phone like '%"+text+"%' or express_name like '%"+text+"%' ";
		if(type != -1){
			if(type == 1){ // 已取件
				sql += " AND express_status = 1 ";
			}else if(type == 2){ // 未取件
				sql += " AND express_status = 0 ";
			}
		}
		sql+=" ORDER BY express_id desc";
		return this.getData4Page(sql, new Object[]{communityId}, pageNum, pageSize, nvm, Express.class);
	}

	/**
	 * 快递统计头部
	 */
	@Override
	public ExpressTop getExpressTop(Integer startTime, Integer endTime)
			throws Exception {
		String sql = "SELECT e.express_create_time AS express_time,COUNT(e.express_id) AS express_num,SUM(case when e.express_user_type=0 then 1 else 0 end) AS app_message," +
				"SUM(case when e.express_user_type=1 then 1 else 0 end) AS short_note FROM express e WHERE e.express_create_time >=? AND e.express_create_time <= ?";
		List<ExpressTop> list = this.getList(sql, new Object[]{startTime,endTime}, ExpressTop.class);
		
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 快递统计列表
	 */
	@Override
	public Page<ExpressTop> getExpressList(Integer pageNum, Integer pageSize,
			Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT e.express_create_time AS express_time,COUNT(e.express_id) AS express_num,SUM(case when e.express_user_type=0 then 1 else 0 end) AS app_message," +
		"SUM(case when e.express_user_type=1 then 1 else 0 end) AS short_note FROM express e WHERE e.express_create_time >=? AND e.express_create_time <= ? GROUP BY day(from_unixtime(e.express_create_time))";
		return this.getData4Page(sql, new Object[]{startTime,endTime}, pageNum, pageSize,10, ExpressTop.class);
	}

	/**
	 * 快递详情统计
	 */
	@Override
	public Page<Express> getExpressDetail(Integer type,Integer pageNum, Integer pageSize,
			Integer startTime, Integer endTime) throws Exception {
		String sql = " SELECT * FROM express e  WHERE e.express_create_time >=? AND e.express_create_time <= ?  ";
		if(type != -1){
			if(type == 1){ // 已取件
				sql += " AND e.express_status = 1 ";
			}else if(type == 2){ // 未取件
				sql += " AND e.express_status = 0 ";
			}
		}
		return this.getData4Page(sql, new Object[]{startTime,endTime}, pageNum, pageSize, 10, Express.class);
	}
}