package com.xj.stat.service;

import java.util.List;


public interface UserService {

	
	List<String> getAllInstallUserByCommunity(Integer communityid);

	List<String> getAllRegisService(Integer communityid);
}
