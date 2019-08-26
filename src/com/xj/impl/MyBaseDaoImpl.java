package com.xj.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.bean.Key;
import com.xj.bean.Page;
import com.xj.bean.TableDesc;
import com.xj.bean.UserBonus;
import com.xj.dao.MyBaseDao;
import com.xj.dao.ReturnKeyList;
import com.xj.exception.PrimaryKeySizeException;
import com.xj.exception.PrimaryKeyValueNullException;
import com.xj.utils.BeanUtil;

/**
 * 
 * @author dongquan
 * 
 * @param <Entity>
 */
@Repository("myBaseDao")
public class MyBaseDaoImpl implements MyBaseDao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Object obj, ReturnKeyList returnKeyList) throws Exception {

	

		Class<?> clazz = obj.getClass();
		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";

		Map<String, TableDesc> primaryKeys = this.getPrimaryKeys(clazz);

		

		StringBuilder sb = new StringBuilder("insert into " + tableName + "(");

		StringBuilder sb2 = new StringBuilder();

		Field[] fields = clazz.getDeclaredFields();

		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if ("serialVersionUID".equals(fieldName) || fields[i].isAnnotationPresent(NotInsertAnnotation.class))
				continue;

			char ch = fieldName.charAt(0);
			if (ch == '_') {
				fieldName = fieldName.substring(1);
			}
			fieldName = BeanUtil.changeName(fieldName).toLowerCase();

			Method m = clazz.getDeclaredMethod(BeanUtil.getGetter(fields[i]
					.getName()));
			Object value = m.invoke(obj);
			if (primaryKeys.containsKey(fieldName)
					&& primaryKeys.get(fieldName).isAutoIncrement()
					&& value == null)
				continue;

			sb.append(fieldName);
			params.add(value);
			sb2.append("?");

