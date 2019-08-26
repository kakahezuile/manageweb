package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xj.bean.UserBean;
import com.xj.dao.UserDao;

public class UserDaoImpl implements UserDao {

	private static Logger logger= Logger.getLogger(UserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(UserBean userBean) {
		int id = 0;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "insert into user(phonenumber,password,realname,birthday,sex,job,address,type) values(?,?,?,?,?,?,?,?)";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setString(index++, userBean.getPhoneNumber());
			preparedStatement.setString(index++, userBean.getPassWord());
			preparedStatement.setString(index++, userBean.getRealName());
			preparedStatement.setDate(index++, new java.sql.Date(userBean.getBirthday().getTime()));
			preparedStatement.setString(index++, userBean.getSex()+"");
			preparedStatement.setString(index++, userBean.getJob());
			preparedStatement.setString(index++, userBean.getAddress());
			preparedStatement.setString(index++, userBean.getType());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
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

	public boolean update(UserBean userBean) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		String sql = "update user set userid=userid,phonenumber=?,password=?,realname=?,birthday=?,sex=?,job=?,address=?,type=?,createtime=createtime where userid=?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setString(index++, userBean.getPhoneNumber());
			preparedStatement.setString(index++, userBean.getPassWord());
			preparedStatement.setString(index++, userBean.getRealName());
			preparedStatement.setDate(index++, new java.sql.Date(userBean.getBirthday().getTime()));
			preparedStatement.setInt(index++, userBean.getSex());
			preparedStatement.setString(index++, userBean.getJob());
			preparedStatement.setString(index++, userBean.getAddress());
			preparedStatement.setString(index++, userBean.getType());
			preparedStatement.setInt(index++, userBean.getUserId());
			int count = preparedStatement.executeUpdate();
			if (count > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
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
		return flag;
	}

	public boolean deleteById(Integer id) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		String sql = "delete from user where userid=?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setInt(index++, id);
			int count = preparedStatement.executeUpdate();
			if (count > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
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
		return flag;
	}

	public UserBean findById(Integer id) {
		UserBean userBean = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select userid,phonenumber,password,realname,birthday,sex,job,address,type,createtime from user where userid=?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setInt(index++, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userBean = new UserBean();
				userBean.setUserId(resultSet.getInt("id"));
				userBean.setPhoneNumber(resultSet.getString("phonenumber"));
				userBean.setPassWord(resultSet.getString("password"));
				userBean.setRealName(resultSet.getString("realname"));
				userBean.setBirthday(new java.util.Date (resultSet.getDate("birthday").getTime()));
				userBean.setSex(resultSet.getInt("sex"));
				userBean.setJob(resultSet.getString("job"));
				userBean.setAddress(resultSet.getString("address"));
				userBean.setType(resultSet.getString("type"));
				userBean.setCreateTime(resultSet.getTimestamp("createtime"));
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
		return userBean;
	}

	public List<UserBean> findAllByType(String type) {
		List<UserBean> userBeans=new ArrayList<UserBean>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select userid,phonenumber,password,realname,birthday,sex,job,address,type,createtime from user where type=?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index=1;
			preparedStatement.setString(index, type);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserBean userBean = new UserBean();
				userBean.setUserId(resultSet.getInt("userid"));
				userBean.setPhoneNumber(resultSet.getString("phonenumber"));
				userBean.setPassWord(resultSet.getString("password"));
				userBean.setRealName(resultSet.getString("realname"));
				userBean.setBirthday(new java.util.Date (resultSet.getDate("birthday").getTime()));
				userBean.setSex(resultSet.getInt("sex"));
				userBean.setJob(resultSet.getString("job"));
				userBean.setAddress(resultSet.getString("address"));
				userBean.setType(resultSet.getString("type"));
				userBean.setCreateTime(resultSet.getTimestamp("createtime"));
				userBeans.add(userBean);
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
		return userBeans;
	}

	public UserBean findByUserName(String userName) {
		UserBean userBean = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select user_id,user_name,password,nickname,phone,gender,age,avatar,idcard,idnumber,create_time from users where user_name = ?";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			int index = 1;
			preparedStatement.setString(index++, userName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userBean = new UserBean();
				userBean.setUserId(resultSet.getInt("id"));
				userBean.setPhoneNumber(resultSet.getString("phonenumber"));
				userBean.setPassWord(resultSet.getString("password"));
				userBean.setRealName(resultSet.getString("realname"));
				userBean.setBirthday(new java.util.Date (resultSet.getDate("birthday").getTime()));
				userBean.setSex(resultSet.getInt("sex"));
				userBean.setJob(resultSet.getString("job"));
				userBean.setAddress(resultSet.getString("address"));
				userBean.setType(resultSet.getString("type"));
				userBean.setCreateTime(resultSet.getTimestamp("createtime"));
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
		return userBean;
	}
}