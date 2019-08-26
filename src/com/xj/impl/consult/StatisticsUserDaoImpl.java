package com.xj.impl.consult;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.click.StatisticsUser;
import com.xj.dao.consult.StatisticsUserDao;
import com.xj.impl.MyBaseDaoImpl;


@Repository("statisticsUserDao")
public class StatisticsUserDaoImpl extends MyBaseDaoImpl implements StatisticsUserDao{

	@Override
	public List<StatisticsUser> selectStatisticsUser(Integer communityId,Integer startTime,Integer endTime) throws Exception {
		String sql = " SELECT bank_category_id , bank_name , bank_cate FROM bank_category WHERE 1 = 1 ";
		List<StatisticsUser> list = this.getList(sql, new Object[]{}, StatisticsUser.class);
		return list;
	}


}
