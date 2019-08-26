package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.ItemCategory;
import com.xj.dao.ItemCategoryDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("itemCategoryDao")
public class ItemCategoryDaoImpl extends MyBaseDaoImpl implements ItemCategoryDao{

	@Override
	public Integer addItemCategory(ItemCategory itemCategory) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(itemCategory, key);
		return key.getId();
	}

	@Override
	public List<ItemCategory> getItemCategoryList() throws Exception {
		return this.getList("SELECT * FROM item_category WHERE 1 = 1 ",null,ItemCategory.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateItemCategory(ItemCategory itemCategory) throws Exception {
		if(itemCategory.getUpdateTime() == null){
			itemCategory.setUpdateTime((int)(System.currentTimeMillis() / 1000));
		}
		String sql = "  UPDATE item_category SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(itemCategory);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE cat_id = ? ";
		System.out.println(sql);
		list.add(itemCategory.getCatId());
		int result = this.updateData(sql, list, null);
		return result > 0 ;
	}


	@Override
	public boolean deleteItemCategory(ItemCategory itemCategory) throws Exception {
		return this.delete(itemCategory) > 0;
	}

	@Override
	public List<ItemCategory> getItemCategoryListByType(String type) throws Exception {
		String sql = "SELECT * FROM item_category WHERE 1 = 1 AND shop_type = ? ";
		return this.getList(sql,new Object[]{type},ItemCategory.class);
	}

	@Override
	public List<ItemCategory> getItemCategoryListByType(Integer communityId, String type , String version) throws Exception {
		String sql = "SELECT * FROM item_category WHERE 1 = 1 AND shop_type = ? AND community_id = ? AND category_version = ? AND item_number != 0 ";
		return this.getList(sql,new Object[]{type , communityId , version},ItemCategory.class);
	}

	@Override
	public List<ItemCategory> getItemCategoryListByType(Integer communityId, String type ) throws Exception {
		String sql = "SELECT * FROM item_category WHERE 1 = 1 AND shop_type = ? AND community_id = ?  ORDER BY category_version desc ,sort_order  ";
		return this.getList(sql,new Object[]{type , communityId },ItemCategory.class);
	}

	@Override
	public Integer maxCreateTime(Integer communityId, String version) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			
			ps = con.prepareStatement("SELECT max(create_time) as create_time FROM item_category i where category_version = ? and community_id = ?");
			ps.setString(1, version);
			ps.setInt(2, communityId);
			
			rs = ps.executeQuery();
			if(rs != null && rs.next()){
				return rs.getInt("create_time");
			}
			return 0;
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != ps) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean updateItemCategory(int catId , int type) throws Exception {
		String sql = "";
		if (type == 1) { // 加
			sql = " UPDATE item_category SET item_number = item_number+1 , create_time = unix_timestamp() WHERE cat_id = ? ";
		} else if(type == 2) { // 减
			sql = " UPDATE item_category SET item_number = item_number-1 , create_time = unix_timestamp() WHERE cat_id = ? AND item_number > 0 ";
		}
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, catId);
			
			return ps.executeUpdate() > 0;
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean updateItemCat(int catId, int num) throws Exception {
		String sql = " UPDATE item_category SET item_number = ? , create_time = unix_timestamp() WHERE cat_id = ? ";
		List<Object> list = new ArrayList<Object>();
		list.add(num);
		list.add(catId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<ItemCategory> getItemCategoryListByShop(Integer communityId, String type, String version) throws Exception {
		String sql = "SELECT * FROM item_category WHERE 1 = 1 AND shop_type = ? AND community_id = ? AND category_version = ?  ";
		return this.getList(sql,new Object[]{type , communityId , version},ItemCategory.class);
	}
}