package com.xj.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Communities;
import com.xj.bean.CommunityFeature;
import com.xj.dao.BonusCoinDao;
import com.xj.dao.CommunitiesDao;

@Service("communitiesService")
public class CommunitiesService {
	
	
	@Autowired
	private CommunitiesDao communitiesDao;
	
	@Autowired
	private BonusCoinDao bonusCoinDao;

	/**
	 * 获取小区属性
	 * @param communityId
	 * @return
	 */
	public List<CommunityFeature> getCommunityFeature(Integer communityId) {
		try {
			return communitiesDao.getCommunityFeature(communityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int addCommunities(Communities communities) throws Exception {
		communities .setCreateTime((int) (System.currentTimeMillis() / 1000));
		int result = communitiesDao.addCommunities(communities);
		if(result>0){
			CommunityFeature communityFeature = new CommunityFeature("showBonuscoin", "bonuscoin", "yes", result, 100, 20, 10, 100, 1);
			communityFeature.setUpdateTime((int)(System.currentTimeMillis()/1000l));
			communityFeature.setExchangeRatio(new BigDecimal(communityFeature.getExchange()).divide(new BigDecimal(communityFeature.getExchangeCoin()),2,BigDecimal.ROUND_HALF_UP).floatValue());
			communityFeature.setExpenseRatio(new BigDecimal(communityFeature.getExpense()).divide(new BigDecimal(communityFeature.getExpenseCoin()),2,BigDecimal.ROUND_HALF_UP).floatValue());
			bonusCoinDao.saveOrUpdateBonusCoinConfig(communityFeature);
			return result;
		}
		return 0 ;
	}
	
	/**
	 * 根据小区id获取首页背景图
	 * @param i
	 * @return
	 */
	public String getHomePic(Integer communityId) {
		return communitiesDao.getHomePic(communityId);
	}

	/**
	 * 根据小区id更新首页图片
	 * @param i 
	 * @param posterFile
	 */
	public void saveOrUpdateHomePic(Integer communityId, String posterFile) {
		communitiesDao.saveOrUpdateHomePic(communityId, posterFile);
	}

}
