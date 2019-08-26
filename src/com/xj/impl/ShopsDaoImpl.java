package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.ShopLimit;
import com.xj.bean.Shops;
import com.xj.bean.ShopsPhone;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;
import com.xj.utils.DateUtils;

/**
 * Created by maxwell on 14/12/4.
 */
@Repository("shopsDao")
public class ShopsDaoImpl extends MyBaseDaoImpl implements ShopsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(ShopsDaoImpl.class);

	public int insert(Shops shopsBean) {
		int id = 0;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "insert into shops(shop_name,shops_desc,address,phone,logo,status,sort,create_time,community_id,emob_id) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			preparedStatement.setString(index++, shopsBean.getShopName());
			preparedStatement.setString(index++, shopsBean.getShopsDesc());
			preparedStatement.setString(index++, shopsBean.getAddress());
			preparedStatement.setString(index++, shopsBean.getPhone());
			preparedStatement.setString(index++, shopsBean.getLogo());
			preparedStatement.setString(index++, shopsBean.getStatus());
			preparedStatement.setString(index++, shopsBean.getSort());
			preparedStatement.setLong(index++, shopsBean.getCreateTime());
			preparedStatement.setInt(index++, shopsBean.getCommunityId());
			preparedStatement.setString(index++, shopsBean.getEmobId());

			logger.info("insert sql is :" + preparedStatement);
			preparedStatement.executeUpdate();
			logger.info("query done");
			resultSet = preparedStatement.getGeneratedKeys();

			logger.info("resultset is :" + resultSet);
			if (resultSet.next()) {
				logger.info("result set has next");
				id = resultSet.getInt(1);
				logger.info("id is :" + id);
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}

		return id;
	}

	public int findIdByName(String name) {
		logger.info("find by name : " + name);
		int id = 0;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select shop_id from shops where shop_name = ?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			logger.info("got connection :" + con);
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setString(index++, name);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				id = Integer.parseInt(resultSet.getString("shop_id"));
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}
		logger.info("id to return is :" + id);
		return id;
	}

	public List<Shops> findAllById(Integer communityId) {
		List<Shops> listShopsBeans = new ArrayList<Shops>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from shops where community_id =?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setInt(index, communityId);
			logger.info("sql is :" + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Shops shopsBean = new Shops();
				shopsBean.setShopId(resultSet.getLong("shop_id"));
				shopsBean.setShopName(resultSet.getString("shop_name"));
				shopsBean.setPhone(resultSet.getString("phone"));
				shopsBean.setStatus(resultSet.getString("status"));
				shopsBean.setCreateTime(resultSet.getLong("create_time"));
				shopsBean.setSort(resultSet.getString("sort"));
				shopsBean.setAddress(resultSet.getString("address"));
				shopsBean.setShopsDesc(resultSet.getString("shops_desc"));
				shopsBean.setLogo(resultSet.getString("logo"));
				listShopsBeans.add(shopsBean);
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}
		return listShopsBeans;
	}

	public List<Shops> findAllBySort(Integer communityId, String sort) {
		List<Shops> listShopsBeans = new ArrayList<Shops>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from shops where community_id =? and sort = ?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setInt(index++, communityId);
			preparedStatement.setString(index++, sort);
			logger.info("sql is :" + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Shops shopsBean = new Shops();
				shopsBean.setShopId(resultSet.getLong("shop_id"));
				shopsBean.setShopName(resultSet.getString("shop_name"));
				shopsBean.setPhone(resultSet.getString("phone"));
				shopsBean.setStatus(resultSet.getString("status"));
				shopsBean.setCreateTime(resultSet.getLong("create_time"));
				shopsBean.setSort(resultSet.getString("sort"));
				shopsBean.setAddress(resultSet.getString("address"));
				shopsBean.setShopsDesc(resultSet.getString("shops_desc"));
				shopsBean.setLogo(resultSet.getString("logo"));
				listShopsBeans.add(shopsBean);
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}
		return listShopsBeans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateShops(Shops shopsBean) throws Exception {
		String sql = "UPDATE shops SET ";
		List<Object> list = new ArrayList<Object>();
		Object objResult[] = DaoUtils.reflect(shopsBean);
		if (objResult[1] != null && ((List<Object>) objResult[1]).size() > 0) {
			sql += (String) objResult[0];
			list = (List<Object>) objResult[1];
		} else {
			return false;
		}
		sql += " WHERE shop_id = ? ";
		list.add(shopsBean.getShopId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<Shops> getShopsListById(Integer communityId, Integer pageNum,
			Integer pageSize, Integer navNum) throws Exception {
		Integer month = DateUtils.getThisMonth();

		String sql = "SELECT (sum(case when o.order_month = ? then 1 else 0 end)) as order_sum  , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
				+

				" s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score)) as score  ,s.business_start_time,s.business_end_time "
				+

				" FROM shops s left join orders o on s.emob_id = o.emob_id_shop  left join comments c on c.emob_id_to = s.emob_id  WHERE s.community_id = ? group by s.shop_id   order by s.shop_id desc ";
		Page<Shops> page = this.getData4Page(sql, new Integer[] { month,
				communityId }, pageNum, pageSize, navNum, Shops.class);
		return page;
	}

	@Override
	public Page<Shops> getShopsListByText(Integer communityId, String text,
			Integer pageNum, Integer pageSize, Integer navNum, Integer type)
			throws Exception {
//		Integer month = DateUtils.getThisMonth();
		String sql = null;
		Page<Shops> page = null;
		if (type == 1) { // 模糊查询
			text = "%" + text + "%";
			sql = "SELECT s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , 0 as order_sum , "
					+

					" s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score)) as score  ,s.business_start_time,s.business_end_time  FROM shops s left join comments c on c.emob_id_to = s.emob_id  WHERE s.community_id = ? and s.phone like ? or s.shop_name like ? group by s.shop_id order by s.shop_id ASC";
			// sql =
			// "SELECT * FROM shops WHERE phone like ? or shop_name like ? order by shop_id desc";
			page = this.getData4Page(sql, new Object[] {  communityId,
					text, text }, pageNum, pageSize, navNum, Shops.class);
		} else if (type == 2) { // 根据sort查询
			sql = "SELECT  s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , 0 as order_sum , "
					+

					" s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score)) as score  ,s.business_start_time,s.business_end_time  FROM shops s left join comments c on c.emob_id_to = s.emob_id  WHERE s.status!='suspend' AND s.community_id = ? and s.sort = ? group by s.shop_id order by s.shop_id ASC ";
			// sql =
			// "SELECT * FROM shops WHERE sort = ? order by shop_id desc ";
			page = this.getData4Page(sql, new Object[] {  communityId,
					text }, pageNum, pageSize, navNum, Shops.class);
		}

		return page;
	}

	@Override
	public Page<Shops> findNoneWordShops(Integer communityId, String status,
			Integer pageNum, Integer pageSize, Integer navNum) throws Exception {
		Integer month = DateUtils.getThisMonth();
		String sql = null;
		Page<Shops> page = null;
		sql = "SELECT (sum(case when o.order_month = ? then 1 else 0 end)) as order_sum  , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
				+

				" s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score)) as score  ,s.business_start_time,s.business_end_time  FROM shops s left join orders o on s.emob_id = o.emob_id_shop  left join comments c on c.emob_id_to = s.emob_id  WHERE s.community_id = ? and  s.status=? group by s.shop_id order by s.shop_id desc ";
		// sql = "SELECT * FROM shops WHERE sort = ? order by shop_id desc ";
		page = this.getData4Page(sql,
				new Object[] { month, communityId, status }, pageNum, pageSize,
				navNum, Shops.class);

		return page;
	}

	@Override
	public String getShopsSortWithUserId(Integer userId) throws Exception {
		String sql = "select sort from shops where emob_id = (select emob_id from users where user_id = ?)";
		List<String> list = jdbcTemplate.queryForList(sql, String.class,
				new Object[] { userId });
		String str = null;
		if (list != null && list.size() > 0) {
			str = list.get(0);
		}
		// String str = this.getValue(sql, String.class, new Object[]{userId});
		return str;
	}

	@Override
	public Shops getShopsByShopId(Integer shopId) throws Exception {
		Integer month = DateUtils.getThisMonth();

		String sql = "SELECT (sum(case when o.order_month = ? then 1 else 0 end)) as order_sum  , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
				+ " s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score)) as score  ,s.business_start_time,s.business_end_time "
				+ " FROM shops s left join orders o on s.emob_id = o.emob_id_shop left join comments c on c.emob_id_to = s.emob_id WHERE s.shop_id = ?  ";
		List<Shops> list = this.getList(sql, new Object[] { month, shopId },
				Shops.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer getShopsIdByEmobId(String emobId) throws Exception {
		String sql = "SELECT shop_id FROM shops WHERE emob_id = ? ";
		List<Number> list = jdbcTemplate.queryForList(sql, Number.class, new Object[] { emobId });
		return list != null && list.size() > 0 ? list.get(0).intValue() : null;
	}

	@Override
	public Shops getShopsByFid(String authCode) throws Exception {
		String sql = " SELECT 0 as order_sum , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
				+ " s.status , s.sort , s.create_time , s.auth_code , s.community_id , 0 as score  ,s.business_start_time,s.business_end_time  FROM shops s WHERE s.auth_code = ? ";
		List<Shops> list = this.getList(sql, new Object[] { authCode }, Shops.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer addShops(Shops shops) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(shops, key);
		return key.getId();
	}

	@Override
	public Page<Shops> findAllBySortAndCommunityId(Integer communityId, String sort, Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT 0 as order_sum , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
			+ " s.status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score),0) as score ,s.business_start_time,s.business_end_time FROM shops s left join comments c"
			+ " on s.emob_id = c.emob_id_to WHERE s.community_id = ? AND s.sort = ?  AND s.status = 'normal' GROUP BY s.shop_id ORDER BY s.status , score";
		return this.getData4Page(sql, new Object[] { communityId, sort }, pageNum, pageSize, nvm, Shops.class);
	}

	@Override
	public Shops getShopsByEmobId(String emobId) throws Exception {
		String sql = " SELECT 0 as order_sum , s.shop_id , s.shop_name , s.shops_desc , s.address , s.phone , s.emob_id , s.logo , "
			+ " case when s.status = 'ongoing' then 'normal' else s.status end as status , s.sort , s.create_time , s.auth_code , s.community_id , round(avg(c.score),0) as score  ,s.business_start_time,s.business_end_time  FROM shops s left join comments c on s.emob_id = c.emob_id_to WHERE s.emob_id = ?";
		List<Shops> list = null;
		try {
			list = this.getList(sql, new Object[] { emobId }, Shops.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询 黄页 商家列表
	 */
	public Page<ShopsPhone> findAllLikeShopsCommunityId(Integer communityId,
			String sort, Integer pageNum, Integer pageSize, Integer nvm,
			String shop) throws Exception {
		String shopName = "%" + shop + "%";
		if (shop == null || shop.equals("null")) {
			shopName = "%%";
		}

		String sql = " SELECT s.community_id,s.shop_id,s.shop_name,s.phone,s.logo,s.business_start_time,s.business_end_time FROM shops s " +
				" WHERE s.status!='suspend' AND s.community_id = ? AND s.sort = ? AND s.shop_name like ? ORDER BY s.shop_id  DESC";
		return this.getData4Page(sql, new Object[] { communityId, sort, shopName }, pageNum, pageSize, nvm, ShopsPhone.class);
	}

	/**
	 * 删除商家表
	 */
	@Override
	public boolean delShops(Long id) {
		Shops shops = new Shops();
		shops.setShopId(id);
		shops.setStatus("suspend");
		try {
			this.updateShops(shops);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public ShopLimit shopLimit(Integer communityId, String sort) throws Exception {
		String sql="SELECT s.shop_id,s.deliver_limit,s.delivery_time as deliver_time FROM community_shops cs LEFT JOIN shops s ON s.shop_id=cs.shop_id WHERE cs.community_id=?";
		List<ShopLimit> list = this.getList(sql, new Object[] { communityId }, ShopLimit.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer upShop(String shopId, String deliverLimit, String deliverTime) throws Exception {
		List<Object> list = new ArrayList<Object>(4);
		list.add(deliverLimit);
		list.add(deliverTime);
		list.add(System.currentTimeMillis() / 1000L);
		list.add(shopId);

		return this.updateData("UPDATE shops SET deliver_limit=?,delivery_time=?,update_time=? WHERE shop_id = ?", list, null);
	}
}