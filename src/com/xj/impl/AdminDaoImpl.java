package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Admin;
import com.xj.bean.TryOut;
import com.xj.bean.Users;
import com.xj.dao.AdminDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("adminDao")
public class AdminDaoImpl extends MyBaseDaoImpl implements AdminDao{

	@Override
	public Integer insert(Admin admin) throws Exception {
		MyReturnKey myKey = new MyReturnKey();
		this.save(admin, myKey);
		return myKey.getId();
	}

	@Override
	public Admin getAdmin(String userName) throws Exception {
		String sql = "SELECT * FROM admin WHERE username = ? ";
		List<Admin> list = this.getList(sql, new String[]{userName}, Admin.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer update(Admin admin) throws Exception {
		String sql = "UPDATE admin SET password = ? WHERE username = ?";
		List<Object> list = new ArrayList<Object>();
		list.add(admin.getPassword());
		list.add(admin.getUsername());
		return this.updateData(sql,list ,null);
	}

	@Override
	public List<Admin> getAdminList() throws Exception {
		String sql = "SELECT * FROM admin WHERE role != 'super' ORDER BY admin_id DESC ";
		return this.getList(sql , null , Admin.class);
	}

	@Override
	public List<Admin> getAdminList(Integer communityId , String adminStatus) throws Exception {
		String sql = " SELECT * FROM admin WHERE community_id = ? and admin_status = ? limit 1";
		return this.getList(sql, new Object[]{communityId,adminStatus}, Admin.class);
	}

	@Override
	public Admin getAdminByEmobId(String emobId) throws Exception {
		String sql = "SELECT * FROM admin WHERE emob_id = ? ";
		List<Admin> list = this.getList(sql, new String[]{emobId}, Admin.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Admin getAdminById(Integer adminId) throws Exception {
		String sql = "SELECT * FROM admin WHERE admin_id = ? ";
		List<Admin> list = this.getList(sql, new Object[]{adminId}, Admin.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Admin> getAdminListByRole(String role) throws Exception {
		String sql = " SELECT * FROM admin WHERE role = ? ";
		return this.getList(sql, new Object[]{role}, Admin.class);
	}

	@Override
	public Users getUser(String username) throws Exception {
		String sql = "SELECT * FROM users WHERE username = ? ";
		List<Users> list = this.getList(sql, new String[]{username}, Users.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer insertUser(Users user) throws Exception {
		MyReturnKey myKey = new MyReturnKey();
		this.save(user, myKey);
		return myKey.getId();
	}

	@Override
	public Integer insertTryOut(TryOut to) throws Exception {
		MyReturnKey myKey = new MyReturnKey();
		this.save(to, myKey);
		return myKey.getId();
	}

	@Override
	public boolean isUniqueKefu(Integer communityId, Integer adminId, String adminStatus) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			String sql = "SELECT COUNT(1) FROM admin WHERE community_id = ? AND admin_id <> ? AND admin_status = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, communityId);
			preparedStatement.setInt(2, adminId);
			preparedStatement.setString(3, adminStatus);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) == 0;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateAdmin(Admin admin) throws Exception {
		Object resultObject[] = DaoUtils.reflect(admin);
		if (resultObject == null || resultObject[1] == null || ((List<?>)resultObject[1]).size() == 0) {
			return false;
		}
		
		String sql = "UPDATE admin SET ";
		sql += (String) resultObject[0];
		sql += " WHERE admin_id = ? ";
		
		List<Object> list = (List<Object>)resultObject[1];
		list.add(admin.getAdminId());
		
		return this.updateData(sql, list, null) > 0;
	}

	@Override
	public boolean updateAdminAsKefu(Admin admin) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			String sql = "UPDATE admin SET avatar=?, admin_status=?, nickname=?, start_time=?, end_time=? WHERE admin_id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, admin.getAvatar());
			preparedStatement.setString(2, admin.getAdminStatus());
			preparedStatement.setString(3, admin.getNickname());
			preparedStatement.setString(4, admin.getStartTime());
			preparedStatement.setString(5, admin.getEndTime());
			preparedStatement.setInt(6, admin.getAdminId());
			
			return preparedStatement.executeUpdate() > 0;
		} finally {
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
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