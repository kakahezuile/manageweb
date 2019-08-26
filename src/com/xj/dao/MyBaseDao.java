package com.xj.dao;

import java.util.List;
import java.util.Map;

import com.xj.bean.Page;
import com.xj.bean.TableDesc;
import com.xj.bean.UserBonus;




/**
 * create by lidongquan 2014.12.30
 * @author Administrator
 *
 */


public interface MyBaseDao{
	
	/**
	 * 持久化实体对象
	 * 如果 实体对应的主键 自动递增 且 实体对应的主键属性为 null 则让数据库来生成主键值
	 * 如果 实体 设置 对应主键属性值 则使用 设置好的 值，如果数据库中存在此值，会出现异常
	 * @param obj
	 * @param returnKeyList
	 * @return
	 * @throws Exception
	 */
	int save(Object obj,ReturnKeyList returnKeyList  ) throws Exception;
	
	int update(Object obj) throws Exception;
	
	int delete(Object obj) throws Exception;
	
	<Entity> Entity getObjByPK(Object[] pk,Class<Entity> clazz) throws Exception;
	
	<Entity> List<Entity> getList(String sql, Object[] params,Class<Entity> clazz)throws Exception;
	
	int updateData(String sql,List<Object> params,ReturnKeyList returnKeyList) throws Exception;
	
	<Entity> Entity getValue(String sql,Class<Entity> clazz, Object...params) throws Exception;
	
	<Entity> Page<Entity> getData4Page(String sql,Object[] params,int pageNum,int pageSize,int navNum,Class<Entity> clazz) throws Exception;
	/**
	 * 获取数据库表中 主键 的字段集合。
	 * Map<String,Boolean>  第一个存储 主键列名  第二个存储 该主键是否为 自动递增
	 * @return
	 */
	List<TableDesc> getTableDesc(Class<?> clazz) throws Exception;
	
	Map<String,TableDesc> getPrimaryKeys(Class<?> clazz) throws Exception;
	
	<Entity> List<Entity> getList(Class<Entity> clazz) throws Exception;
	 public int excuteTrac(final List<UserBonus> list) ;
}
