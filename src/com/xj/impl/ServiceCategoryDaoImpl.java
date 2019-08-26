package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Services;
import com.xj.dao.ServiceCategoryDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("serviceCategoryDao")
public class ServiceCategoryDaoImpl extends MyBaseDaoImpl implements ServiceCategoryDao{

	@Override
	public Integer addCategory(Services serviceCategory)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey myReturnKey = new MyReturnKey();
		Integer result = this.save(serviceCategory, myReturnKey);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateCategory(Services serviceCategory)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE services SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(serviceCategory);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE service_id = ? ";
		list.add(serviceCategory.getServiceId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
		
	}

	@Override
	public boolean deleteCategory(Integer categoryId) throws Exception {
		// TODO Auto-generated method stub
		Services serviceCategory = new Services();
		serviceCategory.setServiceId(categoryId);
		int result = this.delete(serviceCategory);
		return result > 0;
	}

	@Override
	public List<Services> getCategoryList() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT s.service_id ,1 as service_level, s.service_name , 1 as community_service_id , s.div_type , s.input_id FROM services s WHERE 1 = 1";
		List<Services> list = this.getList(sql , null , Services.class);
		return list;
	}

	@Override
	public List<Services> getIsHaveCategoryList(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.service_id ,c.service_level, s.service_name , c.community_service_id " +

					 " FROM community_service c left join services s "+

					 " on c.service_id = s.service_id where c.community_id = ? and c.relation_status = 'normal' ";
		List<Services> list = this.getList(sql, new Object[]{communityId}, Services.class);
		return list;
	}
	
	@Override
	public List<Services> getIsHaveCategoryList(Integer communityId , Integer appVersionId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.service_id , s.service_name ,c.service_level, c.community_service_id " +

					 " FROM community_service c left join services s "+

					 " on c.service_id = s.service_id where c.community_id = ? and c.app_version_id = ? and c.relation_status = 'normal' ";
		List<Services> list = this.getList(sql, new Object[]{communityId,appVersionId}, Services.class);
		return list;
	}

	@Override
	public List<Services> getIsHaveCategoryList(String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.service_id , s.service_name ,c.service_level, c.user_service_id as community_service_id " +

		 			 "   FROM community_user_service c left join services s "+

		 			 " 	 on c.service_id = s.service_id where c.emob_id = ? and c.status = 'normal' ";
		List<Services> list = this.getList(sql, new Object[]{emobId}, Services.class);
		return list;
	}

}
