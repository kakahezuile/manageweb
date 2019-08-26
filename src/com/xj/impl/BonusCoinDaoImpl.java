package com.xj.impl;

import org.springframework.stereotype.Repository;

import com.xj.bean.CommunityFeature;
import com.xj.bean.Page;
import com.xj.dao.BonusCoinDao;
import com.xj.httpclient.vo.MyReturnKey;

/**
 * @author lence
 * @date 2015-7-15 下午01:47:40
 * @version 1.0
 */
@Repository("bonusCoinDao")
public class BonusCoinDaoImpl extends MyBaseDaoImpl implements BonusCoinDao {

	@Override
	public float findBonusRatio(Integer communityId, String sort) throws Exception {
		return this.getValue("SELECT b.proportion from bonuscoin_ratio b WHERE b.community_id=? AND b.sort=?", Float.class, communityId,sort);
	}

	@Override
	public Page<CommunityFeature> getBonusCoinConfig(Integer pageNum, Integer pageSize) throws Exception {
		String sql = " SELECT c.community_id ,c.community_name ,IFNULL(f.feature_value,'no') feature_value ,IFNULL(f.share_coin,0) share_coin ,IFNULL(f.expense,0) expense,IFNULL(f.expense_coin,0) expense_coin,IFNULL(f.expense_ratio,0) expense_ratio,IFNULL(f.exchange_coin,0) exchange_coin,IFNULL(f.exchange,0) exchange,IFNULL(f.exchange_ratio,0) exchange_ratio FROM communities c LEFT JOIN community_feature f ON c.community_id = f.community_id WHERE  feature_name ='showBonuscoin' OR  feature_name IS NULL  "; 
		Page<CommunityFeature> page = this.getData4Page(sql, new Object[]{}, pageNum, pageSize, 10, CommunityFeature.class);
		return page;
	}

	@Override
	public Page<CommunityFeature> getBonusCoinConfig(String query, Integer pageNum, Integer pageSize) throws Exception {
		String sql = " SELECT c.community_id ,c.community_name ,IFNULL(f.feature_value,'no') feature_value ,IFNULL(f.share_coin,0) share_coin ,IFNULL(f.expense,0) expense,IFNULL(f.expense_coin,0) expense_coin,IFNULL(f.expense_ratio,0) expense_ratio,IFNULL(f.exchange_coin,0) exchange_coin,IFNULL(f.exchange,0) exchange,IFNULL(f.exchange_ratio,0) exchange_ratio FROM communities c LEFT JOIN community_feature f ON c.community_id = f.community_id WHERE  ( feature_name ='showBonuscoin' OR  feature_name IS NULL )  AND community_name LIKE '%"+query+"%' "; 
		Page<CommunityFeature> page = this.getData4Page(sql, null , pageNum, pageSize, 10, CommunityFeature.class);
		
		return page;
	}

	@Override
	public Boolean saveOrUpdateBonusCoinConfig(CommunityFeature communityFeature) throws Exception {
		String update = " UPDATE `community_feature` SET `feature_value`=?,`expense`=?, `expense_coin`=?, `expense_ratio`=?, `share_coin`=?, `exchange_coin`=?, `exchange`=?, `exchange_ratio`=?, `update_time`=? WHERE    `community_id`=? AND `feature_type`='bonuscoin' AND `feature_name`='showBonuscoin'  " ;
		int result = this.getJdbcTemplate().update(update, communityFeature.getFeatureValue(),communityFeature.getExpense(),communityFeature.getExpenseCoin(),communityFeature.getExpenseRatio(),communityFeature.getShareCoin(),communityFeature.getExchangeCoin(),communityFeature.getExchange(),communityFeature.getExchangeRatio(),communityFeature.getUpdateTime(),communityFeature.getCommunityId());
		if(result==0){
			communityFeature.setFeatureName("showBonuscoin");
			communityFeature.setFeatureType("bonuscoin");
			this.save(communityFeature, new MyReturnKey());
		}
		return true;
	}
}