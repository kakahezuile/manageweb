package com.xj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.BonuscoinManager;
import com.xj.dao.BonuscoinManagerDao;

@Service
public class BonuscoinManagerService {
	
	@Autowired
	private BonuscoinManagerDao bonuscoinManagerDao;
	
	public boolean addBonuscoinManager(BonuscoinManager bonuscoinManager) throws Exception{ //添加分享模块分享额度
		boolean result = false;
		Integer shopType = bonuscoinManager.getShopTypeId();
		BonuscoinManager bonuscoinManager2 = bonuscoinManagerDao.getBonuscoinManager(shopType);
		if(bonuscoinManager2 != null){
			bonuscoinManager.setBonuscoinManagerId(bonuscoinManager2.getBonuscoinManagerId());
			bonuscoinManagerDao.updateBonuscoinManager(bonuscoinManager);
		}else{
			bonuscoinManagerDao.addBonuscoinManager(bonuscoinManager);
		}
		return result;
	}
	
	public BonuscoinManager getBonuscoinManager(Integer shopType) throws Exception{ // 根据模块获取分享额度
		BonuscoinManager bonuscoinManager = bonuscoinManagerDao.getBonuscoinManager(shopType);
		return bonuscoinManager;
	}
}
