package com.xj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.xj.bean.UniqueData;
import com.xj.bean.UserBonus;
import com.xj.bean.XjBill;
import com.xj.dao.UniqueDataDao;
import com.xj.dao.UserBonusDao;
import com.xj.dao.XjBillDao;

@Service("xjBillService")
public class XjBillService {
	
	@Autowired
	private XjBillDao xjBillDao;
	
	@Autowired
	private UniqueDataDao uniqueDataDao;
	
	@Autowired
	private UserBonusDao userBonusDao;
	
	public Integer addjBill(XjBill xjBill , List<UserBonus> list) throws Exception{ // 添加流水
		if(list != null){
			if(list != null && list.size() > 0){
 				UserBonus userBonus = list.get(0);
 				if(!"used".equals(userBonus.getBonusStatus())){
 					UserBonus userBonus2 = new UserBonus();
 					userBonus2.setUserBonusId(userBonus.getUserBonusId());
 					userBonus2.setUseTime((int)(System.currentTimeMillis() / 1000l));
 					userBonus2.setBonusStatus("used");
 					userBonus2.setSerial(xjBill.getBillChannel());
 					userBonusDao.updateUserBonus(userBonus2);
 				}
 			}
		}
		Integer resultId = null;
		UniqueData uniqueData = new UniqueData();
		uniqueData.setCreateTime((int)(System.currentTimeMillis() / 1000l));
		XjBill xjBill2 = xjBillDao.getXjBill(xjBill.getTradeNo());
		if(xjBill2 != null){
			xjBill.setId(xjBill2.getId());
			xjBill.setBillRet("TRADE_SUCCESS");
			xjBillDao.updateXjBill(xjBill);
		}else{
			Integer billId = uniqueDataDao.addUniqueData(uniqueData);
			xjBill.setBillId(billId);
			
			xjBill.setBillTime((int)(System.currentTimeMillis() / 1000l));
			resultId = xjBillDao.addXjBill(xjBill);
		}
		
		return resultId;
	}
	
	public static void main(String[] args) {
	
	}
}
