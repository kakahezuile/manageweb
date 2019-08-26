package com.xj.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.BonuscoinRatio;
import com.xj.dao.BonuscoinRatioDao;

@Service
public class BonuscoinRatioService {
	
	@Autowired
	private BonuscoinRatioDao bonuscoinRatioDao;
	
	public boolean addBonuscoinRatio(BonuscoinRatio bonuscoinRatio) throws Exception{ // 添加帮帮币兑换比例
		BonuscoinRatio bonuscoinRatio2 = bonuscoinRatioDao.getBonuscoinRatio(bonuscoinRatio.getCommunityId(), bonuscoinRatio.getSort());
		boolean result = false;
		DecimalFormat format = new DecimalFormat("0.0");
		float consum = bonuscoinRatio.getConsume();
		float exchange = bonuscoinRatio.getExchange();
		float proportion = consum / exchange;
		proportion = format.parse(format.format(proportion)).floatValue();
		bonuscoinRatio.setProportion(proportion);
		if(bonuscoinRatio2 != null){
			bonuscoinRatio.setRatiosId(bonuscoinRatio2.getRatiosId());
			bonuscoinRatio.setUpdateTime((int)(System.currentTimeMillis() / 1000));
			result =  bonuscoinRatioDao.updateBonuscoinRatio(bonuscoinRatio);
		}else{
			bonuscoinRatio.setCreateTime((int)(System.currentTimeMillis() / 1000));
			int count = bonuscoinRatioDao.addBonuscoinRatio(bonuscoinRatio);
			if(count > 0){
				result = true;
			}
		}
		return result;
	}
	
	public BonuscoinRatio getBonuscoinRatio(Integer communityId , Integer sort) throws Exception{
		BonuscoinRatio bonuscoinRatio = bonuscoinRatioDao.getBonuscoinRatio(communityId, sort);
		return bonuscoinRatio;
	}
}