			if (i < fields.length) {
				sb.append(",");
				sb2.append(",");
			}
		}
		while (sb.charAt(sb.length() - 1) == ',') {
			sb.delete(sb.length() - 1, sb.length());
		}
		while (sb2.charAt(sb2.length() - 1) == ',') {
			sb2.delete(sb2.length() - 1, sb2.length());
		}
		sb.append(") values (" + sb2 + ")");
	
		return this.updateData(sb.toString(), params, returnKeyList);
	}

	@Override
	public int update(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";

		Map<String, TableDesc> primaryKeys = this.getPrimaryKeys(clazz);

		StringBuilder sb = new StringBuilder("update " + tableName + " ");

		sb.append("set  ");
		Field[] fields = clazz.getDeclaredFields();
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {

			String fieldName = fields[i].getName();

			if ("serialVersionUID".equals(fieldName))
				continue;
			if (primaryKeys.containsKey(fieldName)
					&& primaryKeys.get(fieldName).isAutoIncrement())
				continue;

			char ch = fieldName.charAt(0);
			if (ch == '_') {
				fieldName = fieldName.substring(1);
			}
			fieldName = BeanUtil.changeName(fieldName).toLowerCase();

			sb.append(fieldName + "=?");

			Method m = clazz.getDeclaredMethod(BeanUtil.getGetter(fields[i]
					.getName()));
			params.add(m.invoke(obj));

			if (i < fields.length - 1) {
				sb.append(",");
			}
		}

		sb.append(" where 1=1 ");

		Set<String> keySet = primaryKeys.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			sb.append(" and " + key + "=? ");

			String fieldName = BeanUtil.changeName(key);
			fieldName = fieldName.substring(0, 1).toLowerCase()
					+ fieldName.substring(1);

			char ch = fieldName.charAt(0);
			if (ch > '0' && ch < '9') {
				fieldName = "_" + fieldName;
			}
			Method m = clazz.getDeclaredMethod(BeanUtil.getGetter(fieldName));
			Object value = m.invoke(obj);
			if (value == null) {
				throw new PrimaryKeyValueNullException(clazz.getName()
						+ " 对应表中的主键  " + fieldName + " 的值为 null");
			} else {
				params.add(value);
			}
		}

		return this.updateData(sb.toString(), params, null);
	}

	@Override
	public int delete(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";

		Map<String, TableDesc> primaryKeys = this.getPrimaryKeys(clazz);

		StringBuilder sb = new StringBuilder("delete from " + tableName
				+ " where 2>1 ");

		Set<String> keySet = primaryKeys.keySet();
		List<Object> params = new ArrayList<Object>();

		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = iterator.next();

			String fieldName = BeanUtil.changeName(key);
			// fieldName = fieldName.substring(0,
			// 1).toLowerCase()+fieldName.substring(1);

			char ch = fieldName.charAt(0);
			if (ch > '0' && ch < '9') {
				fieldName = "_" + fieldName;
			}

			Method m = obj.getClass().getDeclaredMethod(
					BeanUtil.getGetter(fieldName));
			Object value = m.invoke(obj);
			if (value == null) {
				throw new PrimaryKeyValueNullException(clazz.getName()
						+ " 对应表中的主键  " + fieldName + " 的值为 null");
			} else {
				params.add(value);
			}
			sb.append("and " + key + "=? ");
		}

		return this.updateData(sb.toString(), params, null);
	}

	@Override
	public <Entity> Entity getObjByPK(Object[] pk, Class<Entity> clazz)
			throws Exception {

		if (pk == null) {
			throw new NullPointerException("传入的主键数组为 null");
		}

		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";
		Map<String, TableDesc> primaryKeys = this.getPrimaryKeys(clazz);

		StringBuilder sql = new StringBuilder("select * from " + tableName
				+ " where 1=1 ");
		Set<String> keySet = primaryKeys.keySet();

		if (pk.length != keySet.size()) {
			throw new PrimaryKeySizeException("传入的主键个数不合法，你传入了 " + pk.length
					+ "个主键值，实际上应该传入 " + keySet.size() + "个主键值");
		}

		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			sql.append("and " + key + "=? ");
		}
		List<Entity> list = this.getList(sql.toString(), pk, clazz);
		return list == null ? null : list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public <Entity> List<Entity> getList(String sql, Object[] params,
			final Class<Entity> clazz) throws Exception {
	
		List<Entity> list = new ArrayList<Entity>();
		list = this.getJdbcTemplate().query(sql, params,
				new RowMapper<Entity>() {

					@Override
					public Entity mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Entity obj = null;
						try {
							obj = clazz.newInstance();

							Field[] fields = clazz.getDeclaredFields();

							for (Field field : fields) {
								String fieldName = field.getName();
								if ("serialVersionUID".equals(fieldName))
									continue;
								if (field
										.isAnnotationPresent(MyAnnotation.class))
									continue; // 当前注解内容部分 不予查询

								Class<?> fieldType = field.getType();

								Method m = clazz.getMethod(
										BeanUtil.getSetter(fieldName),
										new Class[] { fieldType });

								String columnName = BeanUtil.changeName(
										fieldName).toLowerCase();
								Object val = null;

								if (fieldType == Integer.class
										|| fieldType == int.class) {
									val = rs.getInt(columnName);
								} else if (Double.class == fieldType) {
									val = rs.getDouble(columnName);
								} else {
									val = rs.getObject(columnName);
								}

								Object value = BeanUtil.convertType(val,
										field.getType());
								m.invoke(obj, value);

							}

						} catch (Exception e) {
//							e.printStackTrace();
							// throw new RuntimeException(e);
						}

						return obj;
					}
				});
		return list;
	}

	@Override
	public <Entity> Entity getValue(String sql, Class<Entity> clazz,
			Object... params) throws Exception {
		return this.getJdbcTemplate().queryForObject(sql, params, clazz);
	}

	@Override
	public <Entity> Page<Entity> getData4Page(String sql, Object[] params,
			int pageNum, int pageSize, int navNum, Class<Entity> clazz)
			throws Exception {
		String countSql = "select count(*) from (" + sql + ")tb";
		Number rowCount = getValue(countSql, Number.class, params);
		Page<Entity> page = new Page<Entity>(pageNum, rowCount.intValue(),
				pageSize, navNum);
		String pageSql = sql + "  limit ?,?";
		Object[] newparams = null;
		if (params != null) {
			newparams = new Object[params.length + 2];
			for (int i = 0; i < params.length; i++) {
				newparams[i] = params[i];
			}
			newparams[newparams.length - 2] = page.getStartRow();
			newparams[newparams.length - 1] = page.getPageSize();

		} else {
			newparams = new Object[2];
			newparams[0] = page.getStartRow();
			newparams[1] = page.getPageSize();
		}
		List<Entity> pageData = null;
		if(pageNum  > page.getNavCount() ){
			pageData = new ArrayList<Entity>();
		}else{
			pageData = this.getList(pageSql, newparams, clazz);
		}
	
		page.setPageData(pageData);
		return page;
	}

	@Override
	public List<TableDesc> getTableDesc(Class<?> clazz) throws Exception {
		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";
		String sql = "DESC " + tableName;
		List<TableDesc> list = this.getJdbcTemplate().query(sql,
				new RowMapper<TableDesc>() {
					public TableDesc mapRow(ResultSet rs, int index)
							throws SQLException {
						TableDesc tableDesc = new TableDesc();
						tableDesc.setField(rs.getString("Field"));
						tableDesc.setType(BeanUtil.sqlTyptToJavaType(rs
								.getString("Type")));
						tableDesc.setAllowNull("YES".equalsIgnoreCase(rs
								.getString("Null")));
						String key = rs.getString("Key");
						if ("PRI".equalsIgnoreCase(key)) {
							tableDesc.setKey(Key.PRI);
						} else if ("MUL".equalsIgnoreCase(key)) {
							tableDesc.setKey(Key.MUL);
						}
						tableDesc.setDefaultValue(rs.getString("Default"));
						tableDesc.setAutoIncrement("auto_increment"
								.equalsIgnoreCase(rs.getString("Extra")));
						return tableDesc;
					}
				});
		return list;
	}

	@Override
	public Map<String, TableDesc> getPrimaryKeys(Class<?> clazz)
			throws Exception {
		Map<String, TableDesc> pks = new HashMap<String, TableDesc>();
		for (TableDesc tableDesc : getTableDesc(clazz)) {
			if (tableDesc.getKey() == Key.PRI) {
				pks.put(tableDesc.getField(), tableDesc);
			}
		}
		return pks;
	}

	@Override
	public int updateData(final String sql, final List<Object> params,
			ReturnKeyList returnKeyList) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int update = this.getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(
							Connection conn) throws SQLException {
						PreparedStatement statement = conn.prepareStatement(
								sql, Statement.RETURN_GENERATED_KEYS);
						if (params != null) {
							for (int i = 0; i < params.size(); i++) {
								statement.setObject(i + 1, params.get(i));
							}
						}
						return statement;
					}
				}, keyHolder);
		if (returnKeyList != null) {
			returnKeyList.getKeyList(keyHolder.getKeyList());
		}
		return update;
	}

	@Override
	public <Entity> List<Entity> getList(Class<Entity> clazz) throws Exception {
		String tableName = "`" + BeanUtil.changeName(clazz.getSimpleName())
				+ "`";
		String sql = "select * from " + tableName;
		return getList(sql, null, clazz);
	}

	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		// if(jdbcTemplate == null){
		// jdbcTemplate = new JdbcTemplate();
		// }
		return jdbcTemplate;
	}

	public int excuteTrac(final List<UserBonus> list) {
		int temp = 0;
		// 批插入
		String sql1 = "";
		// 向第一个表插入的语句
		sql1 = "insert into user_bonus(bonus_id,user_id,start_time,expire_time,create_time,bonus_status,bonus_type) values (?,?,?,?,?,?,?)";

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				jdbcTemplate.getDataSource());
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			jdbcTemplate.batchUpdate(sql1, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					UserBonus userBonus = (UserBonus) list.get(i);
					
					ps.setInt(1, userBonus.getBonusId());
					ps.setInt(2, userBonus.getUserId());
					ps.setInt(3, userBonus.getStartTime());
					ps.setInt(4, userBonus.getExpireTime());
					ps.setInt(5, userBonus.getCreateTime());
					ps.setString(6, userBonus.getBonusStatus());
					ps.setInt(7, userBonus.getBonusType());
				}

				public int getBatchSize() {
					return list.size();
				}
			});
		} catch (Exception e) {
			System.out.println("出现事务异常");
			e.printStackTrace();
			transactionManager.rollback(status);
			temp = -1;
		} finally {
			transactionManager.commit(status);
		}
		return temp;
	}
}
