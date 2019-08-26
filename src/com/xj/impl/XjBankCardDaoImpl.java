package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjBankCard;
import com.xj.dao.XjBankCardDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjBankCardDao")
public class XjBankCardDaoImpl extends MyBaseDaoImpl implements XjBankCardDao {

	@Override
	public Integer addXjBankCard(XjBankCard xjBankCard) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjBankCard, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateXjBankCard(XjBankCard xjBankCard) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_bank_card SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjBankCard);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE bank_card_id = ? ";
		System.out.println(sql);
		list.add(xjBankCard.getBankCardId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<XjBankCard> getBankCardList(String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT xbc.bank_card_id , xbc.community_id , xbc.card_no , xbc.card_name , xbc.card_phone , xbc.emob_id , xbc.create_time , xbc.bank_category_id , b.bank_name  FROM xj_bank_card xbc" +
				" left join bank_category b on b.bank_category_id = xbc.bank_category_id WHERE xbc.emob_id = ? ";
		List<XjBankCard> list = this.getList(sql, new Object[]{emobId}, XjBankCard.class);
		return list;
	}

	@Override
	public XjBankCard getBankCard(String emobId, String cardNo)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT bank_card_id , card_no , card_name , card_phone , emob_id , create_time FROM xj_bank_card WHERE emob_id = ? AND card_no = ? ";
		List<XjBankCard> list = this.getList(sql, new Object[]{emobId,cardNo}, XjBankCard.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
