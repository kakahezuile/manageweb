package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xj.bean.CatIdNumber;
import com.xj.bean.CommunityShops;
import com.xj.bean.ItemCategory;
import com.xj.bean.Page;
import com.xj.bean.ShopItem;
import com.xj.bean.ShopItemAndShopName;
import com.xj.bean.Shops;
import com.xj.dao.ShopDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("shopDao")
public class ShopDaoImpl extends MyBaseDaoImpl implements ShopDao {
	// loger
	Logger logger = Logger.getLogger(ShopDao.class);

	@Override
	public List<ShopItem> findAllById(Long shopId) throws Exception {
		String sql = "SELECT * FROM shop_item WHERE shop_id = ? order by status ";
		List<ShopItem> list = this.getList(sql, new Long[] { shopId },
				ShopItem.class);
		return list != null ? list : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShop(ShopItem shopItem) throws Exception {
		String sql = " UPDATE shop_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopItem);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE service_id = ? ";
		System.out.println(sql);
		list.add(shopItem.getServiceId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Integer insertShop(ShopItem shopItem) throws Exception {
		if(shopItem != null){
			if(shopItem.getMultiattribute() == null){
				shopItem.setMultiattribute("no");
			}
			if(shopItem.getPurchase() == null){
				shopItem.setPurchase(0);
			}
		}
		MyReturnKey myReturnKey = new MyReturnKey();
		this.save(shopItem, myReturnKey);
		return myReturnKey.getId();
	}

	@Override
	public Page<ShopItem> finAllByIdWithPage(Long shopId, Integer catId,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		// TODO Auto-generated method stub

		String sql = "SELECT * FROM shop_item WHERE 1 = 1";
		Page<ShopItem> page = null;
		if (shopId == -1) {
			sql += " AND cat_id = ? order by service_id desc ";
			page = this.getData4Page(sql, new Object[] { catId }, pageNum,
					pageSize, nvm, ShopItem.class);
		} else {
			sql += " AND shop_id = ? AND cat_id = ? order by service_id desc ";
			page = this.getData4Page(sql, new Object[] { shopId, catId },
					pageNum, pageSize, nvm, ShopItem.class);
		}

		return page;
	}

	@Override
	public List<ShopItemAndShopName> findAllBySort(String sort,
			Integer communityId, String text) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.service_id , s.service_name , ss.emob_id as shop_emob_id , s.shop_id , s.current_price , s.origin_price , s.stock ,"
				+

				" s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , ss.shop_name , round(avg(c.score),0) as score , s.purchase , s.multiattribute , s.attr_id , s.attr_name"
				+

				" FROM shop_item s left join shops ss on s.shop_id = ss.shop_id left join comments c on ss.emob_id = c.emob_id_to "
				+

				" where s.status = 'available' and ss.status = 'normal' and ss.community_id = ? and ss.sort = ? and s.service_name like '%"
				+ text + "%' group by s.attr_id";
		List<ShopItemAndShopName> list = this.getList(sql, new Object[] {
				communityId, sort }, ShopItemAndShopName.class);
		return list;
	}
	
	@Override
	public Page<ShopItemAndShopName> findAllBySort(String sort,
			Integer communityId, String text , Integer pageNum , Integer pageSize , Integer nvm) throws Exception {
		String sql = " SELECT s.service_id , s.service_name , ss.emob_id as shop_emob_id , s.shop_id , s.current_price , s.origin_price , s.stock ,"
				+

				" s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , ss.shop_name , round(avg(c.score),0) as score , s.purchase , s.multiattribute , s.attr_id , s.attr_name "
				+

				" FROM shop_item s left join shops ss on s.shop_id = ss.shop_id left join comments c on ss.emob_id = c.emob_id_to "
				+

				" where s.status = 'available' and ss.status = 'normal' and ss.community_id = ? and ss.sort = ? and s.service_name like '%"
				+ text + "%' group by s.attr_id";
		Page<ShopItemAndShopName> list = this.getData4Page(sql, new Object[]{communityId , sort}, pageNum, pageSize, nvm, ShopItemAndShopName.class);
		return list;
	}

	@Override
	public Page<ShopItemAndShopName> findAllByWIthPageAndName(
			Integer communityId, Long ShopId, Integer catId, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT s.service_id , s.service_name , s.shop_id , ss.emob_id as shop_emob_id , s.current_price , s.origin_price , s.stock ,"
				+

				" s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , ss.shop_name , ifnull(round(avg(c.score),0),5) as score , s.purchase , s.multiattribute , s.attr_id , s.attr_name "
				+

				" FROM shop_item s left join shops ss on s.shop_id = ss.shop_id left join comments c on ss.emob_id = c.emob_id_to WHERE ss.community_id = ? AND ss.status = 'normal' AND s.status = 'available' ";
		Page<ShopItemAndShopName> page = null;
		List<Object> list = new ArrayList<Object>();
		list.add(communityId);
		if (ShopId == -1) {
			sql += " AND s.cat_id = ? GROUP BY s.attr_id order by s.level desc , s.service_id desc ";
			list.add(catId);
			// page = this.getData4Page(sql, new Object[]{ catId}, pageNum,
			// pageSize, nvm, ShopItemAndShopName.class);
		} else {
			sql += " AND s.shop_id = ? AND s.cat_id = ? GROUP BY s.attr_id order by s.level desc , s.service_id desc ";
			list.add(ShopId);
			list.add(catId);
			// page = this.getData4Page(sql, new Object[]{ShopId , catId},
			// pageNum, pageSize, nvm, ShopItemAndShopName.class);
		}

		page = this.getData4Page(sql, list.toArray(), pageNum, pageSize, nvm,
				ShopItemAndShopName.class);

		return page;
	}

	@Override
	public Page<ShopItem> findAllByCatId(Integer catId, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT * FROM shop_item s WHERE s.cat_id = ? and s.status = 'available' GROUP BY s.service_name ORDER BY s.service_id DESC";
		Page<ShopItem> page = this.getData4Page(sql, new Object[] { catId },
				pageNum, pageSize, nvm, ShopItem.class);
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShopByName(ShopItem shopItem) throws Exception {
		String sql = " UPDATE shop_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopItem);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE service_name = ? ";
		System.out.println(sql);
		list.add(shopItem.getServiceName());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<ShopItem> findAllByCatIdGroupBy(Integer catId) throws Exception {
		String sql = " SELECT * FROM shop_item s where cat_id = ? AND status !='delete' group by service_name ";
		List<ShopItem> list = this.getList(sql, new Object[] { catId },
				ShopItem.class);
		return list;
	}

	@Override
	public List<ShopItem> getShopItemList(Integer catId, String serviceName)
			throws Exception {
		String sql = " SELECT * FROM shop_item s where cat_id = ? and service_name = ? AND status !='delete' ";
		List<ShopItem> list = this.getList(sql, new Object[] { catId,
				serviceName }, ShopItem.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShopByCatIdAndServiceName(ShopItem shopItem)
			throws Exception {
		String sql = " UPDATE shop_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopItem);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE cat_id = ? AND service_name = ? AND status !='delete'";
		System.out.println(sql);
		list.add(shopItem.getCatId());
		list.add(shopItem.getServiceName());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<ShopItem> getShopItemByParam(Integer shopId, Integer catId,
			String serviceName) throws Exception {
		String sql = " SELECT * FROM shop_item WHERE shop_id = ? AND cat_id = ? AND service_name = ? ";
		List<ShopItem> list = this.getList(sql, new Object[] { shopId, catId,
				serviceName }, ShopItem.class);
		return list;
	}

	@Override
	public List<ItemCategory> getItemCategory(long shopId) throws Exception {
		String sql2 = "SELECT it.cat_id,it.cat_name,it.keywords,it.cat_desc,it.sort_order,it.create_time,it.shop_type,it.img_name "
				+ "FROM shops_category s RIGHT JOIN item_category it "
				+ "on s.category_id = it.cat_id where s.shop_id =? AND s.status='true'";
		List<ItemCategory> itList = this.getList(sql2, new Object[] { shopId },
				ItemCategory.class);
		return itList;
	}

	@Override
	public Page<ShopItem> findAllByCatId(String status, Integer catId,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT * FROM shop_item s WHERE s.cat_id = ? AND status !='delete' GROUP BY s.service_name ORDER BY s.service_id DESC";
		Page<ShopItem> page = this.getData4Page(sql, new Object[] { catId },
				pageNum, pageSize, nvm, ShopItem.class);
		return page;
	}

	@Override
	public ShopItem getShopItem(Integer serviceId) throws Exception {
		String sql = " SELECT * FROM shop_item s WHERE service_id = ? ";
		List<ShopItem> list = this.getList(sql, new Object[] { serviceId },
				ShopItem.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShopItemByVersion(ShopItem shopItem) throws Exception {
		String sql = " UPDATE shop_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopItem);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE service_id = ? AND version = ? ";
		System.out.println(sql);
		list.add(shopItem.getServiceId());
		list.add(shopItem.getVersion());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<ShopItem> getUpShopItem() throws Exception {
		String sql = " SELECT * FROM shop_item s WHERE  status !='delete' AND (s.cat_id=59 OR s.cat_id=60 OR s.cat_id=61 OR s.cat_id=62) GROUP BY s.service_name ORDER BY s.create_time DESC";
		Page<ShopItem> page = this.getData4Page(sql, new Object[] { },
				1, 5, 5, ShopItem.class);
		return page;
	}

	@Override
	public Page<ShopItem> getShopItemByCommunityIdAndCatId(Integer communityId , Integer catId , Integer pageNum , Integer pageSize , Integer nvm)
			throws Exception {
		String sql = " SELECT s.service_id , s.service_name , s.shop_id , s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , s.current_price " +
				
				" , origin_price , s.stock  FROM shop_item s LEFT JOIN shops ss ON s.shop_id = ss.shop_id WHERE s.cat_id = ? " +
				
				" and ss.community_id = ? and ss.status = 'normal' and s.status = 'available' GROUP BY s.service_name ORDER BY s.service_id DESC ";
		Page<ShopItem> page = this.getData4Page(sql, new Object[] { catId , communityId },
				pageNum, pageSize, nvm, ShopItem.class);
		return page;
	}

	@Override
	public Page<ShopItem> getShopByShopName(Integer communityId , String serviceName,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT s.service_id , s.service_name , s.shop_id , s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , s.current_price " +
		
					 " , origin_price , s.stock  FROM shop_item s LEFT JOIN shops ss ON s.shop_id = ss.shop_id WHERE s.service_name like '%"+serviceName+"%' " +
		
					 " and ss.community_id = ? and ss.status = 'normal' and s.status = 'available' GROUP BY s.service_name ORDER BY s.service_id DESC ";
		Page<ShopItem> page = this.getData4Page(sql, new Object[] {communityId},pageNum, pageSize, nvm, ShopItem.class);
		return page;
	}

	@Override
	public List<ShopItem> findAllByIdAndStatus(Long shopId, String status)
			throws Exception {
		String sql = "SELECT * FROM shop_item WHERE shop_id = ? and status = ? group by attr_id order by level desc , status asc ";
		List<ShopItem> list = this.getList(sql, new Object[] { shopId , status },
				ShopItem.class);
		return list;
	}

	@Override
	public Integer getMaxLevel(Long shopId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			
			ps = con.prepareStatement(" SELECT max(level) as level FROM shop_item WHERE shop_id = ? ");
			ps.setLong(1, shopId);
			
			rs = ps.executeQuery();
			if(rs != null && rs.next()){
				return rs.getInt("level");
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
	public Integer getBrandCount(Long shopId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			ps = con.prepareStatement(" SELECT COUNT(distinct attr_id) FROM shop_item WHERE shop_id = ? AND brand_id = 1 AND status = 'available'");
			ps.setLong(1, shopId);
			
			rs = ps.executeQuery();
			if(rs != null && rs.next()){
				return rs.getInt(1);
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
	public Integer getNewItem(Integer communityId, Integer time , String sort) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			ps = con.prepareStatement(" SELECT count(s.service_id) as item_count FROM shop_item s left join shops ss on s.shop_id = ss.shop_id where s.brand_id = 1 and ss.community_id = ? and ss.sort = ? and (s.create_time > ? or s.update_time > ?) ");
			ps.setInt(1, communityId);
			ps.setString(2, sort);
			ps.setInt(3, time);
			ps.setInt(4, time);
			
			rs = ps.executeQuery();
			if(rs != null && rs.next()){
				return rs.getInt("item_count");
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
	public List<ShopItem> getShopItemByCatId(Integer catId) throws Exception {
		String sql = " SELECT * FROM shop_item WHERE cat_id = ? ";
		return this.getList(sql, new Object[]{catId}, ShopItem.class);
	}

	@Override
	public List<CatIdNumber> getCatIdNumber(Long shopId) throws Exception {
		String sql = " SELECT count(si.service_id) as num , si.cat_id FROM shop_item si left join shops ss on si.shop_id = ss.shop_id where ss.sort = '2' and si.status = 'available' ";
		List<Object> list = new ArrayList<Object>();
		if(shopId != 0){
			sql += " AND si.shop_id = ? ";
			list.add(shopId);
		}
		sql += " group by si.cat_id ";
		List<CatIdNumber> numbers = this.getList(sql, list.toArray(), CatIdNumber.class);
		return numbers;
	}

	@Override
	public List<ShopItem> getShopItems() throws Exception {
		String sql = " SELECT * FROM shop_item WHERE 1 = 1 ";
		List<ShopItem> list = this.getList(sql, new Object[]{}, ShopItem.class);
		return list;
	}

	@Override
	public List<ShopItem> getShopItemByAttrId(Integer attrId , Integer catId) throws Exception {
		String sql = " SELECT s.service_id, s.service_name, s.origin_price, s.shop_id, s.status, s.cat_id, s.brand_id, " +
				
						"s.create_time, s.service_img, s.current_price, s.stock, s.version, s.level, s.multiattribute, s.purchase," +
						
						" s.attr_id, s.attr_name FROM shop_item s LEFT JOIN item_category i ON s.cat_id = i.cat_id WHERE s.attr_id = ? AND s.cat_id = ? AND s.multiattribute = 'yes' AND i.category_version = 'v2' AND s.status = 'available' ";
//		String sql = " SELECT * FROM shop_item WHERE attr_id = ? AND status = 'available' ";
		List<ShopItem> list = this.getList(sql, new Object[]{attrId, catId}, ShopItem.class);
		return list;
	}

	@Override
	public boolean updateShopItemByAttrId(Integer attrId , Integer level) throws Exception {
		String sql = " UPDATE shop_item SET level = ? , update_time = UNIX_TIMESTAMP() WHERE attr_id = ? ";
		List<Object> list = new ArrayList<Object>();
		list.add(level);
		list.add(attrId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShopItemByAttrId(ShopItem shopItem)
			throws Exception {
		String sql = " UPDATE shop_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(shopItem);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}

		sql += " WHERE attr_id = ? and status != 'delete' ";
		System.out.println(sql);
		list.add(shopItem.getAttrId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<ShopItemAndShopName> findAllBySortAndVersion(String sort,
			Integer communityId, String text, String version, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT s.service_id , s.service_name , ss.emob_id as shop_emob_id , s.shop_id , s.current_price , s.origin_price , s.stock ,"
			+

			" s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , ss.shop_name , round(avg(c.score),0) as score , s.purchase , s.multiattribute , s.attr_id , s.attr_name "
			+

			" FROM shop_item s left join shops ss on s.shop_id = ss.shop_id left join comments c on ss.emob_id = c.emob_id_to LEFT JOIN item_category ic ON s.cat_id = ic.cat_id "
			+

			" where s.status = 'available' and ss.status = 'normal' and ss.community_id = ? and ss.sort = ? and ic.category_version = ? and s.service_name like '%"
			+ text + "%' group by s.attr_id";
	Page<ShopItemAndShopName> list = this.getData4Page(sql, new Object[]{communityId , sort , version}, pageNum, pageSize, nvm, ShopItemAndShopName.class);
	return list;
	}

	@Override
	public List<ShopItemAndShopName> findAllBySortAndVersion(String sort,
			Integer communityId, String text, String version) throws Exception {
		String sql = " SELECT s.service_id , s.service_name , ss.emob_id as shop_emob_id , s.shop_id , s.current_price , s.origin_price , s.stock ,"
			+

			" s.status , s.cat_id , s.brand_id , s.create_time , s.service_img , ss.shop_name , round(avg(c.score),0) as score , s.purchase , s.multiattribute , s.attr_id , s.attr_name"
			+

			" FROM shop_item s left join shops ss on s.shop_id = ss.shop_id left join comments c on ss.emob_id = c.emob_id_to LEFT JOIN item_category ic on s.cat_id = ic.cat_id  "
			+

			" where s.status = 'available' and ss.status = 'normal' and ss.community_id = ? and ss.sort = ? and ic.category_version = ? and s.service_name like '%"
			+ text + "%' group by s.attr_id";
	List<ShopItemAndShopName> list = this.getList(sql, new Object[] {
			communityId, sort , version }, ShopItemAndShopName.class);
	return list;
	}

	@Override
	public ShopItem getShopItemByServiceId(Integer serviceId) throws Exception {
		String sql = " SELECT * FROM shop_item s WHERE service_id = ? AND status = 'available' ";
		List<ShopItem> list = this.getList(sql, new Object[]{serviceId}, ShopItem.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<ShopItem> getCountByCutId(Integer catId) throws Exception {
		String sql = " SELECT * FROM shop_item WHERE cat_id = ? AND status = 'available' GROUP BY attr_id";
		List<ShopItem> list = this.getList(sql, new Object[]{catId}, ShopItem.class);
		return list;
	}

	@Override
	public List<ShopItem> getShopItems(Long shopId, Integer catId, String status)
			throws Exception {
		String sql = " SELECT * FROM shop_item WHERE shop_id = ? AND cat_id = ? AND status = ? GROUP BY attr_id ORDER BY level DESC";
		List<ShopItem> list = this.getList(sql, new Object[]{shopId , catId , status}, ShopItem.class);
		return list;
	}

	@Override
	public List<ShopItem> getShopItemByAttrId(Long shopId, Integer catId,
			Integer attrId) throws Exception {
		String sql = " SELECT s.service_id, s.service_name, s.origin_price, s.shop_id, s.status, s.cat_id, s.brand_id, " +
		
						"s.create_time, s.service_img, s.current_price, s.stock, s.version, s.level, s.multiattribute, s.purchase," +
		
						" s.attr_id, s.attr_name FROM shop_item s WHERE s.shop_id = ? AND s.attr_id = ? AND s.multiattribute = 'yes' AND s.status = 'available' ";
		List<ShopItem> list = this.getList(sql, new Object[]{shopId, attrId}, ShopItem.class);
		return list;
	}

	@Override
	public List<Shops> getShopList() throws Exception {
		String sql = " SELECT * FROM shops WHERE sort=2 AND status='normal'";
		List<Shops> list = this.getList(sql, new Object[]{}, Shops.class);
		return list;
	}

	@Override
	public Integer upCommShops(CommunityShops shops) throws Exception {
		
		String sql1= " SELECT * FROM community_shops WHERE community_id=?";
		List<CommunityShops> list1 = this.getList(sql1, new Object[]{shops.getCommunityId()}, CommunityShops.class);
		List<Object> list = new ArrayList<Object>();
		if(list1.size()==0){
			MyReturnKey myReturnKey = new MyReturnKey();
			this.save(shops, myReturnKey);
			return myReturnKey.getId();
		}
		
		String sql = " UPDATE community_shops SET shop_id=?";

		sql += " WHERE community_id = ? ";
		list.add(shops.getShopId());
		list.add(shops.getCommunityId());
		int result = this.updateData(sql, list, null);
		return result ;
	}

	@Override
	public CommunityShops getShopName(Integer communityId) throws Exception {
		String sql1= " SELECT * FROM community_shops WHERE community_id=?";
		List<CommunityShops> list1 = this.getList(sql1, new Object[]{communityId}, CommunityShops.class);
		if(list1.size()==0){
			
			return null;
		}
		return list1.get(0);
	}
}