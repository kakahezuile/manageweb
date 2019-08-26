package com.xj.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.Config;
import com.xj.dao.ConfigDao;

@Repository("configDaoImpl")
public class ConfigDaoImpl extends MyBaseDaoImpl implements ConfigDao {

	@Override
	public void add(final Config config) {
		this.getJdbcTemplate().update("INSERT INTO xj_config(`key`,`value`,`comment`) VALUES (?, ?, ?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, config.getKey());
				ps.setString(2, config.getValue());
				ps.setString(3, null == config.getComment() ? "" : config.getComment());
			}
		});
	}
	
	@Override
	public void update(final Config config) {
		this.getJdbcTemplate().update("UPDATE xj_config SET `value`=?,`comment`=? WHERE `key`=?", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, config.getValue());
				ps.setString(2, null == config.getComment() ? "" : config.getComment());
				ps.setString(3, config.getKey());
			}
		});
	}

	@Override
	public Config get(final String key) {
		return this.getJdbcTemplate().query("SELECT * FROM xj_config WHERE `key`=?", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, key);
			}
		}, new ResultSetExtractor<Config>() {
			@Override
			public Config extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next()) {
					return null;
				}
				
				Config config = new Config();
				config.setKey(key);
				config.setValue(rs.getString("value"));
				config.setComment(rs.getString("comment"));
				return config;
			}
		});
	}
}