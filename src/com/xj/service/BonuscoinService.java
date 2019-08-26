package com.xj.service;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.BonuscoinHistory;
import com.xj.bean.CommunityFeature;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.Shops;
import com.xj.dao.BonusCoinDao;
import com.xj.dao.MyUserDao2;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.vo.MyReturnKey;

/**
 * @author lence
 * @date 2015-7-15 下午12:50:10
 * @version 1.0
 */
@Service("bonuscoinService")
public class BonuscoinService {

	@Autowired
	private MyUserDao2 myUserDao2;
	
	@Autowired
	private ShopsDao shopsDao;
	
	@Autowired
	private BonusCoinDao bonusCoinDao;
	
	public Integer addBonuscoin(Orders orders) throws Exception {
		String emobIdUser = orders.getEmobIdUser();
		String emobIdShop = orders.getEmobIdShop();
		String Price = orders.getOrderPrice();
		BigDecimal orderPrice = new BigDecimal(0);
		if(StringUtils.isNotBlank(Price)){
			orderPrice = new BigDecimal(Price);
		}
		Integer communityId = orders.getCommunityId();
		Shops shops = shopsDao.getShopsByEmobId(emobIdShop);
		String sort = shops.getSort();
		
		//通过小区 和店家 id获取 帮帮兑换比例 
		float ratio = bonusCoinDao.findBonusRatio(communityId,sort);
		String ratiostr = ratio*orderPrice.floatValue()+"";
//		此次消费可以获得的帮帮币数量
		Integer bonusCount = new Integer(ratiostr.substring(0, ratiostr.indexOf(".")));
	
		myUserDao2.updateUserBonusCoin(emobIdUser,bonusCount);
		
		//添加历史信息
		BonuscoinHistory bonuscoinHistory= new BonuscoinHistory();
		bonuscoinHistory.setBonuscoinCount(bonusCount);
		bonuscoinHistory.setCreateTime((int)(System.currentTimeMillis()/1000));
		bonuscoinHistory.setBonuscoinSource(sort);
		bonuscoinHistory.setEmobId(emobIdUser);
		
		bonusCoinDao.save(bonuscoinHistory, null);
		
		return bonusCount;
	}
	
	/**
	 * 添加帮币历史
	 * @param bonuscoinHistory
	 * @throws Exception 
	 */
	public void addBonuscoinHistory(BonuscoinHistory bonuscoinHistory)  {
		try {
			bonusCoinDao.save(bonuscoinHistory, new MyReturnKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取帮帮币配置列表
	 * @param pageSize 
	 * @param pageNum 
	 * @param query 
	 * @return
	 */
	public Page<CommunityFeature> getBonusCoinConfig(String query, Integer pageNum, Integer pageSize) {
		try {
			if(StringUtils.isBlank(query)){
				return bonusCoinDao.getBonusCoinConfig(pageNum,pageSize);
			}else{
				return bonusCoinDao.getBonusCoinConfig(query,pageNum,pageSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 配置帮帮币
	 * @param communityFeature
	 * @return
	 */
	public Boolean configBonusCoin(CommunityFeature communityFeature) {
		communityFeature.setUpdateTime((int)(System.currentTimeMillis()/1000l));
		Boolean result;
		try {
			communityFeature.setExchangeRatio(new BigDecimal(communityFeature.getExchange()).divide(new BigDecimal(communityFeature.getExchangeCoin()),2,BigDecimal.ROUND_HALF_UP).floatValue());
			communityFeature.setExpenseRatio(new BigDecimal(communityFeature.getExpense()).divide(new BigDecimal(communityFeature.getExpenseCoin()),2,BigDecimal.ROUND_HALF_UP).floatValue());
			result = bonusCoinDao.saveOrUpdateBonusCoinConfig(communityFeature);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
}