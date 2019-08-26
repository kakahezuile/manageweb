package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Promotions;
import com.xj.dao.PromotionsDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("promotionsDao")
public class PromotionsDaoImpl extends MyBaseDaoImpl implements PromotionsDao {

	@Override
	public Integer addPromotion(Promotions promotions) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(promotions, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updatePromotion(Promotions promotions) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE promotions SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(promotions);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}	
		sql += " WHERE promotion_id = ? ";
		System.out.println(sql);
		list.add(promotions.getPromotionId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<Promotions> getPromotions(Integer communityId, String sort)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT promotion_id , img_url , create_time , update_time , sort , community_id FROM promotions WHERE community_id = ? AND sort = ? ";
		List<Promotions> list = this.getList(sql, new Object[]{communityId , sort}, Promotions.class);
		return list;
	}

	@Override
	public Promotions getPromotion(Integer communityId, String sort)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "  SELECT promotion_id , img_url , create_time , update_time , sort , community_id FROM promotions WHERE community_id = ? AND sort = ? order by create_time desc limit 1  ";
		List<Promotions> list = this.getList(sql, new Object[]{communityId , sort}, Promotions.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
}
