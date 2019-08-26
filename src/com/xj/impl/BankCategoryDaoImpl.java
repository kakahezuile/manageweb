package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BankCategory;
import com.xj.dao.BankCategoryDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bankCategoryDao")
public class BankCategoryDaoImpl extends MyBaseDaoImpl implements BankCategoryDao{

	@Override
	public Integer addBankCategory(BankCategory bankCategory) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(bankCategory, key);
		return key.getId();
	}

	@Override
	public List<BankCategory> getBankCategoryList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT bank_category_id , bank_name , bank_cate FROM bank_category WHERE 1 = 1 ";
		List<BankCategory> list = this.getList(sql, new Object[]{}, BankCategory.class);
		return list;
	}

}
