package com.xj.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Config;
import com.xj.dao.ConfigDao;

@Service("configService")
public class ConfigService {
	
	@Autowired
	private ConfigDao configDao;
	
	public void add(Config config) {
		if (StringUtils.isBlank(config.getKey()) || StringUtils.isBlank(config.getKey())) {
			throw new IllegalArgumentException("Config的key和value不能为空");
		}
		
		configDao.add(config);
	}
	
	public void update(Config config) {
		if (StringUtils.isBlank(config.getKey()) || StringUtils.isBlank(config.getKey())) {
			throw new IllegalArgumentException("Config的key和value不能为空");
		}
		
		configDao.update(config);
	}
	
	public Config get(String key) {
		if (StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("key不能为空");
		}
		
		return configDao.get(key);
	}
}