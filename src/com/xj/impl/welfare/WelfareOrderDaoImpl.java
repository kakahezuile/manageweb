package com.xj.impl.welfare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.xj.bean.welfare.WelfareOrder;
import com.xj.dao.welfare.WelfareOrderDao;
import com.xj.impl.MyBaseDaoImpl;

@Repository("welfareOrderDao")
public class WelfareOrderDaoImpl extends MyBaseDaoImpl implements WelfareOrderDao {

	@Override
	public List<WelfareOrder> getWelfareOrders(Integer welfareId) throws Exception {
		return this.getList("SELECT o.welfare_order_id,o.welfare_id,o.code,o.status,o.emob_id,o.money,o.bonus_coin,o.share,u.nickname,u.username,u.avatar FROM welfare_order o LEFT JOIN users u ON o.emob_id=u.emob_id WHERE o.welfare_id=? AND o.status<>'unpaid'", new Object[] {welfareId}, WelfareOrder.class);
	}

	@Override
	public WelfareOrder getWelfareOrder(Integer welfareOrderId) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getJdbcTemplate().getDataSource().getConnection();
			
			statement = connection.prepareStatement("SELECT o.welfare_order_id,o.welfare_id,o.code,o.status,o.emob_id,o.money,o.bonus_coin,o.share,u.nickname,u.username,u.avatar FROM welfare_order o LEFT JOIN users u ON o.emob_id=u.emob_id WHERE o.welfare_order_id=? AND o.status<>'unpaid'");
			statement.setInt(1, welfareOrderId);
			
			resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			
			WelfareOrder welfareOrder = new WelfareOrder();
			welfareOrder.setWelfareOrderId(welfareOrderId);
			welfareOrder.setWelfareId(resultSet.getInt("welfare_id"));
			welfareOrder.setCode(resultSet.getString("code"));
			welfareOrder.setStatus(resultSet.getString("status"));
			welfareOrder.setEmobId(resultSet.getString("emob_id"));
			welfareOrder.setMoney(resultSet.getBigDecimal("money"));
			welfareOrder.setBonusCoin(resultSet.getInt("bonus_coin"));
			welfareOrder.setShare(resultSet.getString("share"));
			welfareOrder.setNickname(resultSet.getString("nickname"));
			welfareOrder.setUsername(resultSet.getString("username"));
			welfareOrder.setAvatar(resultSet.getString("avatar"));
			
			return welfareOrder;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean refund(Integer welfareOrderId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		PreparedStatement bonusCoinHistroyStatement = null;
		PreparedStatement userBonusCoinStatement = null;
		PreparedStatement welfareOrderStatement = null;
		
		try {
			Integer now = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
			
			connection = getJdbcTemplate().getDataSource().getConnection();
			connection.setAutoCommit(false);
			
			statement = connection.prepareStatement("SELECT emob_id,bonus_coin FROM welfare_order WHERE welfare_order_id=? AND status='paid'");
			statement.setInt(1, welfareOrderId);
			
			resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			
			int bonusCoin = resultSet.getInt("bonus_coin");
			if (bonusCoin > 0) {
				String emobId = resultSet.getString("emob_id");
				
				bonusCoinHistroyStatement = connection.prepareStatement("INSERT INTO bonuscoin_history(create_time,bonuscoin_count,bonuscoin_source,emob_id) values (?,?,?,?)");
				bonusCoinHistroyStatement.setInt(1, now);
				bonusCoinHistroyStatement.setInt(2, bonusCoin);
				bonusCoinHistroyStatement.setString(3, "welfare");
				bonusCoinHistroyStatement.setString(4, emobId);
				bonusCoinHistroyStatement.execute();
				
				userBonusCoinStatement = connection.prepareStatement("UPDATE users SET bonuscoin_count=bonuscoin_count+? WHERE emob_id=?");
				userBonusCoinStatement.setInt(1, bonusCoin);
				userBonusCoinStatement.setString(2, emobId);
				if (1 != userBonusCoinStatement.executeUpdate()) {
					throw new RuntimeException("更新用户帮帮币失败:" + emobId);
				}
			}
			
			welfareOrderStatement = connection.prepareStatement("UPDATE welfare_order SET status=?,modify_time=? WHERE welfare_order_id=?");
			welfareOrderStatement.setString(1, "refunded");
			welfareOrderStatement.setInt(2, now);
			welfareOrderStatement.setInt(3, welfareOrderId);
			if (1 != welfareOrderStatement.executeUpdate()) {
				throw new RuntimeException("修改福利订单状态失败:" + welfareOrderId);
			}
			
			connection.commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
			if (null != connection) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (null != welfareOrderStatement) {
				try {
					welfareOrderStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != userBonusCoinStatement) {
				try {
					userBonusCoinStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != bonusCoinHistroyStatement) {
				try {
					bonusCoinHistroyStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	@Override
	public String[] getWelfareOrderUsers(Integer welfareId, String status) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getJdbcTemplate().getDataSource().getConnection();
			
			String sql = null;
			if (StringUtils.isBlank(status)) {
				status = "unpaid";
				sql = "SELECT emob_id FROM welfare_order WHERE welfare_id=? AND status<>?";
			} else {
				sql = "SELECT emob_id FROM welfare_order WHERE welfare_id=? AND status=?";
			}
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, welfareId);
			statement.setString(2, status);
			
			resultSet = statement.executeQuery();
			
			Set<String> userSet = new HashSet<String>();
			while (resultSet.next()) {
				userSet.add(resultSet.getString("emob_id"));
			}
			
			String[] users = new String[userSet.size()];
			userSet.toArray(users);
			return users;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}