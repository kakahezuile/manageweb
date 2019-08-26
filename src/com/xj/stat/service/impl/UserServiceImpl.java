package com.xj.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.stat.dao.UserDao;
import com.xj.stat.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public List<String> getAllInstallUserByCommunity(Integer communityid) {
		List<String> list = userDao.findAllInstallUserIdByCommunity(communityid);
		return list;
	}

	public List<String> getAllRegisService(Integer communityid) {
		List<String> list  = userDao.findAllRegisService(communityid);
		return list;
	}


}
