package com.xj.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.DetailVO;
import com.xj.bean.LifeCircleDetail;
import com.xj.bean.LifeCircleNumer;
import com.xj.dao.LifeCircleDetailDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("lifeCircleDetailDao")
public class LifeCircleDetailDaoImpl extends MyBaseDaoImpl implements LifeCircleDetailDao{

	@Override
	public Integer addLifeCircleDetail(LifeCircleDetail lifeCircleDetail)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		lifeCircleDetail.setPraiseSum(0);
		this.save(lifeCircleDetail, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateLifeCircleDetail(LifeCircleDetail lifeCircleDetail)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE life_circle_detail SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(lifeCircleDetail);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE life_circle_detail_id = ? ";
		list.add(lifeCircleDetail.getLifeCircleDetailId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<LifeCircleDetail> getLifeCircleDetails(Integer lifeCircleId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " select ld.life_circle_detail_id , ld.emob_id_from , ld.emob_id_to , u.nickname as from_name , us.nickname as to_name "+

					 " , ld.detail_content , ld.life_circle_id , ld.create_time , ld.update_time , ld.praise_sum "+

					 " FROM life_circle_detail ld left join users u ON ld.emob_id_from = u.emob_id "+

					 " left join users us on ld.emob_id_to = us.emob_id WHERE ld.life_circle_id = ? ORDER BY ld.create_time ASC";
		List<LifeCircleDetail> list = this.getList(sql, new Object[]{lifeCircleId}, LifeCircleDetail.class);
		return list;
	}

	@Override
	public boolean updateLifeCircleDetail(Integer lifeCircleId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE life_circle_detail SET praise_sum = praise_sum + 1 , update_time = unix_timestamp() WHERE life_circle_detail_id = ? ";
		List<Object> list = new ArrayList<Object>();
		list.add(lifeCircleId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public LifeCircleDetail getLifeCircleDetail(Integer lifeCircleId,
			String from, String to) throws Exception {
		// TODO Auto-generated method stub
		String sql = " select ld.life_circle_detail_id , ld.emob_id_from , ld.emob_id_to , '' as from_name , '' as to_name "+

		 " , ld.detail_content , ld.life_circle_id , ld.create_time , ld.update_time , ld.praise_sum "+

		 " FROM life_circle_detail ld WHERE ld.life_circle_id = ? AND ld.emob_id_from = ? AND ld.emob_id_to = ? ";
		List<LifeCircleDetail> list = this.getList(sql, new Object[]{lifeCircleId , from , to}, LifeCircleDetail.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<DetailVO> getDetailVO(String to, Integer createTime) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT l.emob_id_from , l.life_circle_id , l.create_time , u.nickname , u.avatar FROM life_circle_detail l LEFT JOIN users u ON l.emob_id_from = u.emob_id WHERE l.emob_id_to = ? AND l.create_time > ? ORDER BY l.create_time ASC";
		List<DetailVO> list = this.getList(sql, new Object[]{to , createTime}, DetailVO.class);
		return list;
	}

	@Override
	public LifeCircleNumer getLifeCircleNumber(String to, Integer createTime)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) as sum , max(create_time) as create_time FROM life_circle_detail l WHERE emob_id_to = ? AND create_time > ? ";
		List<LifeCircleNumer> list = this.getList(sql, new Object[]{to , createTime}, LifeCircleNumer.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Integer> getLifeCircleId(Integer communityId, String text)
			throws Exception {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		String sql = " select ld.life_circle_detail_id , ld.emob_id_from , ld.emob_id_to , '' as from_name , '' as to_name "+

		 " , ld.detail_content , ld.life_circle_id , ld.create_time , ld.update_time , ld.praise_sum "+

		 " FROM life_circle_detail ld LEFT JOIN life_circle l ON ld.life_circle_id = l.life_circle_id WHERE ld.detail_content like '%"+text+"%' AND l.community_id = ? GROUP BY ld.life_circle_id ";
		List<LifeCircleDetail> list = this.getList(sql, new Object[]{communityId}, LifeCircleDetail.class);
		if(list != null && list.size() > 0){
			Iterator<LifeCircleDetail> iterator = list.iterator();
			LifeCircleDetail lifeCircleDetail = null;
			while(iterator.hasNext()){
				lifeCircleDetail = iterator.next();
				result.add(lifeCircleDetail.getLifeCircleId());
			}
		}
		return result;
	}
	
	

}
