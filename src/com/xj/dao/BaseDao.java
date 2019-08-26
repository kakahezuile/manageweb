package com.xj.dao;

public interface BaseDao<T,PK> {

	public int insert(T t);
	public boolean update(T t);
	public boolean deleteById(PK id);
	public T findById(PK id);
}
