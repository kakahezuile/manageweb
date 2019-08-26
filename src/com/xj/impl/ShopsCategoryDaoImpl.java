package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.ShopsAndCategory;
import com.xj.bean.ShopsCategory;
import com.xj.dao.ShopsCategoryDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("shopsCategoryDao")
public class ShopsCategoryDaoImpl extends MyBaseDaoImpl implements ShopsCategoryDao {

	@Override
	public Integer addShopsCategory(ShopsCategory shopsCategory)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(shopsCategory, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShopsCategory(ShopsCategory shopsCategory)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE shops_category SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopsCategory);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE shops_category_id = ? ";
		list.add(shopsCategory.getShopsCategoryId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	@Override
	public List<ShopsCategory> getShopsCategoryList(Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM shops_category WHERE category_id = ?";
		List<ShopsCategory> list = this.getList(sql, new Object[]{catId}, ShopsCategory.class);
		return list;
	}

	@Override
	public Page<ShopsAndCategory> getShopsAndCategoryList(Integer communityId , Integer categoryId , Integer pageNum , Integer pageSize , Integer nvm)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT sc.shops_category_id , s.shop_id , s.shop_name , s.emob_id , s.status , round(avg(ifnull(c.score,0)),0) as score , s.logo FROM shops_category sc left join shops s on sc.shop_id = s.shop_id " +
					 " left join comments c on s.emob_id = c.emob_id_to WHERE s.community_id = ? and sc.category_id = ? AND sc.status = 'true'  AND s.status != 'block' and s.status != 'leave' group by sc.shops_category_id order by s.status asc , score desc ";
		Page<ShopsAndCategory> page = this.getData4Page(sql, new Object[]{communityId,categoryId}, pageNum, pageSize, nvm, ShopsAndCategory.class);
		return page;
	}

	@Override
	public ShopsCategory getShopsCategory(Integer shopId, Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM shops_category WHERE shop_id = ? AND category_id = ? ";
		List<ShopsCategory> list = this.getList(sql, new Object[]{shopId , catId}, ShopsCategory.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<ShopsCategory> getShopsCategoryByShopId(Integer shopId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM shops_category WHERE shop_id = ? AND status = 'true' ";
		List<ShopsCategory> list = this.getList(sql, new Object[]{shopId}, ShopsCategory.class);
		return list;
	}

}
