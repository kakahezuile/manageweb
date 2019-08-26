package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BlackList;
import com.xj.dao.BlackListDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("blackListDao")
public class BlackListDaoImpl extends MyBaseDaoImpl implements BlackListDao{

	@Override
	public Integer addBlackList(BlackList blackList) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(blackList, key);
		return key.getId();
	}

	@Override
	public List<BlackList> getBlackList(Integer communityId , String emobIdFrom , String status) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT b.black_list_id , b.emob_id_from , b.emob_id_to , b.create_time , b.update_time , u.nickname , u.avatar , b.community_id FROM black_list b LEFT JOIN users u ON b.emob_id_to = u.emob_id WHERE b.community_id = ? AND b.emob_id_from = ? AND b.status = ?";
		List<BlackList> list = this.getList(sql, new Object[]{communityId , emobIdFrom , status}, BlackList.class);
		return list;
	}

	@Override
	public boolean deleteBlackList(BlackList blackList) throws Exception {
		// TODO Auto-generated method stub
		int result = this.delete(blackList);
		return result > 0;
	}

	@Override
	public List<BlackList> getBlackList(Integer communityId, String emobIdFrom,
			String emobIdTo , String status) throws Exception {
		// TODO Auto-generated method stub
		String sql = "  SELECT b.black_list_id , b.emob_id_from , b.emob_id_to , b.create_time , b.update_time , u.nickname , u.avatar , b.community_id FROM black_list b LEFT JOIN users u ON b.emob_id_to = u.emob_id WHERE b.community_id = ? AND b.emob_id_from = ? AND b.emob_id_to = ? AND b.status = ? ";
		List<BlackList> list = this.getList(sql, new Object[]{communityId , emobIdFrom , emobIdTo , status}, BlackList.class);
		return list;
	}

}
