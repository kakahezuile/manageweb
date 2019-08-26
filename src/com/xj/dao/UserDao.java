package com.xj.dao;

import java.util.List;

import com.xj.bean.UserBean;

public interface UserDao extends BaseDao<UserBean, Integer> {

	public List<UserBean> findAllByType(String type);

	public UserBean findByUserName(String userName);

}