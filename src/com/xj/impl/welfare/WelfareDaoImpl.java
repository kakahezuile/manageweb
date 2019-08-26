package com.xj.impl.welfare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xj.bean.welfare.Welfare;
import com.xj.dao.welfare.WelfareDao;
import com.xj.httpclient.vo.MyReturnKey;
import com.xj.impl.MyBaseDaoImpl;

@Repository("welfareDao")
public class WelfareDaoImpl extends MyBaseDaoImpl implements WelfareDao {

	public Integer addWelfare(Welfare welfare) throws Exception {
		Integer modifyTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
		welfare.setModifyTime(modifyTime);
		if (null == welfare.getWelfareId()) {
			welfare.setCreateTime(modifyTime);
		}
		
		MyReturnKey key = new MyReturnKey();
		this.save(welfare, key);
		
		return key.getId();
	}

	@Override
	public boolean updateWelfare(Welfare welfare) throws Exception {
		Integer total = welfare.getTotal();
		if (null == total || total.intValue() < 1) {
			throw new IllegalArgumentException("福利上线数量为空");
		}
		
		Integer modifyTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
		welfare.setModifyTime(modifyTime);
		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getJdbcTemplate().getDataSource().getConnection();
			statement = connection.prepareStatement("UPDATE welfare SET title=?,poster=?,content=?,charactervalues=?,rule=?,phone=?,end_time=?,price=?,provide_instruction=?,modify_time=?,remain=remain-(total-" + total + "),total=" + total + " WHERE welfare_id=?");
			statement.setString(1, welfare.getTitle());
			statement.setString(2, welfare.getPoster());
			statement.setString(3, welfare.getContent());
			statement.setInt(4, welfare.getCharactervalues());
			statement.setString(5, welfare.getRule());
			statement.setString(6, welfare.getPhone());
			statement.setInt(7, welfare.getEndTime());
			statement.setBigDecimal(8, welfare.getPrice());
			statement.setString(9, welfare.getProvideInstruction());
			statement.setInt(10, welfare.getModifyTime());
			statement.setInt(11, welfare.getWelfareId());
			
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != statement) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	@Override
	public List<Welfare> getWelfares(Integer communityId) throws Exception {
		final Map<Integer, Welfare> welfareMap = new HashMap<Integer, Welfare>();
		
		String sql = "SELECT w.*,o.welfare_order_id,o.status AS welfare_order_status FROM welfare w LEFT JOIN welfare_order o ON w.welfare_id=o.welfare_id WHERE w.community_id=? AND w.status<>?";
		getJdbcTemplate().query(sql, new Object[] {communityId, STATUS_DELETE}, new RowMapper<Welfare>() {
			@Override
			public Welfare mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer welfareId = Integer.valueOf(rs.getInt("welfare_id"));
				Welfare welfare = welfareMap.get(welfareId);
				if (null == welfare) {
					welfare = resolveResultSet(rs);
					welfareMap.put(welfareId, welfare);
				}
				
				return fillWelfareOrderInfo(welfare, rs);
			}
		});
		
		List<Welfare> welfares = new ArrayList<Welfare>();
		welfares.addAll(welfareMap.values());
		
		Collections.sort(welfares, new Comparator<Welfare>() {
			@Override
			public int compare(Welfare o1, Welfare o2) {
				int score1 = STATUS_ONGOING.equals(o1.getStatus()) ? 0 : 10;
				int score2 = STATUS_ONGOING.equals(o2.getStatus()) ? 0 : 10;
				
				if (o1.getEndTime() > o2.getEndTime()) {
					score1 -= 1;
				} else if (o1.getEndTime() < o2.getEndTime()) {
					score2 -= 1;
				}
				
				return score1 - score2;
			}
		});
		
		return welfares;
	}

	private Welfare fillWelfareOrderInfo(Welfare welfare, ResultSet rs) throws SQLException {
		if (null == welfare) {
			return welfare;
		}
		
		Integer welfareOrderId = rs.getInt("welfare_order_id");
		if (null != welfareOrderId && welfareOrderId.intValue() > 0) {
			String welfareOrderStatus = rs.getString("welfare_order_status");
			if ("unpaid".equals(welfareOrderStatus)) {
				welfare.setOrderUnpaid(welfare.getOrderUnpaid().intValue() + 1);
			} else if ("paid".equals(welfareOrderStatus)) {
				welfare.setOrderPaid(welfare.getOrderPaid().intValue() + 1);
			} else if ("refunded".equals(welfareOrderStatus)) {
				welfare.setOrderRefunded(welfare.getOrderRefunded().intValue() + 1);
			}
		}
		
		return welfare;
	}

	@Override
	public Welfare getWelfare(Integer welfareId) throws Exception {
		return getJdbcTemplate().query("SELECT * FROM welfare WHERE welfare_id = ? AND status <> ?", new Object[] {welfareId, STATUS_DELETE}, new ResultSetExtractor<Welfare>() {
			@Override
			public Welfare extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next()) {
					return null;
				}
				
				return resolveResultSet(rs);
			}
		});
	}

	private Welfare resolveResultSet(ResultSet rs) throws SQLException {
		Welfare welfare = new Welfare();
		welfare.setWelfareId(rs.getInt("welfare_id"));
		welfare.setTitle(rs.getString("title"));
		welfare.setPoster(rs.getString("poster"));
		welfare.setContent(rs.getString("content"));
		welfare.setCharactervalues(rs.getInt("charactervalues"));
		welfare.setTotal(rs.getInt("total"));
		welfare.setRule(rs.getString("rule"));
		welfare.setPhone(rs.getString("phone"));
		welfare.setCommunityId(rs.getInt("community_id"));
		welfare.setStatus(rs.getString("status"));
		welfare.setStartTime(rs.getInt("start_time"));
		welfare.setEndTime(rs.getInt("end_time"));
		welfare.setPrice(rs.getBigDecimal("price"));
		welfare.setProvideInstruction(rs.getString("provide_instruction"));
		welfare.setCreateTime(rs.getInt("create_time"));
		welfare.setModifyTime(rs.getInt("modify_time"));
		welfare.setReason(rs.getString("reason"));
		
		return welfare;
	}

	@Override
	public boolean deleteWelfare(final Integer welfareId) {
		Connection connection = null;
		PreparedStatement welfareOrderCountStatement = null;
		ResultSet welfareOrderCountResultSet = null;
		PreparedStatement welfareDeleteStatement = null;
		
		try {
			connection = getJdbcTemplate().getDataSource().getConnection();
			welfareOrderCountStatement = connection.prepareStatement("SELECT COUNT(1) FROM welfare_order WHERE welfare_id=?");
			welfareOrderCountStatement.setInt(1, welfareId);
			welfareOrderCountResultSet = welfareOrderCountStatement.executeQuery();
			if (welfareOrderCountResultSet.next() && welfareOrderCountResultSet.getInt(1) > 0) {
				return false;
			}
			
			welfareDeleteStatement = connection.prepareStatement("UPDATE welfare SET status=? WHERE welfare_id=? AND status=?");
			welfareDeleteStatement.setString(1, STATUS_DELETE);
			welfareDeleteStatement.setInt(2, welfareId);
			welfareDeleteStatement.setString(3, STATUS_OFFLINE);
			
			return welfareDeleteStatement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != welfareDeleteStatement) {
				try {
					welfareDeleteStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != welfareOrderCountResultSet) {
				try {
					welfareOrderCountResultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != welfareOrderCountStatement) {
				try {
					welfareOrderCountStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	@Override
	public String online(Integer communityId, Integer welfareId) {
		Connection connection = null;
		PreparedStatement onlineWelfareCountStatement = null;
		ResultSet onlineWelfareCountResultSet = null;
		PreparedStatement onlineWelfareStatement = null;
		
		try {
			int currentTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
			
			connection = getJdbcTemplate().getDataSource().getConnection();
			onlineWelfareCountStatement = connection.prepareStatement("SELECT COUNT(1) FROM welfare WHERE community_id=? AND end_time>? AND status=?");
			onlineWelfareCountStatement.setInt(1, communityId);
			onlineWelfareCountStatement.setInt(2, currentTime);
			onlineWelfareCountStatement.setString(3, STATUS_ONGOING);
			
			onlineWelfareCountResultSet = onlineWelfareCountStatement.executeQuery();
			if (onlineWelfareCountResultSet.next() && onlineWelfareCountResultSet.getInt(1) > 0) {
				return "该小区已经有福利了";
			}
			
			onlineWelfareStatement = connection.prepareStatement("UPDATE welfare SET status=?,start_time=? WHERE welfare_id=? AND status=? AND end_time>?");
			onlineWelfareStatement.setString(1, STATUS_ONGOING);
			onlineWelfareStatement.setInt(2, currentTime);
			onlineWelfareStatement.setInt(3, welfareId);
			onlineWelfareStatement.setString(4, STATUS_OFFLINE);
			onlineWelfareStatement.setInt(5, currentTime);
			
			boolean result = onlineWelfareStatement.executeUpdate() == 1;
			if (result) {
				return "success";
			}
			
			return "请检查该福利的结束时间是否已经到了";
		} catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		} finally {
			if (null != onlineWelfareCountStatement) {
				try {
					onlineWelfareCountStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != onlineWelfareCountResultSet) {
				try {
					onlineWelfareCountResultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != onlineWelfareStatement) {
				try {
					onlineWelfareStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean offline(Integer welfareId) {
		Connection connection = null;
		PreparedStatement welfareOrderCountStatement = null;
		ResultSet welfareOrderCountResultSet = null;
		PreparedStatement offlineWelfareStatement = null;
		
		try {
			connection = getJdbcTemplate().getDataSource().getConnection();
			welfareOrderCountStatement = connection.prepareStatement("SELECT COUNT(1) FROM welfare_order WHERE welfare_id=?");
			welfareOrderCountStatement.setInt(1, welfareId);
			welfareOrderCountResultSet = welfareOrderCountStatement.executeQuery();
			if (welfareOrderCountResultSet.next() && welfareOrderCountResultSet.getInt(1) > 0) {
				return false;
			}
			
			offlineWelfareStatement = connection.prepareStatement("UPDATE welfare SET status=? WHERE welfare_id=? AND status=?");
			offlineWelfareStatement.setString(1, STATUS_OFFLINE);
			offlineWelfareStatement.setInt(2, welfareId);
			offlineWelfareStatement.setString(3, STATUS_ONGOING);
			
			return offlineWelfareStatement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != offlineWelfareStatement) {
				try {
					offlineWelfareStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != welfareOrderCountResultSet) {
				try {
					welfareOrderCountResultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != welfareOrderCountStatement) {
				try {
					welfareOrderCountStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean fail(final Integer welfareId, final String reason) {
		return 1 == getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement statement = conn.prepareStatement("UPDATE welfare SET status=?,reason=? WHERE welfare_id=? AND status IN(?,?)");
				statement.setString(1, STATUS_FAILURE);
				statement.setString(2, reason);
				statement.setInt(3, welfareId);
				statement.setString(4, STATUS_ONGOING);
				statement.setString(5, STATUS_SUCCESS);
				
				return statement;
			}
		});
	}

	@Override
	public boolean succeed(final Integer welfareId) {
		return 1 == getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement statement = conn.prepareStatement("UPDATE welfare SET status=?,reason=null WHERE welfare_id=? AND status IN(?,?)");
				statement.setString(1, STATUS_SUCCESS);
				statement.setInt(2, welfareId);
				statement.setString(3, STATUS_ONGOING);
				statement.setString(4, STATUS_FAILURE);
				
				return statement;
			}
		});
	}
}